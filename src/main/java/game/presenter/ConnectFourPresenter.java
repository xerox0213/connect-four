package game.presenter;

import game.dto.GameConfigDto;
import game.model.Game;
import game.model.GameRoom;
import game.oo.ConnectFourEvent;
import game.oo.Observer;
import game.view.ConnectFourView;

public class ConnectFourPresenter implements Observer {
    private final GameRoom gameRoom;
    private Game game;
    private ConnectFourView connectFourView;
    private boolean isItAgainstComputer;

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

    public void configureGame(boolean isItAgainstComputer) {
        this.isItAgainstComputer = isItAgainstComputer;
        connectFourView.showConfigurator();
    }

    public void createGame(GameConfigDto gameConfigDto) {
        if (isItAgainstComputer) {
            game = gameRoom.playAgainstComputer(gameConfigDto, this);
        }
    }

    @Override
    public void update(ConnectFourEvent e, Object data) {

    }
}
