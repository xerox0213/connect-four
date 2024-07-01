package game.presenter;

import game.dto.*;
import game.model.Game;
import game.model.GameRoom;
import game.oo.ConnectFourEvent;
import game.oo.Observer;
import game.view.ConnectFourView;

import java.io.IOException;

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
            showMenu();
        }
    }

    public void configureGame(boolean isItAgainstComputer) {
        this.isItAgainstComputer = isItAgainstComputer;
        connectFourView.showConfigurator();
    }

    public void createGame(GameConfigDto gameConfigDto) {
        if (isItAgainstComputer) {
            game = gameRoom.playAgainstComputer(gameConfigDto, this);
            game.start();
        } else {
            try {
                game = gameRoom.playWithFriend(gameConfigDto, this);
                game.start();
            } catch (IOException ioe) {
                connectFourView.showError("Impossible de créer une partie en ligne");
            }
        }
    }

    public void play(int columnIndex) {
        game.play(columnIndex);
    }

    public void setConnectFourView(ConnectFourView connectFourView) {
        this.connectFourView = connectFourView;
    }

    public void showMenu() {
        connectFourView.showMenu();
    }

    public void interruptGame() {
        if (game != null) {
            game.stop();
            showMenu();
        }
    }

    @Override
    public void update(ConnectFourEvent e, Object data) {
        if (e == ConnectFourEvent.GAME_INIT) {
            GameDto gameDto = (GameDto) data;
            connectFourView.showGame(gameDto);
        } else if (e == ConnectFourEvent.MY_TURN) {
            connectFourView.updatePlayerTurn(true);
        } else if (e == ConnectFourEvent.OPPONENT_TURN) {
            connectFourView.updatePlayerTurn(false);
        } else if (e == ConnectFourEvent.ROUND_TIME_UPDATED) {
            long millis = (long) data;
            connectFourView.updateRoundTime(millis);
        } else if (e == ConnectFourEvent.PLAYER_TIME_UPDATED) {
            PlayerDto playerDto = (PlayerDto) data;
            connectFourView.updatePlayerTime(playerDto.time(), true);
        } else if (e == ConnectFourEvent.OPPONENT_TIME_UPDATED) {
            PlayerDto opponentPlayerDto = (PlayerDto) data;
            connectFourView.updatePlayerTime(opponentPlayerDto.time(), false);
        } else if (e == ConnectFourEvent.BOARD_UPDATED) {
            MoveDto moveDto = (MoveDto) data;
            connectFourView.updateBoard(moveDto.token(), moveDto.columnIndex(), moveDto.rowIndex());
        } else if (e == ConnectFourEvent.VICTORY) {
            connectFourView.showVictory();
        } else if (e == ConnectFourEvent.GAME_OVER) {
            connectFourView.showGameOver();
        } else if (e == ConnectFourEvent.GAME_DRAW) {
            connectFourView.showDraw();
        } else if (e == ConnectFourEvent.CONNECT_FOUR_SERVER_STARTED) {
            ServerConnectionDto serverConnectionDto = (ServerConnectionDto) data;
            connectFourView.showWaitingForOpponent(serverConnectionDto.ip(), serverConnectionDto.port());
        }
    }
}
