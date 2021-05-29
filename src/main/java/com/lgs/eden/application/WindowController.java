package com.lgs.eden.application;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.lgs.eden.Main;
import com.lgs.eden.utils.ViewsPath;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Handle window related methods, events, ...
 * This class will be used to change screen.
 *
 * NOT RESIZABLE.
 */
public class WindowController {

    // ------------------------------ STATIC ----------------------------- \\

    /** save windows here **/
    private static BorderPane window;
    private static WindowController instance;

    /** load window **/
    public static BorderPane loadWindow(Stage primaryStage){
        try {
            // todo: load
            URL resource = Main.class.getResource(ViewsPath.FRAME.path);
            if (resource == null) throw new IOException();
            FXMLLoader loader = new FXMLLoader(resource);

            // init
            window = loader.load();
            instance = loader.getController();
            instance.stage = primaryStage;

            return window;
        } catch (IOException e) {
            throw new IllegalStateException("Can't load FXML: frame.fxml");
        }
    }

    /** Init frame using scene. */
    public static void init(BorderlessScene scene, int width, int height) {
        // set move control, meaning place used to move the frame
        scene.setMoveControl(instance.topPane);
        // remove the default css style
        scene.removeDefaultCSS();
        // save attribute
        instance.scene = scene;
        // set size
        window.setPrefSize(width, height);
        // not resizable
        scene.setResizable(false);
        scene.setDoubleClickMaximizeEnabled(false);
        scene.setSnapEnabled(false);
    }

    /** set content as the content of the screen **/
    public static void setScreen(Parent content){ window.setCenter(content); }

    /** change window size **/
    public static void setSize(int width, int height){
        window.setPrefSize(width, height);
        instance.stage.setWidth(width);
        instance.stage.setHeight(height);
        instance.stage.centerOnScreen();
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML // title + minimize + close
    private BorderPane topPane;

    // current scene
    private BorderlessScene scene;
    // current stage
    private Stage stage;

    public WindowController() {}

    // ------------------------------ LISTENERS ----------------------------- \\

    @FXML
    public void onCloseRequest(ActionEvent ignore) {
        // todo: maybe make a class
        Platform.runLater(() -> this.stage.close());
    }

    @FXML
    public void onMinimizeRequest(ActionEvent ignore) {
        // todo: maybe make a class
        Platform.runLater(() -> scene.minimizeStage());
    }
}
