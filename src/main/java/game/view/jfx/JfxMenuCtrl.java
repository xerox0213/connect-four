package game.view.jfx;

import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;

public class JfxMenuCtrl extends Showable {
    private final ConnectFourPresenter connectFourPresenter;

    public JfxMenuCtrl(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    @FXML
    private void handleClickPlayAgainstComputerBtn() {
        connectFourPresenter.configureGame(true);
    }

    @FXML
    private void handleClickPlayWithFriend() {
        connectFourPresenter.configureGame(false);
    }
}
