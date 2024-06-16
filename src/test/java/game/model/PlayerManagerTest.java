package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerManagerTest {
    @Mock
    Player link;
    @Mock
    Player zelda;

    @BeforeEach
    void setUp() throws ConnectFourException {
        ConnectFourException e = new ConnectFourException(ConnectFourError.NO_TIME_LEFT);
        Mockito.lenient().doThrow(e).when(zelda).reduceTime(1000);
        Mockito.lenient().when(zelda.getToken()).thenReturn(Token.RED);
    }

    @Test
    void testNextPlayerShouldNotifyNewCurrPlayerThatItIsHisTurnAndPreviousCurrPlayerThatItIsOpponentTurn() {
        List<Player> players = new ArrayList<>(List.of(link, zelda));
        PlayerManager playerManager = new PlayerManager(players, 0);
        playerManager.nextPlayer();
        Player newCurrPlayer = players.get(1);
        Player previousCurrPlayer = players.get(0);
        Mockito.verify(newCurrPlayer, Mockito.times(1)).play();
        Mockito.verify(previousCurrPlayer, Mockito.times(1)).notifyOpponentTurn(Token.RED);
    }

    @Test
    void testGetCurrPlayerTokenShouldReturnTokenOfTheCurrentPlayer() {
        List<Player> players = new ArrayList<>(List.of(link, zelda));
        int indexCurrPlayer = 1;
        PlayerManager playerManager = new PlayerManager(players, indexCurrPlayer);
        Token expected = Token.RED;
        Token result = playerManager.getCurrPlayerToken();
        Mockito.verify(zelda, Mockito.times(1)).getToken();
        assertEquals(expected, result);
    }

    @Test
    void testReduceCurrPlayerTimeShouldReduceTimeOfTheCurrentPlayer() throws ConnectFourException {
        List<Player> players = new ArrayList<>(List.of(link, zelda));
        int indexCurrPlayer = 0;
        PlayerManager playerManager = new PlayerManager(players, indexCurrPlayer);
        long millis = 1000;
        assertDoesNotThrow(() -> playerManager.reduceCurrPlayerTime(millis));
        Mockito.verify(link, Mockito.times(1)).reduceTime(millis);
    }

    @Test
    void testReduceCurrPlayerTimeShouldThrowExceptionForNoTimeLeft() {
        List<Player> players = new ArrayList<>(List.of(link, zelda));
        int indexCurrPlayer = 1;
        PlayerManager playerManager = new PlayerManager(players, indexCurrPlayer);
        long millis = 1000;
        assertThrows(ConnectFourException.class, () -> playerManager.reduceCurrPlayerTime(millis));
    }

    @Test
    void testDeclareCurrPlayerWinnerShouldDeclareCurrPlayerWinnerAndNextPlayerLoser() {
        List<Player> players = new ArrayList<>(List.of(link, zelda));
        int indexCurrPlayer = 0;
        PlayerManager playerManager = new PlayerManager(players, indexCurrPlayer);
        playerManager.declareCurrPlayerWinner();
        Mockito.verify(link, Mockito.times(1)).notifyPlayerWon();
        Mockito.verify(zelda, Mockito.times(1)).notifyPlayerLost();
    }

    @Test
    void testDeclareNextPlayerWinnerShouldDeclareCurrPlayerLoserAndNextPlayerWinner() {
        List<Player> players = new ArrayList<>(List.of(link, zelda));
        int indexCurrPlayer = 0;
        PlayerManager playerManager = new PlayerManager(players, indexCurrPlayer);
        playerManager.declareNextPlayerWinner();
        Mockito.verify(link, Mockito.times(1)).notifyPlayerLost();
        Mockito.verify(zelda, Mockito.times(1)).notifyPlayerWon();
    }

    @Test
    void testDeclareGameDrawShouldNotifyPlayersThereIsNoWinner() {
        List<Player> players = new ArrayList<>(List.of(link, zelda));
        int indexCurrPlayer = 0;
        PlayerManager playerManager = new PlayerManager(players, indexCurrPlayer);
        playerManager.declareGameDraw();
        Mockito.verify(link, Mockito.times(1)).notifyDraw();
        Mockito.verify(zelda, Mockito.times(1)).notifyDraw();
    }
}