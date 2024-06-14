package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import game.oo.ConnectFourEvent;
import game.oo.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

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

    void fillBoard() {
        for (int colIndex = 0; colIndex < tokens.length; colIndex++) {
            fillColumn(colIndex, tokens[0].length, Token.RED);
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
        Token expected = tokens[colIndex][6];
        assertEquals(expected, token);
    }

    @Test
    void testAddObserverShouldAddGivenObserver() {
        Set<Observer> observers = new HashSet<>();
        Board board = new Board(tokens, observers);
        board.addObserver(observer);
        assertTrue(observers.contains(observer));
    }

    @Test
    void testNotifyObserverShouldNotifyObserver() {
        Set<Observer> observers = new HashSet<>(Set.of(observer));
        Board board = new Board(tokens, observers);
        ConnectFourEvent event = ConnectFourEvent.BOARD_UPDATED;
        String data = "Hello";
        board.notifyObservers(event, data);
        Mockito.verify(observer, Mockito.times(1)).update(event, data);
    }

    @Test
    void testIsBoardFullShouldReturnTrue() {
        fillBoard();
        Set<Observer> observers = new HashSet<>();
        Board board = new Board(tokens, observers);
        boolean isBoardFull = board.isBoardFull();
        assertTrue(isBoardFull);
    }

    @Test
    void testIsBoardFullShouldReturnFalse() {
        Set<Observer> observers = new HashSet<>();
        Board board = new Board(tokens, observers);
        boolean isBoardFull = board.isBoardFull();
        assertFalse(isBoardFull);
    }

}