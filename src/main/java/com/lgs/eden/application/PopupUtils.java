package com.lgs.eden.application;

import com.lgs.eden.api.APIException;
import com.lgs.eden.utils.Translate;
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
    public static void showPopup(String text) {
        showPopup(text, false);
    }

    public static void showPopup(String text, boolean close) {
        showPopup(text, closeCallBack(close));
    }


    /**
     * Create and display to popup with a custom content
     */
    public static void showPopup(Parent content) {
        showPopup(content, false);
    }

    public static void showPopup(Parent content, boolean close) {
        showPopup(content, closeCallBack(close));
    }

    /**
     * Create and display to popup for an APIException
     */
    public static void showPopup(APIException e) {
        showPopup(e, false);
    }

    public static void showPopup(APIException e, boolean close) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder b = new StringBuilder("\nStacktrace:\n");
        for (int i = 0; i < stackTrace.length && i < 1; i++) {
            b.append(stackTrace[i]).append('\n');
        }

        showPopup(Translate.getTranslation(e.code) + "\n" + e.getMessage() +"\n"+ b, close);
    }

    // ------------------------------ CORE ----------------------------- \\

    public static void showPopup(String text, Runnable r) {
        // create a label, wrap it in a borderpane
        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        textLabel.setPrefWidth(250);

        BorderPane pane = new BorderPane();
        pane.setCenter(textLabel);
        pane.setPadding(new Insets(SPACE));

        Platform.runLater(() -> showPopup(pane, r));
    }

    public static void showPopup(Parent content, Runnable r) {
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
        if (r != null) popup.setOnCloseRequest((e) -> r.run());
    }

    // ------------------------------ UTILS ----------------------------- \\

    private static Runnable closeCallBack(boolean close) {
        return close ? () -> ApplicationCloseHandler.close(true) : null;
    }

}
