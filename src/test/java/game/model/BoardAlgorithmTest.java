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

    @Test
    void testIsOutsideBoardWithJustColumnIndexShouldReturnTrue() {
        Token[][] tokens = {{Token.BLUE, null}, {Token.BLUE, Token.RED}};
        assertTrue(BoardAlgorithm.isOutsideBoard(tokens, -1));
        assertTrue(BoardAlgorithm.isOutsideBoard(tokens, tokens.length));
    }

    @Test
    void testIsOutsideBoardWithJustColumnIndexShouldReturnFalse() {
        Token[][] tokens = {{Token.BLUE, null}, {Token.BLUE, Token.RED}};
        assertFalse(BoardAlgorithm.isOutsideBoard(tokens, 0));
        assertFalse(BoardAlgorithm.isOutsideBoard(tokens, 1));
    }

    @Test
    void testIsOutsideBoardWithColumnIndexAndRowIndexShouldReturnTrue() {
        Token[][] tokens = {{Token.BLUE, null}, {Token.BLUE, Token.RED}};
        assertTrue(BoardAlgorithm.isOutsideBoard(tokens, tokens.length, -1));
        assertTrue(BoardAlgorithm.isOutsideBoard(tokens, tokens.length, tokens[0].length));
    }

    @Test
    void testIsOutsideBoardWithColumnIndexAndRowIndexShouldReturnFalse() {
        Token[][] tokens = {{Token.BLUE, null}, {Token.BLUE, Token.RED}};
        assertFalse(BoardAlgorithm.isOutsideBoard(tokens, 0, 0));
    }

    @Test
    void testGetFreeRowIndexShouldNotHaveFreeRowIndex() {
        Token[][] tokens = {{Token.BLUE, Token.RED}, {Token.BLUE, Token.RED}};
        int expected = -1;
        int rowIndex = BoardAlgorithm.getFreeRowIndex(tokens, 0);
        assertEquals(expected, rowIndex);
    }

    @Test
    void testGetFreeRowIndexShouldHaveFreeRowIndex() {
        Token[][] tokens = {{Token.BLUE, Token.RED}, {null, Token.RED}};
        int expected = 0;
        int rowIndex = BoardAlgorithm.getFreeRowIndex(tokens, 1);
        assertEquals(expected, rowIndex);
    }
}