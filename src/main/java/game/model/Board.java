package game.model;

/**
 * Represents the game board for Connect Four.
 */
public class Board {
    private final int cols;
    private final int rows;
    private final Token[][] tokens;

    public Board(BoardSize boardSize) {
        cols = boardSize.getCols();
        rows = boardSize.getRows();
        tokens = new Token[cols][rows];
    }

    /**
     * Checks if a move is valid by verifying the column index is within the board's bounds
     * and the column is not already filled.
     *
     * @param colIndex the index of the column to check
     * @return true if the move is valid, false otherwise
     */
    public boolean isMoveValid(int colIndex) {
        return isOutsideBoard(colIndex) && !isColumnFilled(colIndex);
    }

    private boolean isOutsideBoard(int colIndex) {
        return colIndex < 0 || colIndex >= cols;
    }

    private boolean isColumnFilled(int colIndex) {
        int rowIndex = 0;
        while (rowIndex < rows) {
            if (tokens[colIndex][rowIndex] == null) return false;
            rowIndex++;
        }
        return true;
    }
}
