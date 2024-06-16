package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;

public class Time {
    private long millis;
    private final long backupMillis;

    public Time(long millis) {
        this.millis = millis;
        this.backupMillis = millis;
    }

    public void reduceTime(long millis) throws ConnectFourException {
        if (this.millis < millis) throw new ConnectFourException(ConnectFourError.NO_TIME_LEFT);
        this.millis -= millis;
    }

    public boolean isTimeLeft() {
        return millis > 0;
    }

    public void reset() {
        this.millis = backupMillis;
    }

    public long getMillis() {
        return millis;
    }
}
