package gui;

import util.LoggerHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class MainGUI extends Application {

    private static final Logger LOGGER = new LoggerHandler().getGenericConsoleHandler(MainGUI.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SysRestaurante");
        primaryStage.setScene(createScene(loadMainPane()));
        primaryStage.show();

        LOGGER.info("Program started with no errors.");
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane wrapperPane = loader.load(
                getClass().getResourceAsStream(SceneNavigator.MAIN)
        );

        MainGUIController mainController = loader.getController();

        LOGGER.info("Wrapper pane successfully loaded.");

        SceneNavigator.setMainGUIController(mainController);
        SceneNavigator.loadScene(SceneNavigator.LOGIN);

        return wrapperPane;
    }

    private Scene createScene(Pane mainPane) {
        return new Scene(mainPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
