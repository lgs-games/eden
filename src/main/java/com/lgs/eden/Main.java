package com.lgs.eden;

import com.lgs.eden.api.API;
import com.lgs.eden.application.UpdateWindowHandler;
import com.lgs.eden.utils.config.Config;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Start program
 */
public class Main extends Application {

    public static void main(String[] args) {
        // init API handler
        API.imp.init();
        // load config
        Config.init();
        // start
        launch(args);
    }

    @Override // load fxml frame
    public void start(Stage primaryStage) {
        // we call for installer handler in order to check
        // for updates
        UpdateWindowHandler.start(primaryStage);
    }
}
