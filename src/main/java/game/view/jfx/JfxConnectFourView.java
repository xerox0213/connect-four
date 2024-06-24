package game.view.jfx;

import game.dto.GameDto;
import game.model.Token;
import game.view.ConnectFourView;

public class JfxConnectFourView implements ConnectFourView {
    private final JfxMenuCtrl jfxMenuCtrl;
    private final JfxConfigCtrl jfxConfigCtrl;
    private final JfxGameCtrl jfxGameCtrl;
    private final JfxEndCtrl jfxEndCtrl;

    public JfxConnectFourView(JfxMenuCtrl JfxMenuCtrl, JfxConfigCtrl jfxConfigCtrl, JfxGameCtrl jfxGameCtrl, JfxEndCtrl jfxEndCtrl) {
        this.jfxMenuCtrl = JfxMenuCtrl;
        this.jfxConfigCtrl = jfxConfigCtrl;
        this.jfxGameCtrl = jfxGameCtrl;
        this.jfxEndCtrl = jfxEndCtrl;
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
    public void showGame(GameDto gameDto) {
        jfxGameCtrl.initGame(gameDto);
        jfxGameCtrl.show();
    }

    @Override
    public void showEnd(boolean isWon) {
        jfxEndCtrl.init(isWon);
        jfxEndCtrl.show();
    }

    @Override
    public void showError(String error) {
        System.out.println(error);
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
