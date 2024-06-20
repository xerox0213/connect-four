package game.view.jfx;

import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;

public class JfxMenuController extends Showable {
    private final ConnectFourPresenter connectFourPresenter;

    public JfxMenuController(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    @FXML
    private void handleClickPlayAgainstComputerBtn() {
        connectFourPresenter.configureGame(true);
    }

}
