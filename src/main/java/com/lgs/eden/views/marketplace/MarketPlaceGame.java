package com.lgs.eden.views.marketplace;

import com.lgs.eden.api.games.MarketplaceGameData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

/**
 * Controller for market_game.fxml
 */
public class MarketPlaceGame {

    public static Parent getScreen(MarketplaceGameData data) {
        FXMLLoader loader = Utility.loadView(ViewsPath.MARKETPLACE_GAME.path);
        Parent parent = Utility.loadViewPane(loader);
        MarketPlaceGame controller = loader.getController();
        controller.init(data);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Button downloadButton;
    @FXML
    private Label gameDesc;
    @FXML
    private ImageView gameIcon;
    @FXML
    private Label gameName;
    @FXML
    private ImageView gameImage;
    @FXML
    private FlowPane languages;
    @FXML
    private FlowPane tags;

    private void init(MarketplaceGameData data) {
        // ...
    }
}
