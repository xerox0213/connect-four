package game.model;

/**
 * Represents the possible board sizes for the Connect Four game.
 */
public enum BoardSize {
    SEVEN_BY_SIX(7, 6, "7x6"),
    EIGHT_BY_SEVEN(8, 7, "8x7"),
    EIGHT_BY_EIGHT(8, 8, "8x8"),
    NINE_BY_SEVEN(9, 7, "9x7"),
    NINE_BY_NINE(9, 9, "9x9");

    private final String representation;
    private final int cols;
    private final int rows;

    BoardSize(int cols, int rows, String representation) {
        this.cols = cols;
        this.rows = rows;
        this.representation = representation;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    @Override
    public String toString() {
        return representation;
    }
}
