package game.view.jfx;

import game.dto.GameDto;
import game.dto.PlayerDto;
import game.model.Token;
import game.presenter.ConnectFourPresenter;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;

public class JfxGameCtrl extends Showable {
    private final ConnectFourPresenter connectFourPresenter;
    private boolean isMyTurn;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label playerTimeLabel;
    @FXML
    private Label roundTimeLabel;
    @FXML
    private Label opponentNameLabel;
    @FXML
    private Label opponentTimeLabel;
    @FXML
    private GridPane grid;

    public JfxGameCtrl(ShowStrategy showStrategy, ConnectFourPresenter connectFourPresenter) {
        super(showStrategy);
        this.connectFourPresenter = connectFourPresenter;
    }

    public void initGame(GameDto gameDto) {
        PlayerDto playerDto = gameDto.you();
        PlayerDto opponentPlayerDto = gameDto.opponent();
        playerNameLabel.setText(playerDto.name());
        updatePlayerTime(playerTimeLabel, playerDto.time());
        opponentNameLabel.setText(opponentPlayerDto.name());
        updatePlayerTime(opponentTimeLabel, opponentPlayerDto.time());
        updatePlayerTurn(gameDto.isYourTurn());
        updateRoundTime(gameDto.roundTime());
        initBoard(gameDto.tokens());
    }

    public void updateBoard(Token token, int columnIndex, int rowIndex) {
        Node node = getCircleAtCell(columnIndex, rowIndex);
        String circleClass = token == Token.RED ? "circle-red" : "circle-blue";
        node.getStyleClass().setAll(circleClass);
    }

    public void updateRoundTime(long millis) {
        long seconds = millisToSeconds(millis);
        roundTimeLabel.setText(String.valueOf(seconds));
    }

    public void updatePlayerTime(long millis, boolean isMine) {
        if (isMine) updatePlayerTime(playerTimeLabel, millis);
        else updatePlayerTime(opponentTimeLabel, millis);
    }

    public void updatePlayerTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
        if (isMyTurn) updatePlayerTurn(playerTimeLabel, opponentTimeLabel);
        else updatePlayerTurn(opponentTimeLabel, playerTimeLabel);
    }

    private void handlePlayToken(MouseEvent e) {
        Node node = (Node) e.getSource();
        Integer columnIndex = GridPane.getColumnIndex(node);
        connectFourPresenter.play(columnIndex);
    }

    private void filterHandlerPlayToken(MouseEvent e) {
        if (isMyTurn) e.consume();
    }

    private void updatePlayerTime(Label playerTimeLabel, long millis) {
        long[] minutesAndSeconds = millisToMinutesAndSeconds(millis);
        long minutes = minutesAndSeconds[0];
        long seconds = minutesAndSeconds[1];
        String time = minutes + ":" + seconds;
        playerTimeLabel.setText(time);
    }

    private void updatePlayerTurn(Label toActivate, Label toDeactivate) {
        toActivate.getStyleClass().add("active-time");
        toDeactivate.getStyleClass().remove("active-time");
    }

    private void initBoard(Token[][] tokens) {
        grid.getChildren().clear();

        int cols = tokens.length;
        int rows = tokens[0].length;

        for (int colIndex = 0; colIndex < cols; colIndex++) {
            ColumnConstraints colConst = new ColumnConstraints(60);
            grid.getColumnConstraints().add(colConst);
        }

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            RowConstraints rowConst = new RowConstraints(60);
            grid.getRowConstraints().add(rowConst);
        }

        for (int colIndex = 0; colIndex < cols; colIndex++) {
            for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
                Circle circle = new Circle(15);
                circle.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handlePlayToken);
                circle.addEventFilter(MouseEvent.MOUSE_CLICKED, this::filterHandlerPlayToken);
                circle.getStyleClass().add("circle-white");
                grid.add(circle, colIndex, rowIndex, 1, 1);
            }
        }
    }

    private Node getCircleAtCell(int columnIndex, int rowIndex) {
        Node result = null;
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == columnIndex) {
                result = node;
                break;
            }
        }
        return result;
    }

    private long millisToSeconds(long millis) {
        return millis / 1000;
    }

    private long millisToMinutes(long millis) {
        return millis / 60;
    }

    private long[] millisToMinutesAndSeconds(long millis) {
        long totalSeconds = millisToSeconds(millis);
        long minutes = millisToMinutes(millis);
        long seconds = totalSeconds % 60;
        return new long[]{minutes, seconds};
    }
}
