package game.model;

import game.exception.ConnectFourException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    @Test
    void testReduceTimeShouldThrowExceptionForNoTimeLeft() {
        long initialMillis = 1000;
        Time time = new Time(initialMillis);
        assertThrows(ConnectFourException.class, () -> time.reduceTime(1001));
    }
}