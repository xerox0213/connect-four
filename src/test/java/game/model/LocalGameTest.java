package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocalGameTest {
    @Mock
    RoundTimer roundTimer;
    @Mock
    Board board;
    @Mock
    PlayerManager playerManager;

    @BeforeEach
    void setUp() throws ConnectFourException {
        Mockito.lenient().when(playerManager.getCurrPlayerToken()).thenReturn(Token.RED);
        ConnectFourException e1 = new ConnectFourException(ConnectFourError.OUTSIDE_BOARD);
        Mockito.lenient().doThrow(e1).when(board).addToken(-1, Token.RED);
        ConnectFourException e2 = new ConnectFourException(ConnectFourError.COLUMN_FILLED);
        Mockito.lenient().doThrow(e2).when(board).addToken(5, Token.RED);
        Mockito.lenient().when(board.addToken(1, Token.RED)).thenReturn(true);
        Mockito.lenient().when(roundTimer.getMillis()).thenReturn(1000L);
    }

    void verifyCallMethodMock(int nStart, int nIsBoardFull, int nNextPlayer, int nCurrPlayerWinner, int nGameDraw, int columnIndex) throws ConnectFourException {
        Mockito.verify(roundTimer, Mockito.times(1)).stop();
        Mockito.verify(roundTimer, Mockito.times(nStart)).start();
        Mockito.verify(board, Mockito.times(1)).addToken(columnIndex, Token.RED);
        Mockito.verify(board, Mockito.times(nIsBoardFull)).isBoardFull();
        Mockito.verify(playerManager, Mockito.times(nNextPlayer)).nextPlayer();
        Mockito.verify(playerManager, Mockito.times(nCurrPlayerWinner)).declareCurrPlayerWinner();
        Mockito.verify(playerManager, Mockito.times(nGameDraw)).declareGameDraw();
    }

    @Test
    void testPlayShouldJustRestartRoundTimerIfBoardThrowExceptionForOutsideBoard() throws ConnectFourException {
        LocalGame localGame = new LocalGame(board, playerManager, roundTimer);
        int columnIndex = -1;
        localGame.play(columnIndex);
        verifyCallMethodMock(1, 0, 0, 0, 0, columnIndex);
    }

    @Test
    void testPlayShouldJustRestartRoundTimerIfBoardThrowExceptionForColumnFilled() throws ConnectFourException {
        LocalGame localGame = new LocalGame(board, playerManager, roundTimer);
        int columnIndex = 5;
        localGame.play(columnIndex);
        verifyCallMethodMock(1, 0, 0, 0, 0, columnIndex);
    }

    @Test
    void testPlayShouldHandleTheMoveFromThePlayer() throws ConnectFourException {
        LocalGame localGame = new LocalGame(board, playerManager, roundTimer);
        int columnIndex = 0;
        localGame.play(columnIndex);
        verifyCallMethodMock(1, 1, 1, 0, 0, columnIndex);
    }

    @Test
    void testPlayShouldDeclareCurrPlayerWinner() throws ConnectFourException {
        LocalGame localGame = new LocalGame(board, playerManager, roundTimer);
        int columnIndex = 1;
        localGame.play(columnIndex);
        verifyCallMethodMock(0, 1, 0, 1, 0, columnIndex);
    }

    @Test
    void testPlayShouldStopGameIfBoardFilled() throws ConnectFourException {
        LocalGame localGame = new LocalGame(board, playerManager, roundTimer);
        Mockito.lenient().when(board.isBoardFull()).thenReturn(true);
        int columnIndex = 0;
        localGame.play(columnIndex);
        verifyCallMethodMock(0, 1, 0, 0, 1, columnIndex);
    }

    @Test
    void testStartShouldStartTheGame() {
        Token[][] tokens = new Token[1][1];
        Mockito.lenient().when(board.getCopyTokens()).thenReturn(tokens);
        LocalGame localGame = new LocalGame(board, playerManager, roundTimer);
        localGame.start();
        Mockito.verify(board, Mockito.times(1)).getCopyTokens();
        Mockito.verify(playerManager, Mockito.times(1)).notifyGameStart(tokens, 1000);
        Mockito.verify(roundTimer, Mockito.times(1)).start();
    }

    @Test
    void testStopShouldStopTheGame() {
        Token[][] tokens = new Token[1][1];
        Mockito.lenient().when(board.getCopyTokens()).thenReturn(tokens);
        LocalGame localGame = new LocalGame(board, playerManager, roundTimer);
        Token playerId = Token.BLUE;
        localGame.stop(playerId);
        Mockito.verify(roundTimer, Mockito.times(1)).stop();
        Mockito.verify(playerManager, Mockito.times(1)).giveUp(playerId);
    }
}