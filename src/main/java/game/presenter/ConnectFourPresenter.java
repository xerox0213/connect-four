package game.presenter;

import game.model.Game;
import game.model.GameRoom;
import game.oo.ConnectFourEvent;
import game.oo.Observer;
import game.view.ConnectFourView;

public class ConnectFourPresenter implements Observer {
    private final GameRoom gameRoom;
    private Game game;
    private ConnectFourView connectFourView;

    public ConnectFourPresenter(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public void setPlayerName(String playerName) {
        if (playerName.isEmpty()) {
            connectFourView.showError("Enter a valid player name !");
        } else {
            gameRoom.setMyPlayerName(playerName);
            connectFourView.showMenu();
        }
    }

    public void configureGame() {
        connectFourView.showConfigurator();
    }

    @Override
    public void update(ConnectFourEvent e, Object data) {

    }
}
