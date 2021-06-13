package com.lgs.eden.application;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        FXMLLoader loader = Utility.loadView(ViewsPath.FRAME.path);
        // init
        window = (BorderPane) Utility.loadViewPane(loader);
        instance = loader.getController();
        instance.stage = primaryStage;
        // return
        return window;
    }

    /** Init frame using scene. */
    public static void init(BorderlessScene scene, int width, int height) {
        // set move control, meaning place used to move the frame
        scene.setMoveControl(instance.topPane);
        // save attribute
        instance.scene = scene;
        // set size
        window.setPrefSize(width, height);
    }

    /** set content as the content of the screen **/
    public static void setScreen(Parent content){ window.setCenter(content); }

    /** returns primary stage **/
    public static Stage getStage() { return instance != null ? instance.stage : oldStage; }

    private static Stage oldStage;

    public static void setStage(Stage oldStage) {
        WindowController.oldStage = oldStage;
    }

    /** change window size **/
    public static void setSize(int width, int height) {
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

    public WindowController() {
    }

    // ------------------------------ LISTENERS ----------------------------- \\

    @FXML
    public void onCloseRequest() {
        // todo: maybe make a class
        Platform.runLater(() -> {
            this.stage.close();
            // call handler
            new ApplicationCloseHandler().handle(null);
        });
    }

    @FXML
    public void onMinimizeRequest() {
        // todo: maybe make a class
        Platform.runLater(() -> scene.minimizeStage());
    }
}
