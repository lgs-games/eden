package com.lgs.eden;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.lgs.eden.application.WindowController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/**
 * Start program
 */
public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    @Override // load fxml frame
    public void start(Stage primaryStage) { //todo: load values from config
        // load frame
        BorderPane root = WindowController.loadWindow(primaryStage);
        // make a borderless frame
        BorderlessScene scene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, root, 0, 0);
        // init frame
        WindowController.init(scene, 800, 550);
        // then set scene
        primaryStage.setScene(scene);
        // window title
        primaryStage.setTitle("Eden");
        // window icon
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icon64.png"))));
        // and show
        primaryStage.show();
    }
}
