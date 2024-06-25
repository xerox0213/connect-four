package game.view.jfx;

import game.dto.GameDto;
import game.dto.PlayerDto;
import game.model.Token;
import game.presenter.ConnectFourPresenter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
        playerTimeLabel.getStyleClass().remove("active-time");
        opponentTimeLabel.getStyleClass().remove("active-time");
        updateRoundTime(gameDto.roundTime());
        initBoard(gameDto.tokens());
    }

    public void updateBoard(Token token, int columnIndex, int rowIndex) {
        Node node = getCircleAtCell(columnIndex, rowIndex);
        node.getStyleClass().remove("circle-white");
        String circleClass = token == Token.RED ? "circle-red" : "circle-blue";
        node.getStyleClass().setAll(circleClass);
    }

    public void updateRoundTime(long millis) {
        long seconds = millisToSeconds(millis);
        Platform.runLater(() -> roundTimeLabel.setText(formatTime(seconds)));
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
        if (!isMyTurn) e.consume();
    }

    private void updatePlayerTime(Label playerTimeLabel, long millis) {
        long[] minutesAndSeconds = millisToMinutesAndSeconds(millis);
        long minutes = minutesAndSeconds[0];
        long seconds = minutesAndSeconds[1];
        String time = formatTime(minutes) + ":" + formatTime(seconds);
        Platform.runLater(() -> playerTimeLabel.setText(time));
    }

    private void updatePlayerTurn(Label toActivate, Label toDeactivate) {
        toActivate.getStyleClass().add("active-time");
        toDeactivate.getStyleClass().remove("active-time");
    }

    private void initBoard(Token[][] tokens) {
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setStyle("-fx-background-color: #CED4DA");

        int cols = tokens.length;
        int rows = tokens[0].length;

        for (int colIndex = 0; colIndex < cols; colIndex++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setMinWidth(60);
            colConst.setPrefWidth(60);
            colConst.setMaxWidth(60);
            colConst.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(colConst);
        }

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setMinHeight(60);
            rowConst.setPrefHeight(60);
            rowConst.setMaxHeight(60);
            rowConst.setValignment(VPos.CENTER);
            grid.getRowConstraints().add(rowConst);
        }

        for (int colIndex = 0; colIndex < cols; colIndex++) {
            for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
                Circle circle = new Circle(18);
                circle.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handlePlayToken);
                circle.addEventFilter(MouseEvent.MOUSE_CLICKED, this::filterHandlerPlayToken);
                circle.getStyleClass().add("circle-white");
                grid.add(circle, colIndex, rowIndex);
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

    private long secondsToMinutes(long millis) {
        return millis / 60;
    }

    private long[] millisToMinutesAndSeconds(long millis) {
        long totalSeconds = millisToSeconds(millis);
        long minutes = secondsToMinutes(totalSeconds);
        long seconds = totalSeconds % 60;
        return new long[]{minutes, seconds};
    }

    private String formatTime(long time) {
        return time <= 9 ? "0" + time : String.valueOf(time);
    }
}
