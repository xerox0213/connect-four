package game.model;

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

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
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
