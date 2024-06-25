package game.view.jfx;

import game.dto.GameDto;
import game.dto.PlayerDto;
import game.model.Token;
import game.presenter.ConnectFourPresenter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
    @FXML
    private Button interruptBtn;

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
        HBox container = (HBox) getCircleAtCell(columnIndex, rowIndex);
        Node circle = container.getChildren().getFirst();
        circle.getStyleClass().remove("circle-white");
        String circleClass = token == Token.RED ? "circle-red" : "circle-blue";
        circle.getStyleClass().setAll(circleClass);
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
        handleExitColumn(e);
        HBox container = (HBox) e.getSource();
        Integer columnIndex = GridPane.getColumnIndex(container);
        connectFourPresenter.play(columnIndex);
    }

    private void filterPlayerTurn(MouseEvent e) {
        if (!isMyTurn) e.consume();
    }

    @FXML
    private void handleInterruptGame() {
        connectFourPresenter.interruptGame();
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
        grid.setStyle("-fx-background-color: #DEE2E6");

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
                HBox container = new HBox();
                container.setAlignment(Pos.CENTER);
                container.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handlePlayToken);
                container.addEventFilter(MouseEvent.MOUSE_CLICKED, this::filterPlayerTurn);
                container.addEventHandler(MouseEvent.MOUSE_ENTERED, this::handleEnterColumn);
                container.addEventFilter(MouseEvent.MOUSE_ENTERED, this::filterPlayerTurn);
                container.addEventHandler(MouseEvent.MOUSE_EXITED, this::handleExitColumn);
                container.addEventFilter(MouseEvent.MOUSE_EXITED, this::filterPlayerTurn);
                Circle circle = new Circle(18);
                circle.getStyleClass().add("circle-white");
                container.getChildren().add(circle);
                grid.add(container, colIndex, rowIndex);
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

    private void handleEnterColumn(MouseEvent e) {
        Node node = (Node) e.getSource();
        Integer columnIndex = GridPane.getColumnIndex(node);
        List<Node> columnContainers = getColumnContainers(columnIndex);
        columnContainers.forEach((n) -> n.getStyleClass().add("cell-hover"));
    }

    private void handleExitColumn(MouseEvent e) {
        Node node = (Node) e.getSource();
        Integer columnIndex = GridPane.getColumnIndex(node);
        List<Node> columnContainers = getColumnContainers(columnIndex);
        columnContainers.forEach((n) -> n.getStyleClass().remove("cell-hover"));
    }

    private List<Node> getColumnContainers(int columnIndex) {
        return grid.getChildren().stream().filter(n -> Objects.equals(GridPane.getColumnIndex(n), columnIndex)).toList();
    }
}
