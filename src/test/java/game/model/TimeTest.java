package game.model;

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
}