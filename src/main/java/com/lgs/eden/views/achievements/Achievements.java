package com.lgs.eden.views.achievements;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Achievement view
 */
public class Achievements {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(int gameID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.ACHIEVEMENTS.path);
        Parent parent = Utility.loadViewPane(loader);
        Achievements controller = loader.getController();
        controller.init(gameID);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label achievementTitle;

    @FXML
    private GridPane achievements;

    private void init(int gameID) {

    }

}
