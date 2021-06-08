package com.lgs.eden.views.gameslist;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Game Settings controller
 */
public class GameSettings {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(int gameID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_SETTINGS.path);
        Parent parent = Utility.loadViewPane(loader);
        GameSettings controller = loader.getController();
        controller.init(gameID);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private void init(int gameID) {

    }

}
