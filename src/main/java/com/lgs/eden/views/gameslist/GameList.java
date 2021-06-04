package com.lgs.eden.views.gameslist;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Pane with the list of games
 * at the left and a game at the right.
 */
public class GameList {

    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_LIST.path);
        return Utility.loadViewPane(loader);
    }
}
