package game.view.jfx;

import game.dto.GameDto;
import game.model.Token;
import game.view.ConnectFourView;
import javafx.scene.control.Alert;

public class JfxConnectFourView implements ConnectFourView {
    private final JfxMenuCtrl jfxMenuCtrl;
    private final JfxConfigCtrl jfxConfigCtrl;
    private final JfxJoinCtrl jfxJoinCtrl;
    private final JfxGameCtrl jfxGameCtrl;
    private final JfxEndCtrl jfxEndCtrl;
    private final JfxWaitingCtrl jfxWaitingCtrl;

    public JfxConnectFourView(JfxMenuCtrl jfxMenuCtrl, JfxConfigCtrl jfxConfigCtrl, JfxJoinCtrl jfxJoinCtrl, JfxGameCtrl jfxGameCtrl, JfxEndCtrl jfxEndCtrl, JfxWaitingCtrl jfxWaitingCtrl) {
        this.jfxMenuCtrl = jfxMenuCtrl;
        this.jfxConfigCtrl = jfxConfigCtrl;
        this.jfxJoinCtrl = jfxJoinCtrl;
        this.jfxGameCtrl = jfxGameCtrl;
        this.jfxEndCtrl = jfxEndCtrl;
        this.jfxWaitingCtrl = jfxWaitingCtrl;
    }

    @Override
    public void showMenu() {
        jfxMenuCtrl.show();
    }

    @Override
    public void showConfigurator() {
        jfxConfigCtrl.show();
    }

    @Override
    public void showJoin() {
        jfxJoinCtrl.show();
    }

    @Override
    public void showWaitingForOpponent(String ip, String port) {
        jfxWaitingCtrl.init(ip, port);
        jfxWaitingCtrl.show();
    }

    @Override
    public void showGame(GameDto gameDto) {
        jfxGameCtrl.initGame(gameDto);
        jfxGameCtrl.show();
    }

    @Override
    public void showVictory() {
        jfxEndCtrl.showVictory();
    }

    @Override
    public void showGameOver() {
        jfxEndCtrl.showGameOver();
    }

    @Override
    public void showDraw() {
        jfxEndCtrl.showDraw();
    }

    @Override
    public void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void updateBoard(Token token, int columnIndex, int rowIndex) {
        jfxGameCtrl.updateBoard(token, columnIndex, rowIndex);
    }

    @Override
    public void updateRoundTime(long millis) {
        jfxGameCtrl.updateRoundTime(millis);
    }

    @Override
    public void updatePlayerTime(long millis, boolean isMine) {
        jfxGameCtrl.updatePlayerTime(millis, isMine);
    }

    @Override
    public void updatePlayerTurn(boolean isMyTurn) {
        jfxGameCtrl.updatePlayerTurn(isMyTurn);
    }
}
