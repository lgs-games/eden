package com.lgs.eden.application;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.lgs.eden.api.API;
import com.lgs.eden.api.games.EdenVersionData;
import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.config.InstallUtils;
import com.lgs.eden.utils.download.DownloadManager;
import com.lgs.eden.views.login.Login;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Check for updates, if none then run
 * the application but if we have one then install.
 */
public class UpdateWindowHandler {

    // todo: temporary bypass
    private static final boolean CHECK_UPDATES = false;

    // state of our installer
    enum State {
        LOOKING_FOR_UPDATE,
        DOWNLOAD_UPDATE,
        STARTING_INSTALLATION,
        CLIENT_STARTING;

        @Override
        public String toString() { return name().toLowerCase(); }
    }

    private static volatile Stage oldStage;
    private static UpdateWindowHandler controller;

    /** Show update window */
    public static void start(Stage primaryStage){
        // save for later
        oldStage = primaryStage;
        WindowController.setStage(oldStage);

        if (CHECK_UPDATES){
            // init installer
            FXMLLoader loader = Utility.loadView(ViewsPath.UPDATE.path);
            VBox parent = (VBox) Utility.loadViewPane(loader);
            controller = loader.getController();
            // make a borderless frame
            BorderlessScene scene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, parent,0, 0);

            // set background and fill as transparent
            scene.setFill(Color.TRANSPARENT);
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            // init
            controller.init();

            // call basic functions
            formalizeStage(primaryStage, scene);

            // we need that to close 3 dots thread
            primaryStage.setOnCloseRequest(event -> {
                new ApplicationCloseHandler().handle(event);
                // todo: move to close handler
                oldStage = null;
            });
        }

        // call check for update runnable
        new Thread(new CheckForUpdateRunnable()).start();
    }

    // ------------------------------ JAVAFX ----------------------------- \\

    @FXML // application current version
    public Label version;
    @FXML // shown to the user
    public Label message;
    @FXML // the . or .. or ...
    public Label dots;
    @FXML // we can show a percent if needed
    public Label percent;

    public UpdateWindowHandler() {
    }

    private void init() {
        setState(State.LOOKING_FOR_UPDATE);
        this.version.setText(Config.VERSION);
        this.percent.setVisible(false); // usually hidden
        Platform.runLater(new DotUpdateRunnable());
    }

    // set label text
    private void setState(State state) {
        this.message.setText(Translate.getTranslation(state.toString()));
    }

    // ------------------------------ UTILS ----------------------------- \\

    /** Init stage with our parameters */
    private static void formalizeStage(Stage primaryStage, BorderlessScene scene) {
        // remove the default css style
        scene.removeDefaultCSS();
        // not resizable
        scene.setResizable(false);
        scene.setDoubleClickMaximizeEnabled(false);
        scene.setSnapEnabled(false);

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

        public CheckForUpdateRunnable() {
        }

        @Override
        public void run() {
            EdenVersionData edenVersion;
            boolean needUpdate = false;
            if (CHECK_UPDATES){
                System.out.println("Checking client version...");
                edenVersion = API.imp.getEdenVersion();
                needUpdate = !Config.VERSION.equals(edenVersion.version);
            }
            System.out.println(needUpdate ? "Client needs an update" : "Client is up to date");

            if (CHECK_UPDATES && needUpdate){
                Platform.runLater(() -> {
                    controller.setState(State.DOWNLOAD_UPDATE);

                    // get the update information
                    DownloadManager d = new DownloadManager(edenVersion.getURL(Utility.getUserOS()), Config.getDownloadRepository());

                    // init
                    d.onInitCalled((e) -> Platform.runLater(() -> {
                        // init and show show
                        controller.percent.setText("0%");
                        controller.percent.setVisible(true);
                    }));

                    // start download thread
                    d.onUpdateProgress((e) -> Platform.runLater(
                            () -> controller.percent.setText(Math.round((float) e.downloaded / e.expectedSize * 100)+"%")
                    ));

                    // move to install
                    d.onDownloadEnd((e) ->
                            Platform.runLater(() -> {
                                controller.percent.setVisible(false);
                                controller.setState(State.STARTING_INSTALLATION);
                                // launch install process
                                InstallUtils.installEden(e.fileName);
                            }
                    ));

                    // start download thread
                    ApplicationCloseHandler.startDownloadThread(d);
                });
            } else {
                Platform.runLater(() -> { if(controller != null) controller.setState(State.CLIENT_STARTING); });
                // start
                Platform.runLater(new StartFrameRunnable());
            }
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
            oldStage.setOnCloseRequest((e) -> {});
            oldStage.close();
            oldStage = null;

            // open a new one
            Stage primaryStage = new Stage();
            primaryStage.setOnCloseRequest(new ApplicationCloseHandler());
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

    // ------------------------------ JavaFX DOT ----------------------------- \\

    /**
     * Allow us to have something like that
     *
     * text.
     * text..
     * text...
     * text.
     * etc.
     *
     */
    private static class DotUpdateRunnable implements Runnable {

        private static final String ONE_DOT = ".";
        private static final String TWO_DOT = "..";
        private static final String THREE_DOT = "...";

        @Override
        public void run() {
            final int[] dot = {0};

            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // cancel if we are leaving to another window
                    if (oldStage == null){
                        t.cancel();
                        return;
                    }
                    // next
                    dot[0]++;
                    int r = dot[0] % 3;
                    // find answer
                    String answer;
                    if (r == 0)  answer = ONE_DOT;
                    else if (r == 1)  answer = TWO_DOT;
                    else answer = THREE_DOT;

                    Platform.runLater(() -> controller.dots.setText(answer));
                }
            }, 0, 500);
        }
    }
}
