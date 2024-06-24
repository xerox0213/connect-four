package game.view.jfx;

public abstract class Showable {
    private final ShowStrategy showStrategy;

    public Showable(ShowStrategy showStrategy) {
        this.showStrategy = showStrategy;
    }

    public void show() {
        showStrategy.show();
    }

    public void close() {
        showStrategy.close();
    }
}
