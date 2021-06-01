package com.lgs.eden.views.marketplace;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.scene.Parent;

/**
 * Controller for market.fxml
 */
public class MarketPlace {

    public static Parent getScreen() {
        return Utility.loadViewPane(ViewsPath.MARKETPLACE.path);
    }
}
