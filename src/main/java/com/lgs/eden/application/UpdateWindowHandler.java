package com.lgs.eden.application;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.login.Login;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Check for updates, if none then run
 * the application but if we have one then install.
 */
public class UpdateWindowHandler {

    // state of our installer
    enum State {
        LOOKING_FOR_UPDATE,
        DOWNLOAD_UPDATE,
        STARTING_INSTALLATION,
        CLIENT_STARTING;

        @Override
        public String toString() { return name().toLowerCase(); }
    }

    private static Stage oldStage;
    private static UpdateWindowHandler controller;

    /** Show update window */
    public static void start(Stage primaryStage){
        // save for later
        oldStage = primaryStage;

        // init installer
        FXMLLoader loader = Utility.loadView(ViewsPath.UPDATE.path);
        VBox parent = (VBox) Utility.loadViewPane(loader);
        controller = loader.getController();
        // make a borderless frame
        BorderlessScene scene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, parent,0, 0);
        // remove the default css style
        scene.removeDefaultCSS();
        // not resizable
        scene.setResizable(false);
        scene.setDoubleClickMaximizeEnabled(false);
        scene.setSnapEnabled(false);

        // set background and fill as transparent
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        // init
        controller.setState(State.LOOKING_FOR_UPDATE);

        // call basic functions
        formalizeStage(primaryStage, scene);

        // call check for update runnable
        new Thread(new CheckForUpdateRunnable()).start();
    }

    // ------------------------------ JAVAFX ----------------------------- \\

    @FXML // application current version
    public Label version;
    @FXML // shown to the user
    public Label message;

    public UpdateWindowHandler() {
    }

    // set label text
    private void setState(State state) {
        this.message.setText(Translate.getTranslation(state.toString()));
    }

    // ------------------------------ UTILS ----------------------------- \\

    /** Init stage with our parameters */
    private static void formalizeStage(Stage primaryStage, Scene scene) {
        // then set scene
        primaryStage.setScene(scene);
        // window title
        primaryStage.setTitle(Config.APP_NAME);
        // window icon
        primaryStage.getIcons().add(Config.appIcon());
        // and show
        primaryStage.show();
    }

    // ------------------------------ CHECK VERSION RUNNABLE ----------------------------- \\

    private static class CheckForUpdateRunnable implements Runnable {
        @Override
        public void run() {
            boolean needUpdate = Config.checkClientVersion();
            System.out.println(needUpdate ? "Client needs an update" : "Client is up to date");

            if (needUpdate){ // todo: update code
                Platform.runLater(new ChangeUpdaterMessageRunnable(State.DOWNLOAD_UPDATE));
                // ...

                // Platform.runLater(new ChangeUpdaterMessageRunnable(State.STARTING_INSTALLATION));
                // ...
            } else {
                controller.setState(State.CLIENT_STARTING);
                // start
                Platform.runLater(new StartFrameRunnable());
            }
        }

        // Runnable for JavaFX platform in order to change JavaFX label value
        private static class ChangeUpdaterMessageRunnable implements Runnable {
            private final State state;

            public ChangeUpdaterMessageRunnable(State state) { this.state = state; }

            @Override
            public void run() { controller.setState(state); }
        }
    }

    // ------------------------------ JAVAFX RUNNABLE ----------------------------- \\

    /**
     * Show a frame with login screen.
     */
    private static class StartFrameRunnable implements Runnable {

        @Override
        public void run() {
            // close old
            oldStage.close();

            // open a new one
            Stage primaryStage = new Stage();

            // load frame
            BorderPane root = WindowController.loadWindow(primaryStage);
            // make a borderless frame
            BorderlessScene scene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, root,
                    0, 0);
            // init frame
            WindowController.init(scene, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
            // basic stage init
            UpdateWindowHandler.formalizeStage(primaryStage, scene);
            // show login screen
            WindowController.setScreen(Login.getScreen());
        }
    }
}
