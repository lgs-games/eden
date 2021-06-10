package com.lgs.eden.application;

import com.lgs.eden.utils.Config;
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
        // create a label, wrap it in a borderpane
        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        showPopup(textLabel);
    }

    /**
     * Create and display to popup with a custom content
     */
    public static void showPopup(Parent content){
        Stage popup = new Stage();
        // make it beautiful
        popup.getIcons().add(Config.appIcon());
        popup.setTitle(Config.APP_NAME);
        popup.resizableProperty().setValue(Boolean.FALSE);

        // a popup
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(WindowController.getStage());

        // create a label, wrap it in a borderpane
        BorderPane pane = new BorderPane();
        pane.setCenter(content);
        pane.setPadding(new Insets(SPACE));

        // create and show popup
        Scene dialogScene = new Scene(pane, WIDTH, -1);
        popup.setScene(dialogScene);
        popup.show();
    }

}
