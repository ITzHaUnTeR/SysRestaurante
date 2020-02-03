package org.sysRestaurante.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.sysRestaurante.applet.AppFactory;
import org.sysRestaurante.dao.ComandaDao;
import org.sysRestaurante.dao.TableDao;
import org.sysRestaurante.model.Order;
import org.sysRestaurante.util.DBConnection;
import org.sysRestaurante.util.ExceptionHandler;
import org.sysRestaurante.util.LoggerHandler;
import org.sysRestaurante.util.Encryption;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class MainGUI extends Application {

    private static final Logger LOGGER = new LoggerHandler().getGenericConsoleHandler(MainGUI.class.getName());
    private static MainGUIController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setHeight(550);
        primaryStage.setWidth(800);

        final String JAVA_VERSION = System.getProperty("java.version");
        final String JAVAFX_VERSION = System.getProperties().get("javafx.runtime.version").toString();
        LOGGER.info("Java JDK Runtime Version: " + JAVA_VERSION);
        LOGGER.info("JavaFX Runtime Version: " + JAVAFX_VERSION);

        startProgram(primaryStage);
        Encryption.setKey("Jaguaric@3105");

        LOGGER.info("Program started with " + ExceptionHandler.getGlobalExceptionsCount() + " errors.");
    }

    private static Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane wrapperPane = loader.load(MainGUI.class.getResourceAsStream(SceneNavigator.MAIN));

        mainController = loader.getController();
        LOGGER.info("Wrapper pane successfully loaded.");
        mainController.setMainPanePadding(300, 70, 300, 70);
        wrapperPane.setOnMouseClicked(e -> wrapperPane.requestFocus());

        SceneNavigator.setMainGUIController(mainController);
        SceneNavigator.loadScene(SceneNavigator.LOGIN);
        return wrapperPane;
    }

    public static void clear() {
        Stage stage = (Stage) mainController.getScene().getWindow();
        stage.close();
    }

    public static void startProgram(Stage stage) throws IOException {
        stage.setTitle("SysRestaurante");
        stage.setScene(createScene(loadMainPane()));
        stage.setMinHeight(390);
        stage.setMinWidth(465);
        stage.show();

        stage.setOnCloseRequest(e -> {
            if (AppFactory.getUserDao() != null) {
                AppFactory.getLoginController().storeLastSessionDuration();
            }
            LOGGER.info(DBConnection.getGlobalDBRequestsCount() + " requests to database.");
            Platform.exit();
            LOGGER.info("Program exited.");
            System.exit(0);
        });
    }

    public static void restartProgram() throws IOException {
        clear();
        startProgram(new Stage());
    }

    public static MainGUIController getMainController() {
        return mainController;
    }

    private static Scene createScene(Pane mainPane) {
        return new Scene(mainPane);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void test_tables() {
        List<ComandaDao> comandas = new Order().getComandasByIdCashier(304);
        for (ComandaDao table : comandas) {
            System.out.println("Comanda #" + table.getIdComanda() + " aberta.");
        }
        Platform.exit();
    }
}
