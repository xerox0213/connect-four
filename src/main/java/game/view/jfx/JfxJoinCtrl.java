package game.view.jfx;

import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class JfxJoinCtrl extends Showable {
    private final ConnectFourPresenter connectFourPresenter;
    @FXML
    private TextField ipTextField;
    @FXML
    private TextField portTextField;

    public JfxJoinCtrl(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    @FXML
    private void handleClickJoin() {
        String ip = ipTextField.getText();
        String port = portTextField.getText();
        ipTextField.clear();
        portTextField.clear();
        close();
        connectFourPresenter.joinGame(ip, port);
    }
}
