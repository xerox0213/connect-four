package game.view.jfx;

public class JfxConnectFourView {
    private final JfxMenuCtrl jfxMenuCtrl;
    private final JfxConfigCtrl jfxConfigCtrl;
    private final JfxGameCtrl jfxGameCtrl;

    public JfxConnectFourView(JfxMenuCtrl JfxMenuCtrl, JfxConfigCtrl jfxConfigCtrl, JfxGameCtrl jfxGameCtrl) {
        this.jfxMenuCtrl = JfxMenuCtrl;
        this.jfxConfigCtrl = jfxConfigCtrl;
        this.jfxGameCtrl = jfxGameCtrl;
    }
}
