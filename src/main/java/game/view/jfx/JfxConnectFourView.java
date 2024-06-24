package game.view.jfx;

import game.dto.GameDto;
import game.model.Token;
import game.view.ConnectFourView;

public class JfxConnectFourView implements ConnectFourView {
    private final JfxMenuCtrl jfxMenuCtrl;
    private final JfxConfigCtrl jfxConfigCtrl;
    private final JfxGameCtrl jfxGameCtrl;

    public JfxConnectFourView(JfxMenuCtrl JfxMenuCtrl, JfxConfigCtrl jfxConfigCtrl, JfxGameCtrl jfxGameCtrl) {
        this.jfxMenuCtrl = JfxMenuCtrl;
        this.jfxConfigCtrl = jfxConfigCtrl;
        this.jfxGameCtrl = jfxGameCtrl;
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
