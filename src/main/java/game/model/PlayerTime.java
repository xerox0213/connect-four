package game.model;

public enum PlayerTime {
    ONE_MINUTE(60000, "1 minute"),
    TWO_MINUTES(120000, "2 minutes"),
    THREE_MINUTES(180000, "3 minutes");

    private final long millis;
    private final String representation;

    PlayerTime(long millis, String representation) {
        this.millis = millis;
        this.representation = representation;
    }

    public long getMillis() {
        return millis;
    }

    @Override
    public String toString() {
        return representation;
    }
}
