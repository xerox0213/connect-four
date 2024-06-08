package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import game.oo.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BoardTest {

    @Mock
    private Observer observer;
    private Token[][] tokens;

    void fillColumn(int colIndex, int numberTokens, Token token) {
        int rowIndex = tokens[0].length - 1;
        while (numberTokens > 0) {
            tokens[colIndex][rowIndex] = token;
            numberTokens--;
            rowIndex--;
        }
    }

    @BeforeEach
    void setUp() {
        BoardSize boardSize = BoardSize.EIGHT_BY_SEVEN;
        this.tokens = new Token[boardSize.getCols()][boardSize.getRows()];
    }

    @Test
    public void testAddTokenShouldThrowExceptionForOutsideBoard() {
        int colIndex = 1000000;
        Token token = Token.RED;
        Board board = new Board(tokens, new HashSet<>());
        ConnectFourException e = assertThrows(ConnectFourException.class, () -> board.addToken(colIndex, token));
        assertEquals(e.getConnectFourError(), ConnectFourError.OUTSIDE_BOARD);
    }

    @Test
    void testAddTokenShouldThrowExceptionForColumnFilled() {
        int colIndex = 0;
        int numberOfToken = tokens[0].length;
        Token token = Token.RED;
        fillColumn(colIndex, numberOfToken, token);
        Board board = new Board(tokens, new HashSet<>());
        ConnectFourException e = assertThrows(ConnectFourException.class, () -> board.addToken(colIndex, token));
        assertEquals(e.getConnectFourError(), ConnectFourError.COLUMN_FILLED);
    }

    @Test
    void testAddTokenShouldAddToken() {
        Board board = new Board(tokens, new HashSet<>());
        int colIndex = 0;
        Token token = Token.RED;
        assertDoesNotThrow(() -> board.addToken(colIndex, token));
        Token expected = tokens[colIndex][0];
        assertEquals(expected, token);
    }

}