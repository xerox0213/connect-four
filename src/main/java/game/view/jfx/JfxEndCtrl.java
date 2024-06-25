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

    public JfxEndCtrl(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    @FXML
    public void handleStartNewGame() {
        connectFourPresenter.showMenu();
    }

    public void showVictory() {
        resultLabel.setText("Victory");
        show();
    }

    public void showGameOver() {
        resultLabel.setText("Game Over");
        show();
    }

    public void showDraw() {
        resultLabel.setText("Draw");
        show();
    }
}
