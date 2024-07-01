package game.multiplayer;

import game.dto.*;
import game.model.Game;
import game.oo.ConnectFourEvent;
import game.oo.Observable;
import game.oo.Observer;

import java.util.Set;

public class PlayerSocket implements Game, Observable, MsgHandler {
    private final RolePlayer rolePlayer;
    private final SocketHandler socketHandler;
    private final String playerName;
    private final Set<Observer> observers;
    private final GameConfigDto gameConfigDto;

    public PlayerSocket(RolePlayer rolePlayer, SocketHandler socketHandler, String playerName, Set<Observer> observers, GameConfigDto gameConfigDto) {
        this.rolePlayer = rolePlayer;
        this.socketHandler = socketHandler;
        this.playerName = playerName;
        this.observers = observers;
        this.gameConfigDto = gameConfigDto;
    }

    @Override
    public void handleMessage(SocketHandler socketHandler, SocketMsgDto<?> input) {
        SocketEvent socketEvent = input.socketEvent();
        if (socketEvent == SocketEvent.CONNECT_FOUR_EVENT) {
            ConnectFourEventDto cfed = (ConnectFourEventDto) input.data();
            ConnectFourEvent e = cfed.connectFourEvent();
            Object data = cfed.data();
            notifyObservers(e, data);
        }
    }

    @Override
    public void start() {
        Thread thread = new Thread(socketHandler);
        thread.setDaemon(true);
        thread.start();
        send(SocketEvent.REMOTE_PLAYER_DATA, new RemotePlayerDataDto(rolePlayer, playerName, gameConfigDto));
    }

    @Override
    public void play(int columnIndex) {
        send(SocketEvent.PLAY, columnIndex);
    }

    @Override
    public void stop() {
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(ConnectFourEvent e, Object data) {
        observers.forEach(o -> o.update(e, data));
    }

    private <T> void send(SocketEvent socketEvent, T data) {
        SocketMsgDto<T> socketMsgDto = new SocketMsgDto<>(socketEvent, data);
        socketHandler.send(socketMsgDto);
    }

}
