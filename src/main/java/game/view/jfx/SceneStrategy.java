package game.view.jfx;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneStrategy implements ShowStrategy {
    private final Parent parent;
    private final Scene scene;

    public SceneStrategy(Parent parent, Scene scene) {
        this.parent = parent;
        this.scene = scene;
    }

    @Override
    public void show() {
        scene.setRoot(parent);
    }
}
