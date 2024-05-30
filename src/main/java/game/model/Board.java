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
}
