package game.view.jfx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalStrategy implements ShowStrategy {

    private Stage stage;

    @Override
    public void show() {
        stage.show();
    }

    @Override
    public void close() {
        stage.close();
    }

    public void initModal(Parent parent) {
        stage = new Stage();
        stage.setScene(new Scene(parent, 400, 400));
        stage.initModality(Modality.APPLICATION_MODAL);
    }
}
