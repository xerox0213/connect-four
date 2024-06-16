package game.model;

import game.exception.ConnectFourException;
import game.oo.ConnectFourEvent;
import game.oo.Observable;
import game.oo.Observer;

import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoundTimer implements Observable {
    private final Time time;
    private final PlayerManager playerManager;
    private final ScheduledExecutorService scheduler;
    private final Set<Observer> observers;

    public RoundTimer(Time time, PlayerManager playerManager, ScheduledExecutorService scheduler, Set<Observer> observers) {
        this.time = time;
        this.playerManager = playerManager;
        this.scheduler = scheduler;
        this.observers = observers;
    }

    public void start() {
        Runnable runnable = this::reduceCurrPlayerTime;
        scheduler.scheduleAtFixedRate(runnable, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        scheduler.shutdownNow();
    }

    private void reduceCurrPlayerTime() {
        try {
            time.reduceTime(1000);
            notifyObservers(ConnectFourEvent.ROUND_TIME_UPDATED, time.getMillis());
            playerManager.reduceCurrPlayerTime(1000);
            if (!playerManager.hasCurrentPlayerTimeLeft() || !time.isTimeLeft()) declareNextPlayerWinner();
        } catch (ConnectFourException e) {
            declareNextPlayerWinner();
        }
    }

    private void declareNextPlayerWinner() {
        stop();
        playerManager.declareNextPlayerWinner();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(ConnectFourEvent e, Object data) {
        observers.forEach(observer -> observer.update(e, data));
    }
}
