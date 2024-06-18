package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleStrategyTest {

    SimpleStrategy simpleStrategy;
    Token aiToken;

    @BeforeEach
    void setUp() {
        simpleStrategy = new SimpleStrategy();
        aiToken = Token.BLUE;
    }

    @Test
    void testGetBestMoveShouldReturnBlockingMove() {
        Token[][] tokens = {{null, Token.RED, Token.RED, Token.RED}, {null, null, Token.BLUE, Token.BLUE}};
        int expected = 0;
        int result = simpleStrategy.getBestMove(tokens, aiToken);
        assertEquals(expected, result);
    }

    @Test
    void testGetBestMoveShouldReturnWinningMove() {
        Token[][] tokens = {{null, Token.RED, Token.RED, Token.RED}, {null, Token.BLUE, Token.BLUE, Token.BLUE}};
        int expected = 1;
        int result = simpleStrategy.getBestMove(tokens, aiToken);
        assertEquals(expected, result);
    }

    @Test
    void testGetBestMoveShouldReturnNothingMove() {
        Token[][] tokens = {{null, null, Token.RED, Token.RED}, {null, null, Token.BLUE, Token.BLUE}};
        List<Integer> nothingMoves = new ArrayList<>(List.of(0, 1));
        int result = simpleStrategy.getBestMove(tokens, aiToken);
        assertTrue(nothingMoves.contains(result));
    }

}