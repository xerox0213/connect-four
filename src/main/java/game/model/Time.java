package game.model;

public class Time {
    private long millis;
    private final long backupMillis;

    public Time(long millis) {
        this.millis = millis;
        this.backupMillis = millis;
    }

    public void reduceTime(long millis) {
        this.millis -= millis;
    }

    public void reset() {
        this.millis = backupMillis;
    }

    public long getMillis() {
        return millis;
    }
}
