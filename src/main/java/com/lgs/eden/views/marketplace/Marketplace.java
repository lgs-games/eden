package com.lgs.eden.views.marketplace;

import com.lgs.eden.api.API;
import com.lgs.eden.api.games.MarketplaceGameData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;

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

    @FXML
    private Pagination paginations;
    @FXML
    private GridPane content;

    private void init() {
        // todo: handle pagination

        ArrayList<MarketplaceGameData> games = API.imp.getMarketPlaceGames();

        int i = 0;
        for (MarketplaceGameData d: games) {
            this.content.add(MarketplaceGame.getScreen(d), i % 2, i / 2);
            i++;
        }
    }
}
