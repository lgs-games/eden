package com.lgs.eden.application;

import com.lgs.eden.utils.config.Config;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Utilities to create popups
 */
public class PopupUtils {

    private static final int SPACE = 10;
    private static final int WIDTH = 300;

    /**
     * Create and display to popup with some text
     */
    public static void showPopup(String text){
        showPopup(text, false);
    }

    /**
     * Create and display to popup with a custom content
     */
    public static void showPopup(Parent content){
        showPopup(content, false);
    }

    public static void showPopup(Parent content, boolean close) {
        Stage popup = new Stage();
        // make it beautiful
        popup.getIcons().add(Config.appIcon());
        popup.setTitle(Config.APP_NAME);
        popup.resizableProperty().setValue(Boolean.FALSE);

        // a popup
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(WindowController.getStage());

        // create and show popup
        Scene dialogScene = new Scene(content, WIDTH, -1);
        popup.setScene(dialogScene);
        popup.show();
        if (close) popup.setOnCloseRequest((e) -> ApplicationCloseHandler.close(true));
    }

    public static void showPopup(String text, boolean close) {
        // create a label, wrap it in a borderpane
        Label textLabel = new Label(text);
        textLabel.setWrapText(true);

        BorderPane pane = new BorderPane();
        pane.setCenter(textLabel);
        pane.setPadding(new Insets(SPACE));

        if (close){
            Platform.runLater(() -> {
                showPopup(pane, true);
            });
        } else {
            showPopup(pane);
        }
    }
}
