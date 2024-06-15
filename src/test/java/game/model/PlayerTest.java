package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerTest {

    @Mock
    Time time;

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
}