package game.model;

public class Time {
    private int millis;
    private final int backupMillis;

    public Time(int millis) {
        this.millis = millis;
        this.backupMillis = millis;
    }
}
