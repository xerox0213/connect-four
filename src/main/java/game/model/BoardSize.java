package game.model;

/**
 * Represents the possible board sizes for the Connect Four game.
 */
public enum BoardSize {
    SEVEN_BY_SIX(7, 6),
    EIGHT_BY_SEVEN(8, 7),
    EIGHT_BY_EIGHT(8, 8),
    NINE_BY_SEVEN(9, 7),
    NINE_BY_NINE(9, 9);

    private final int cols;
    private final int rows;

    BoardSize(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
