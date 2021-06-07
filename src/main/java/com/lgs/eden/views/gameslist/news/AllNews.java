package com.lgs.eden.views.gameslist.news;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Show all news of a game
 */
public class AllNews {

    public static Parent getScreen(int gameID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_ALL_NEWS.path);
        return Utility.loadViewPane(loader);
    }

}
