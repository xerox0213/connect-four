package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Token[][] tokens;

    @BeforeEach
    void setUp() {
        BoardSize boardSize = BoardSize.EIGHT_BY_SEVEN;
        this.tokens = new Token[boardSize.getCols()][boardSize.getRows()];
    }

    @Test
    public void testAddTokenShouldThrowExceptionForOutsideBoard() {
        int colIndex = 1000000;
        Token token = Token.RED;
        Board board = new Board(tokens);
        ConnectFourException e = assertThrows(ConnectFourException.class, () -> board.addToken(colIndex, token));
        assertEquals(e.getConnectFourError(), ConnectFourError.OUTSIDE_BOARD);
    }
}