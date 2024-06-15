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
class PlayerTest {

    @Mock
    Time time;
    @Mock
    Observer observer;

    @BeforeEach
    void setUp() throws ConnectFourException {
        ConnectFourException e = new ConnectFourException(ConnectFourError.NO_TIME_LEFT);
        Mockito.lenient().doThrow(e).when(time).reduceTime(1001);
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
}