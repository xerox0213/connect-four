package game.view.jfx;

import game.model.BoardSize;
import game.model.FirstPlayer;
import game.model.PlayerTime;
import game.model.RoundTime;
import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class JfxConfiguratorController extends Showable {
    private final ConnectFourPresenter connectFourPresenter;
    @FXML
    private ComboBox<BoardSize> boardSizeComboBox;
    @FXML
    private ComboBox<RoundTime> lapTimeComboBox;
    @FXML
    private ComboBox<PlayerTime> minutesPerPlayerComboBox;
    @FXML
    private ComboBox<FirstPlayer> firstPlayerComboBox;

    public JfxConfiguratorController(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    private void initBoardSizeComboBox() {
        BoardSize[] boardSizes = BoardSize.values();
        boardSizeComboBox.getItems().setAll(boardSizes);
        boardSizeComboBox.setValue(boardSizes[0]);
    }
}
