package com.lgs.eden;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.lgs.eden.application.WindowController;
import com.lgs.eden.utils.Config;
import com.lgs.eden.views.login.Login;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Start program
 */
public class Main extends Application {

    public static void main(String[] args) {
        Config.init();

        // TODO: change version type to EdenVersion
        //  and maybe process to some install screen
        System.out.println(Config.checkClientVersion() ? "Client needs an update" : "Client is up to date");

        launch(args);
    }

    @Override // load fxml frame
    public void start(Stage primaryStage) {
        // load frame
        BorderPane root = WindowController.loadWindow(primaryStage);
        // make a borderless frame
        BorderlessScene scene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, root,
                0, 0);
        // init frame
        WindowController.init(scene, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        // then set scene
        primaryStage.setScene(scene);
        // window title
        primaryStage.setTitle(Config.APP_NAME);
        // window icon
        primaryStage.getIcons().add(Config.appIcon());
        // and show
        primaryStage.show();
        // show login screen
        WindowController.setScreen(Login.getScreen());
    }
}
