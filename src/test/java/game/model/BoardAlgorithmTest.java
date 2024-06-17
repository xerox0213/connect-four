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

    @Test
    void testIsWinningMoveShouldReturnTrueCauseOfVerticalAlignment() {
        Token[][] tokens = {{null, null, Token.BLUE, Token.BLUE, Token.BLUE}, {Token.RED, Token.BLUE, Token.RED, Token.BLUE, Token.RED}};
        assertTrue(BoardAlgorithm.isWinningMove(tokens, 0, 1, Token.BLUE));
    }

    @Test
    void testIsWinningMoveShouldReturnTrueBecauseHorizontalAlignment() {
        Token[][] tokens = {{Token.RED, Token.BLUE}, {Token.RED, Token.BLUE}, {Token.RED, Token.BLUE}, {null, null}};
        assertTrue(BoardAlgorithm.isWinningMove(tokens, 3, 1, Token.BLUE));
    }

    @Test
    void testIsWinningMoveShouldReturnTrueBecauseDiagonalUpLeftToRightAlignment() {
        Token[][] tokens = {{null, null, null, null}, {null, null, null, null}, {null, Token.BLUE, null, null},
                {null, null, Token.BLUE, null}, {null, null, null, Token.BLUE}};

        assertTrue(BoardAlgorithm.isWinningMove(tokens, 1, 0, Token.BLUE));
    }

    @Test
    void testIsWinningMoveShouldReturnTrueBecauseDiagonalDownLeftToRightAlignment() {
        Token[][] tokens = {{null, null, null, null}, {null, null, Token.BLUE, null}, {null, Token.BLUE, null, null},
                {Token.BLUE, null, null, null}, {null, null, null, null}};

        assertTrue(BoardAlgorithm.isWinningMove(tokens, 0, 3, Token.BLUE));
    }

    @Test
    void testIsWinningMoveShouldReturnFalse(){
        Token[][] tokens = {{null, null, null, null}, {null, null, Token.BLUE, Token.BLUE}};
        assertFalse(BoardAlgorithm.isWinningMove(tokens, 1, 1, Token.BLUE));
    }
}