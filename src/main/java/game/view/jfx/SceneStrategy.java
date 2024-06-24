package game.view.jfx;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneStrategy implements ShowStrategy {
    private Parent parent;
    private Scene scene;

    @Override
    public void show() {
        scene.setRoot(parent);
    }

    @Override
    public void close() {
        scene.setRoot(null);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
