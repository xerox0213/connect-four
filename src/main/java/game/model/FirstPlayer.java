package game.model;

public enum FirstPlayer {
    ME("I play first"),
    OPPONENT("Opponent plays first"),
    RANDOM("Random");

    private final String representation;

    FirstPlayer(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
