package game.presenter;

import game.model.Game;
import game.model.GameRoom;
import game.oo.ConnectFourEvent;
import game.oo.Observer;

public class ConnectFourPresenter implements Observer {
    private final GameRoom gameRoom;
    private Game game;

    public ConnectFourPresenter(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public void setPlayerName(String playerName) {
        gameRoom.setMyPlayerName(playerName);
    }

    @Override
    public void update(ConnectFourEvent e, Object data) {

    }
}
