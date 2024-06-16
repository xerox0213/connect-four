package game.model;

import game.dto.PlayerDto;
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
class PlayerTest {

    @Mock
    Time time;
    @Mock
    Observer observer;

    @BeforeEach
    void setUp() throws ConnectFourException {
        ConnectFourException e = new ConnectFourException(ConnectFourError.NO_TIME_LEFT);
        Mockito.lenient().doThrow(e).when(time).reduceTime(1001);
        Mockito.lenient().when(time.isTimeLeft()).thenReturn(true);
        Mockito.lenient().when(time.getMillis()).thenReturn(1000L);
    }

    @Test
    void testReduceTimeShouldCallReduceTimeMethodOfTimeObject() throws ConnectFourException {
        Player player = new Player("test", Token.RED, time, new HashSet<>());
        long millis = 1000;
        player.reduceTime(1000);
        Mockito.verify(time, Mockito.times(1)).reduceTime(millis);
    }

    @Test
    void testReduceTimeShouldLetPassExceptionThrownByTimeObject() {
        Player player = new Player("test", Token.RED, time, new HashSet<>());
        assertThrows(ConnectFourException.class, () -> player.reduceTime(1001));
    }

    @Test
    void testReduceTimeShouldNotifyPlayerForTimeUpdated() throws ConnectFourException {
        Set<Observer> observers = new HashSet<>(Set.of(observer));
        String name = "test";
        Token token = Token.RED;
        Player player = new Player(name, token, time, observers);
        player.reduceTime(1000);
        ConnectFourEvent event = ConnectFourEvent.PLAYER_TIME_UPDATED;
        PlayerDto playerDto = new PlayerDto(name, token, time.getMillis());
        Mockito.verify(observer, Mockito.times(1)).update(event, playerDto);
    }

    @Test
    void testAddObserverShouldAddTheGivenObserver() {
        Set<Observer> observers = new HashSet<>();
        Player player = new Player("test", Token.RED, time, observers);
        player.addObserver(observer);
        assertTrue(observers.contains(observer));
    }

    @Test
    void testNotifyObserversShouldNotifyObservers() {
        Set<Observer> observers = new HashSet<>(Set.of(observer));
        Token token = Token.RED;
        Player player = new Player("test", token, time, observers);
        ConnectFourEvent event = ConnectFourEvent.MY_TURN;
        player.notifyObservers(event, token);
        Mockito.verify(observer, Mockito.times(1)).update(event, token);
    }

    @Test
    void testPlayShouldNotifyObserversThatItIsItsTurn() {
        Set<Observer> observers = new HashSet<>(Set.of(observer));
        Token token = Token.RED;
        Player player = new Player("test", token, time, observers);
        ConnectFourEvent event = ConnectFourEvent.MY_TURN;
        player.notifyObservers(event, token);
        Mockito.verify(observer, Mockito.times(1)).update(event, token);
    }

    @Test
    void testNotifyOpponentTurnShouldNotifyObserverThatIsTheOpponentTurn() {
        Set<Observer> observers = new HashSet<>(Set.of(observer));
        Player player = new Player("test", Token.RED, time, observers);
        Token opponentToken = Token.BLUE;
        ConnectFourEvent event = ConnectFourEvent.OPPONENT_TURN;
        player.notifyOpponentTurn(opponentToken);
        Mockito.verify(observer, Mockito.times(1)).update(event, opponentToken);
    }

    @Test
    void testNotifyPlayerLostShouldNotifyObserverThatHeHasLost() {
        Set<Observer> observers = new HashSet<>(Set.of(observer));
        Player player = new Player("test", Token.RED, time, observers);
        ConnectFourEvent event = ConnectFourEvent.GAME_OVER;
        player.notifyPlayerLost();
        Mockito.verify(observer, Mockito.times(1)).update(event, null);
    }

    @Test
    void testNotifyPlayerWonShouldNotifyObserverThatHeHasWon() {
        Set<Observer> observers = new HashSet<>(Set.of(observer));
        Player player = new Player("test", Token.RED, time, observers);
        ConnectFourEvent event = ConnectFourEvent.VICTORY;
        player.notifyPlayerWon();
        Mockito.verify(observer, Mockito.times(1)).update(event, null);
    }

    @Test
    void testNotifyDrawShouldNotifyObserverThatItIsAGameDraw() {
        Set<Observer> observers = new HashSet<>(Set.of(observer));
        Player player = new Player("test", Token.RED, time, observers);
        ConnectFourEvent event = ConnectFourEvent.GAME_DRAW;
        player.notifyDraw();
        Mockito.verify(observer, Mockito.times(1)).update(event, null);
    }

    @Test
    void testIsTimeLeftShouldCallMethodIsTimeLeftOfTimeObject() {
        Player player = new Player("test", Token.RED, time, new HashSet<>());
        boolean isTimeLeft = player.isTimeLeft();
        Mockito.verify(time, Mockito.times(1)).isTimeLeft();
        assertTrue(isTimeLeft);
    }

    @Test
    void testGetPlayerDtoShouldReturnPlayerDtoBasedOnThePlayer() {
        String name = "test";
        Token token = Token.RED;
        long timeMillis = time.getMillis();
        Player player = new Player(name, token, time, new HashSet<>());
        PlayerDto expected = new PlayerDto(name, token, timeMillis);
        PlayerDto result = player.getPlayerDto();
        assertEquals(expected, result);
    }
}