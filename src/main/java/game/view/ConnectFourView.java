package game.view;

import game.dto.GameDto;
import game.model.Token;

public interface ConnectFourView {
    void showMenu();

    void showConfigurator();

    void showJoin();

    void showWaitingForOpponent(String ip, String port);

    void showGame(GameDto gameDto);

    void showVictory();

    void showGameOver();

    void showDraw();

    void showError(String title, String header, String error);

    void updateBoard(Token token, int columnIndex, int rowIndex);

    void updateRoundTime(long millis);

    void updatePlayerTime(long millis, boolean isMine);

    void updatePlayerTurn(boolean isMyTurn);
}
