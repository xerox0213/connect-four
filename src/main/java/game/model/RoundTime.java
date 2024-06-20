package game.model;

public enum RoundTime {
    TEN_SECONDS(10000, "10 seconds"),
    TWENTY_SECONDS(20000, "20 seconds"),
    THIRTY_SECONDS(30000, "30 seconds"),
    FORTY_SECONDS(40000, "40 seconds");

    private final long millis;
    private final String representation;

    RoundTime(long millis, String representation) {
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
