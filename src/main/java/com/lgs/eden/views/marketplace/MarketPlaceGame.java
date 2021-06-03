package com.lgs.eden.views.marketplace;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Controller for market_game.fxml
 */
public class MarketPlaceGame {

    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView(ViewsPath.MARKETPLACE_GAME.path);
        Parent parent = Utility.loadViewPane(loader);
        MarketPlaceGame controller = loader.getController();
        controller.init();
        return parent;
    }

    private void init() {

    }
}
