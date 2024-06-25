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
        Scene scene = new Scene(parent, 400, 400);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
    }
}
