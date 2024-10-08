package game.model;

import game.dto.GameDto;
import game.dto.PlayerDto;
import game.exception.ConnectFourException;
import game.oo.ConnectFourEvent;
import game.oo.Observable;
import game.oo.Observer;

import java.util.Set;

public class Player implements Observable {
    private final String name;
    private final Token token;
    private final Time time;
    private final Set<Observer> observers;

    public Player(String name, Token token, Time time, Set<Observer> observers) {
        this.name = name;
        this.token = token;
        this.time = time;
        this.observers = observers;
    }

    public void reduceTime(long millis) throws ConnectFourException {
        time.reduceTime(millis);
        notifyObservers(ConnectFourEvent.PLAYER_TIME_UPDATED, getPlayerDto());
    }

    public void notifyOpponentTimeUpdated(PlayerDto opponentPlayerDto) {
        notifyObservers(ConnectFourEvent.OPPONENT_TIME_UPDATED, opponentPlayerDto);
    }

    public boolean isTimeLeft() {
        return time.isTimeLeft();
    }

    public void play() {
        notifyObservers(ConnectFourEvent.MY_TURN, token);
    }

    public void notifyOpponentTurn(Token opponentToken) {
        notifyObservers(ConnectFourEvent.OPPONENT_TURN, opponentToken);
    }

    public void notifyPlayerLost() {
        notifyObservers(ConnectFourEvent.GAME_OVER, null);
    }

    public void notifyPlayerWon() {
        notifyObservers(ConnectFourEvent.VICTORY, null);
    }

    public void notifyDraw() {
        notifyObservers(ConnectFourEvent.GAME_DRAW, null);
    }

    public Token getToken() {
        return token;
    }

    public void notifyInitialGameState(GameDto gameDto) {
        notifyObservers(ConnectFourEvent.GAME_INIT, gameDto);
    }

    public PlayerDto getPlayerDto() {
        return new PlayerDto(name, token, time.getMillis());
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(ConnectFourEvent e, Object data) {
        observers.forEach((observer) -> observer.update(e, data));
    }
}
