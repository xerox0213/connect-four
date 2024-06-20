package game.view.jfx;

import game.dto.GameConfigDto;
import game.model.BoardSize;
import game.model.FirstPlayer;
import game.model.PlayerTime;
import game.model.RoundTime;
import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class JfxConfiguratorController extends Showable implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initBoardSizeComboBox();
        initLapTimeComboBox();
        initMinutesPerPlayerComboBox();
        initFirstPlayerComboBox();
    }

    @FXML
    private void handleClickContinueBtn() {
        BoardSize boardSize = boardSizeComboBox.getValue();
        RoundTime roundTime = lapTimeComboBox.getValue();
        PlayerTime playerTime = minutesPerPlayerComboBox.getValue();
        FirstPlayer firstPlayer = firstPlayerComboBox.getValue();
        GameConfigDto gameConfigDto = new GameConfigDto(boardSize, playerTime, roundTime, firstPlayer);
        boardSizeComboBox.setValue(BoardSize.values()[0]);
        lapTimeComboBox.setValue(RoundTime.values()[0]);
        minutesPerPlayerComboBox.setValue(PlayerTime.values()[0]);
        firstPlayerComboBox.setValue(FirstPlayer.values()[0]);
        connectFourPresenter.createGame(gameConfigDto);
    }

    private void initBoardSizeComboBox() {
        BoardSize[] boardSizes = BoardSize.values();
        boardSizeComboBox.getItems().setAll(boardSizes);
        boardSizeComboBox.setValue(boardSizes[0]);
    }

    private void initLapTimeComboBox() {
        RoundTime[] roundTimes = RoundTime.values();
        lapTimeComboBox.getItems().setAll(roundTimes);
        lapTimeComboBox.setValue(roundTimes[0]);
    }

    private void initMinutesPerPlayerComboBox() {
        PlayerTime[] playerTimes = PlayerTime.values();
        minutesPerPlayerComboBox.getItems().setAll(playerTimes);
        minutesPerPlayerComboBox.setValue(playerTimes[0]);
    }

    private void initFirstPlayerComboBox() {
        FirstPlayer[] firstPlayers = FirstPlayer.values();
        firstPlayerComboBox.getItems().setAll(firstPlayers);
        firstPlayerComboBox.setValue(firstPlayers[0]);
    }
}
