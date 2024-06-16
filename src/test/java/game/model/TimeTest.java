package game.model;

import game.exception.ConnectFourException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    @Test
    void testReduceTimeShouldReduceTime() {
        long initialMillis = 2000;
        Time time = new Time(initialMillis);
        long millis = 1000;
        assertDoesNotThrow(() -> time.reduceTime(millis));
        long expected = initialMillis - millis;
        long result = time.getMillis();
        assertEquals(expected, result);
    }

    @Test
    void testReduceTimeShouldThrowExceptionForNoTimeLeft() {
        long initialMillis = 1000;
        Time time = new Time(initialMillis);
        long millis = 1001;
        assertThrows(ConnectFourException.class, () -> time.reduceTime(millis));
    }

    @Test
    void testIsTimeLeftShouldReturnTrue() {
        long initialMillis = 1000;
        Time time = new Time(initialMillis);
        assertTrue(time.isTimeLeft());
    }

    @Test
    void testIsTimeLeftShouldReturnFalse() {
        long initialMillis = 0;
        Time time = new Time(initialMillis);
        assertFalse(time.isTimeLeft());
    }
}