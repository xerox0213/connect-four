package game.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardAlgorithmTest {

    @Test
    void testIsBoardFullShouldReturnTrue() {
        Token[][] tokens = {{Token.BLUE, Token.RED}, {Token.BLUE, Token.RED}};
        boolean isBoardFull = BoardAlgorithm.isBoardFull(tokens);
        assertTrue(isBoardFull);
    }

    @Test
    void testIsBoardFullShouldReturnFalse() {
        Token[][] tokens = {{Token.BLUE, null}, {Token.BLUE, Token.RED}};
        boolean isBoardFull = BoardAlgorithm.isBoardFull(tokens);
        assertFalse(isBoardFull);
    }
}