package game.model;

import game.exception.ConnectFourException;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoundTimer {
    private final Time time;
    private final PlayerManager playerManager;
    private final ScheduledExecutorService scheduler;

    public RoundTimer(Time time, PlayerManager playerManager, ScheduledExecutorService scheduler) {
        this.time = time;
        this.playerManager = playerManager;
        this.scheduler = scheduler;
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

}
