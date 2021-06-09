package com.lgs.eden.views.gameslist;

import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.marketplace.Marketplace;
import javafx.fxml.FXML;
import javafx.scene.Parent;

/**
 * Empty game list controller
 */
public class EmptyGameList {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen() {
        return Utility.loadViewPane(ViewsPath.GAMES_LIST_EMPTY.path);
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    public void goToMarketplace(){
        AppWindowHandler.setScreen(Marketplace.getScreen(), ViewsPath.MARKETPLACE);
    }

}
