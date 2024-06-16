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

}