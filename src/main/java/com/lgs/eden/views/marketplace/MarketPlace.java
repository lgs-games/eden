package com.lgs.eden.views.marketplace;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;

/**
 * Controller for market.fxml
 */
public class MarketPlace {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView(ViewsPath.MARKETPLACE.path);
        Parent parent = Utility.loadViewPane(loader);
        MarketPlace controller = loader.getController();
        controller.init();
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Pagination paginations;
    @FXML
    private GridPane content;

    private void init() {
        content.add(MarketPlaceGame.getScreen(), 0,0);
        content.add(MarketPlaceGame.getScreen(), 1,0);
        content.add(MarketPlaceGame.getScreen(), 0,1);
        content.add(MarketPlaceGame.getScreen(), 1,1);
    }
}
