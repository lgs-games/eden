package com.lgs.eden.views.gameslist.news;

import com.lgs.eden.api.API;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Show all news of a game
 */
public class AllNews {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(int gameID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_ALL_NEWS.path);
        Parent parent = Utility.loadViewPane(loader);
        AllNews controller = loader.getController();
        controller.init(gameID);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private static final int COUNT_PER_PAGE = 7;

    @FXML
    private Pagination pagination;
    @FXML
    private VBox newsPanel;
    @FXML
    private Label from;
    @FXML
    private Label to;

    private void init(int gameID) {
        this.pagination.setPageFactory(pageIndex -> {
            // get the news for our page
            ArrayList<BasicNewsData> allNews = API.imp.getAllNews(pageIndex, COUNT_PER_PAGE, Config.getCode(), gameID);
            // set max page
            this.pagination.setPageCount((int) Math.ceil((double) BasicNewsData.newsCount / COUNT_PER_PAGE));
            // clear old view
            this.newsPanel.getChildren().clear();
            // fill up again
            if (allNews.isEmpty()){
                this.from.setText(""+0);
                this.to.setText(""+0);
                // show empty message
                Label emptyMessage = new Label(Translate.getTranslation("no_news"));
                emptyMessage.getStyleClass().add("empty_message");
                BorderPane empty = new BorderPane();
                empty.setCenter(emptyMessage);
                VBox.setVgrow(empty, Priority.ALWAYS);
                this.newsPanel.getChildren().add(empty);
            } else {
                int start = (pageIndex*COUNT_PER_PAGE)+1;
                this.from.setText(""+start);
                this.to.setText(""+(start+allNews.size()-1));
                for (BasicNewsData news : allNews) {
                    this.newsPanel.getChildren().add(AllNewsCell.getScreen(news));
                }
            }
            // <=> we don't care
            return new VBox();
        });
    }

}
