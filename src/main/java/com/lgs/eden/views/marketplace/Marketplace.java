package com.lgs.eden.views.marketplace;

import com.lgs.eden.api.API;
import com.lgs.eden.api.games.MarketplaceGameData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Controller for market.fxml
 */
public class Marketplace {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView(ViewsPath.MARKETPLACE.path);
        Parent parent = Utility.loadViewPane(loader);
        Marketplace controller = loader.getController();
        controller.init();
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private static final int COUNT_PER_PAGE = 4;

    @FXML
    private Pagination pagination;
    @FXML
    private GridPane content;

    private void init() {
        this.pagination.setPageFactory(pageIndex -> {
            // get game for our page
            ArrayList<MarketplaceGameData> games = API.imp.getMarketPlaceGames(pageIndex, COUNT_PER_PAGE,
                    Config.getCode(), AppWindowHandler.currentUserID());
            // set max page
            this.pagination.setPageCount(Math.round((float) MarketplaceGameData.gameCount / COUNT_PER_PAGE));
            // clear old view
            this.content.getChildren().clear();
            int i = 0;
            // set new view, meaning we are adding up to 4 games
            for (MarketplaceGameData d: games) {
                this.content.add(MarketplaceGame.getScreen(d), i % 2, i / 2);
                i++;
            }
            // <=> we don't care
            return new VBox();
        });
    }
}
