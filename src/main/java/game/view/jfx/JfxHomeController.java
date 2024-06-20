package game.view.jfx;

import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class JfxHomeController extends Showable {
    private final ConnectFourPresenter connectFourPresenter;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Button confirmPlayerNameBtn;

    public JfxHomeController(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    @FXML
    public void handleConfirmPlayerName() {
        String playerName = playerNameLabel.getText();
        connectFourPresenter.setPlayerName(playerName);
    }
}
