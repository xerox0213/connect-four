package game.model;

import game.exception.ConnectFourException;

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
