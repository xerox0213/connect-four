package game.model;

import game.dto.GameConfigDto;
import game.oo.Observer;

public class GameRoom {
    private String playerName;
    private final GameFactory gameFactory;

    public GameRoom(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    public void setMyPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Game playAgainstComputer(GameConfigDto gameConfigDto, Observer presenterObserver) {
        return gameFactory.createGame(gameConfigDto, playerName, "Bip Boop", presenterObserver, null);
    }
}
