package game.model;

import game.exception.ConnectFourException;
import game.oo.Observer;

import java.util.Set;

public class Player {
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

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
    }
}
