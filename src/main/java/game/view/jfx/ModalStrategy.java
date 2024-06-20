package game.view.jfx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalStrategy implements ShowStrategy {
    private final Stage stage;

    public ModalStrategy(Parent parent, Stage stage, double v, double v1) {
        this.stage = stage;
        stage.setScene(new Scene(parent, v, v1));
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public void show() {
        stage.show();
    }
}
