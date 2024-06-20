package game.presenter;

import game.model.Game;
import game.model.GameRoom;

public class ConnectFourPresenter {
    private final GameRoom gameRoom;
    private Game game;

    public ConnectFourPresenter(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
}
