package game.model;

import game.dto.GameConfigDto;
import game.oo.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameRoomTest {
    @Mock
    GameFactory gameFactory;
    @Mock
    Game gameAgainstComputer;
    @Mock
    Observer observer;
    GameConfigDto gameConfigDto;
    GameRoom gameRoom;
    String playerName;

    @BeforeEach
    void setUp() {
        Mockito.lenient()
                .when(gameFactory.createGameAgainstComputer(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(gameAgainstComputer);
        BoardSize boardSize = BoardSize.EIGHT_BY_SEVEN;
        PlayerTime playerTime = PlayerTime.ONE_MINUTE;
        RoundTime roundTime = RoundTime.TEN_SECONDS;
        FirstPlayer firstPlayer = FirstPlayer.HOST;
        gameConfigDto = new GameConfigDto(boardSize, playerTime, roundTime, firstPlayer);
        gameRoom = new GameRoom(gameFactory);
        playerName = "Link";
    }

    @Test
    void testPlayAgainstComputerShouldReturnGameAgainstComputer() {
        gameRoom.setMyPlayerName(playerName);
        Game result = gameRoom.playAgainstComputer(gameConfigDto, observer);
        Mockito.verify(gameFactory, Mockito.times(1)).createGameAgainstComputer(gameConfigDto, playerName, observer, "Bip Boop");
        assertEquals(gameAgainstComputer, result);
    }
}