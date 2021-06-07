package com.lgs.eden.views.gameslist.news;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * View for a cell all the all_news view.
 * See all_news_cell.fxml
 */
public class AllNewsCell {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_ALL_NEWS_CELL.path);
        Parent parent = Utility.loadViewPane(loader);
        AllNewsCell controller = loader.getController();
        controller.init();
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private void init() {

    }

}
