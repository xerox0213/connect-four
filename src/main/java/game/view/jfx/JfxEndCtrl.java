package game.view.jfx;

import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class JfxEndCtrl extends Showable {
    private final ConnectFourPresenter connectFourPresenter;
    @FXML
    private Label resultLabel;
    @FXML
    private Button newGameBtn;
    @FXML
    private Button quitBtn;

    public JfxEndCtrl(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    public void init(boolean isWon) {
        if (isWon) resultLabel.setText("Victory");
        else resultLabel.setText("Game Over");
    }
}
