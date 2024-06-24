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
    private final ConnectFourView connectFourView;
    private boolean isItAgainstComputer;

    public ConnectFourPresenter(GameRoom gameRoom, ConnectFourView connectFourView) {
        this.gameRoom = gameRoom;
        this.connectFourView = connectFourView;
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

    public void play(int columnIndex) {
        game.play(columnIndex);
    }

    @Override
    public void update(ConnectFourEvent e, Object data) {

    }
}
