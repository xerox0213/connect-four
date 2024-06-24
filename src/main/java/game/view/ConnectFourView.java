package game.view;

import game.dto.GameDto;
import game.model.Token;

public interface ConnectFourView {
    void showMenu();

    void showConfigurator();

    void showGame(GameDto gameDto);

    void showEnd();

    void showError(String error);

    void updateBoard(Token token, int columnIndex, int rowIndex);

    void updateRoundTime(long millis);

    void updatePlayerTime(long millis, boolean isMine);

    void updatePlayerTurn(boolean isMyTurn);
}
