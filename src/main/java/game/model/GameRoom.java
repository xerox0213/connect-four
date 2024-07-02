package game.model;

import game.dto.GameConfigDto;
import game.dto.ServerConnectionDto;
import game.multiplayer.*;
import game.oo.ConnectFourEvent;
import game.oo.Observable;
import game.oo.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameRoom implements Observable {
    private String playerName;
    private final GameFactory gameFactory;
    private boolean againstComputer;
    private final Set<Observer> observers;
    private ConnectFourServer connectFourServer;

    public GameRoom(GameFactory gameFactory, Set<Observer> observers) {
        this.gameFactory = gameFactory;
        this.observers = observers;
    }

    public void setMyPlayerName(String playerName) {
        this.playerName = playerName;
    }


    public Game createGame(GameConfigDto gameConfigDto, Observer presenterObserver) throws IOException {
        if (againstComputer) {
            return playAgainstComputer(gameConfigDto, presenterObserver);
        } else {
            return playWithFriend(gameConfigDto, presenterObserver);
        }
    }

    public Game joinGame(String ip, int port, Observer presenterObserver) throws IOException {
        return createPlayerSocket(ip, port, RolePlayer.GUEST, null, presenterObserver);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(ConnectFourEvent e, Object data) {
        observers.forEach(o -> o.update(e, data));
    }

    public void setAgainstComputer(boolean againstComputer) {
        this.againstComputer = againstComputer;
    }

    private Game playAgainstComputer(GameConfigDto gameConfigDto, Observer presenterObserver) {
        return gameFactory.createGameAgainstComputer(gameConfigDto, playerName, presenterObserver, "Bip Boop");
    }

    private Game playWithFriend(GameConfigDto gameConfigDto, Observer presenterObserver) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        this.connectFourServer = new ConnectFourServer(serverSocket, new ArrayList<>());
        connectFourServer.runServer();
        notifyObservers(ConnectFourEvent.CONNECT_FOUR_SERVER_STARTED, new ServerConnectionDto("localhost", "8080"));

        return createPlayerSocket("localhost", 8080, RolePlayer.HOST, gameConfigDto, presenterObserver);
    }

    private PlayerSocket createPlayerSocket(String ip, int port, RolePlayer rolePlayer, GameConfigDto gameConfigDto, Observer presenterObserver) throws IOException {
        Socket socket = new Socket(ip, port);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        SocketHandler socketHandler = new SocketHandler(socket, ois, oos);

        PlayerSocket playerSocket = new PlayerSocket(rolePlayer, socketHandler, playerName, new HashSet<>(), gameConfigDto);
        playerSocket.addObserver(presenterObserver);
        socketHandler.setMsgHandler(playerSocket);

        return playerSocket;
    }
}
