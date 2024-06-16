package game.model;

import game.exception.ConnectFourException;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoundTimer {
    private final PlayerManager playerManager;
    private final ScheduledExecutorService scheduler;

    public RoundTimer(PlayerManager playerManager, ScheduledExecutorService scheduler) {
        this.playerManager = playerManager;
        this.scheduler = scheduler;
    }

    public void start() {
        Runnable runnable = () -> {
            try {
                playerManager.reduceCurrPlayerTime(1000);
            } catch (ConnectFourException e) {
                playerManager.declareNextPlayerWinner();
                stop();
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        scheduler.shutdownNow();
    }

}
