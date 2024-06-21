package game.view;

import game.model.Token;

public interface ConnectFourView {
    void showMenu();

    void showConfigurator();

    void showGame();

    void showEnd();

    void showError(String error);

    void updateBoard(Token token, int columnIndex, int rowIndex);

    void updateRoundTime();

    void updatePlayerTime(boolean isMine);

    void updatePlayerTurn(boolean isMyTurn);
}
