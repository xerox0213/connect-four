package game.view.jfx;

import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JfxWaitingCtrl extends Showable {
    private final ConnectFourPresenter connectFourPresenter;
    @FXML
    private Label ipLabel;
    @FXML
    private Label portLabel;

    public JfxWaitingCtrl(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    @FXML
    public void handleClickCancelBtn() {
        connectFourPresenter.cancelGame();
    }

    public void init(String ip, String port) {
        ipLabel.setText(ip);
        portLabel.setText(port);
    }

}
