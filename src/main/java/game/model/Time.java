package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;

public class Time {
    private int millis;
    private final int backupMillis;

    public Time(int millis) {
        this.millis = millis;
        this.backupMillis = millis;
    }

    public void reduceTime(int millis) throws ConnectFourException {
        if (this.millis - millis <= 0) {
            throw new ConnectFourException(ConnectFourError.NO_TIME_LEFT);
        }
        this.millis -= millis;
    }

    public void reset() {
        this.millis = backupMillis;
    }

}
