package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(BoardSize.EIGHT_BY_SEVEN);
    }

    @Test
    public void testAddTokenShouldThrowExceptionForOutsideBoard() {
        int colIndex = 1000000;
        Token token = Token.RED;
        ConnectFourException e = assertThrows(ConnectFourException.class, () -> board.addToken(colIndex, token));
        assertEquals(e.getConnectFourError(), ConnectFourError.OUTSIDE_BOARD);
    }
}