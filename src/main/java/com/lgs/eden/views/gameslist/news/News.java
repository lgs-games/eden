package com.lgs.eden.views.gameslist.news;

import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * View for a news
 */
public class News {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(BasicNewsData news) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_A_NEWS.path);
        Parent parent = Utility.loadViewPane(loader);
        News controller = loader.getController();
        controller.init(news);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private void init(BasicNewsData news) {

    }
}
