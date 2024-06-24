package game;

import game.model.GameFactory;
import game.model.GameRoom;
import game.presenter.ConnectFourPresenter;
import game.view.jfx.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameFactory gameFactory = new GameFactory();
        GameRoom gameRoom = new GameRoom(gameFactory);
        ConnectFourPresenter connectFourPresenter = new ConnectFourPresenter(gameRoom);

        Parent parent = getRootJfxHomeCtrl(connectFourPresenter);
        Scene scene = new Scene(parent, 800, 700);
        JfxMenuCtrl jfxMenuCtrl = createJfxMenuCtrl(scene, connectFourPresenter);
        JfxConfigCtrl jfxConfigCtrl = createJfxConfigCtrl(connectFourPresenter);
        JfxGameCtrl jfxGameCtrl = createJfxGameCtrl(scene, connectFourPresenter);
        JfxEndCtrl jfxEndCtrl = createJfxEndCtrl(scene, connectFourPresenter);
        JfxConnectFourView jfxConnectFourView = new JfxConnectFourView(jfxMenuCtrl, jfxConfigCtrl, jfxGameCtrl, jfxEndCtrl);
        connectFourPresenter.setConnectFourView(jfxConnectFourView);

        scene.getStylesheets().add(getClass().getClassLoader().getResource("./css/style.css").toExternalForm());
        stage.setOnCloseRequest((e) -> connectFourPresenter.quitGame());
        stage.setScene(scene);
        stage.show();
    }

    private Parent getRootJfxHomeCtrl(ConnectFourPresenter cfp) throws IOException {
        SceneStrategy sceneStrategy = new SceneStrategy();
        JfxHomeCtrl jfxHomeCtrl = new JfxHomeCtrl(sceneStrategy, cfp);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/home.fxml"));
        loader.setController(jfxHomeCtrl);
        return loader.load();
    }

    private JfxMenuCtrl createJfxMenuCtrl(Scene mainScene, ConnectFourPresenter cfp) throws IOException {
        SceneStrategy sceneStrategy = new SceneStrategy();
        JfxMenuCtrl jfxMenuCtrl = new JfxMenuCtrl(sceneStrategy, cfp);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/menu.fxml"));
        loader.setController(jfxMenuCtrl);
        Parent parent = loader.load();
        sceneStrategy.setParent(parent);
        sceneStrategy.setScene(mainScene);
        return jfxMenuCtrl;
    }

    private JfxConfigCtrl createJfxConfigCtrl(ConnectFourPresenter cfp) throws IOException {
        ModalStrategy modalStrategy = new ModalStrategy();
        JfxConfigCtrl jfxConfigCtrl = new JfxConfigCtrl(modalStrategy, cfp);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/configurator.fxml"));
        loader.setController(jfxConfigCtrl);
        Parent parent = loader.load();
        modalStrategy.initModal(parent);
        return jfxConfigCtrl;
    }

    private JfxGameCtrl createJfxGameCtrl(Scene mainScene, ConnectFourPresenter cfp) throws IOException {
        SceneStrategy sceneStrategy = new SceneStrategy();
        JfxGameCtrl jfxGameCtrl = new JfxGameCtrl(sceneStrategy, cfp);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/game.fxml"));
        loader.setController(jfxGameCtrl);
        Parent parent = loader.load();
        sceneStrategy.setParent(parent);
        sceneStrategy.setScene(mainScene);
        return jfxGameCtrl;
    }

    private JfxEndCtrl createJfxEndCtrl(Scene mainScene, ConnectFourPresenter cfp) throws IOException {
        SceneStrategy sceneStrategy = new SceneStrategy();
        JfxEndCtrl jfxEndCtrl = new JfxEndCtrl(sceneStrategy, cfp);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/end.fxml"));
        loader.setController(jfxEndCtrl);
        Parent parent = loader.load();
        sceneStrategy.setParent(parent);
        sceneStrategy.setScene(mainScene);
        return jfxEndCtrl;
    }
}
