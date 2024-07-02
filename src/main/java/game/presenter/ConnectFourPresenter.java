package game.presenter;

import game.dto.*;
import game.model.Game;
import game.model.GameRoom;
import game.model.Token;
import game.oo.ConnectFourEvent;
import game.oo.Observer;
import game.view.ConnectFourView;

import java.io.IOException;

public class ConnectFourPresenter implements Observer {
    private final GameRoom gameRoom;
    private Game game;
    private ConnectFourView connectFourView;
    private Token playerId;
    private boolean isItAgainstComputer;

    public ConnectFourPresenter(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public void setPlayerName(String playerName) {
        if (playerName.isEmpty()) {
            String title = "Invalid input";
            String header = "Invalid player name";
            String content = "The name should not be empty.";
            connectFourView.showError(title, header, content);
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
                String title = "Game error";
                String header = "Unable to create game";
                String content = "It is impossible to create a game. Please try again.";
                connectFourView.showError(title, header, content);
            }
        }
    }

    public void showJoin() {
        connectFourView.showJoin();
    }

    public void joinGame(String ip, String port) {
        try {
            int portInt = Integer.parseInt(port);
            game = gameRoom.joinGame(ip, portInt, this);
            game.start();
        } catch (NumberFormatException e) {
            String title = "Invalid input";
            String header = "Invalid port number";
            String content = "Port number must be a numeric value.";
            connectFourView.showError(title, header, content);
        } catch (IOException e) {
            String title = "Connection error";
            String header = "Unable to join game";
            String content = "It was not possible to join the game. Please check your network connection and try again.";
            connectFourView.showError(title, header, content);
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
            game.stop(playerId);
            showMenu();
        }
    }

    public void cancelGame() {
        game.stop(playerId);
        showMenu();
        game = null;
    }

    @Override
    public void update(ConnectFourEvent e, Object data) {
        if (e == ConnectFourEvent.GAME_INIT) {
            GameDto gameDto = (GameDto) data;
            this.playerId = gameDto.you().token();
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
