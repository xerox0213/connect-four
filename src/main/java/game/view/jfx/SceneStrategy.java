package game.view.jfx;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneStrategy implements ShowStrategy {
    private final Parent parent;
    private Scene scene;

    public SceneStrategy(Parent parent) {
        this.parent = parent;
    }

    @Override
    public void show() {
        scene.setRoot(parent);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
