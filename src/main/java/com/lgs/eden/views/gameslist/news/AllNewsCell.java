package com.lgs.eden.views.gameslist.news;

import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.gameslist.GameList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * View for a cell all the all_news view.
 * See all_news_cell.fxml
 */
public class AllNewsCell {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(BasicNewsData news) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_ALL_NEWS_CELL.path);
        Parent parent = Utility.loadViewPane(loader);
        AllNewsCell controller = loader.getController();
        controller.init(news);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label newsTitle;
    @FXML
    private Label newsDay;
    @FXML
    private Label newsMonth;
    @FXML
    private Label newsDesc;
    // save news data
    private BasicNewsData news;

    private void init(BasicNewsData news) {
        this.news = news;

        this.newsTitle.setText(news.title);
        this.newsDesc.setText(news.desc);
        this.newsDay.setText(Translate.getDate(news.date, "d"));
        this.newsMonth.setText(Translate.getDate(news.date, "LLL"));
    }

    @FXML
    public void gotoNews(){
        GameList.getController().goToSubMenu(News.getScreen(this.news));
    }

}
