package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
}