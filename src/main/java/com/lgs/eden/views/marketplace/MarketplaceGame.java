package com.lgs.eden.views.marketplace;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.games.BasicGameData;
import com.lgs.eden.api.games.MarketplaceGameData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.gameslist.GameList;
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
public class MarketplaceGame {

    private MarketplaceGameData data;

    public static Parent getScreen(MarketplaceGameData data) {
        FXMLLoader loader = Utility.loadView(ViewsPath.MARKETPLACE_GAME.path);
        Parent parent = Utility.loadViewPane(loader);
        MarketplaceGame controller = loader.getController();
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
    @FXML
    private Label gameVersion;
    @FXML
    private Label gameSize;

    private void init(MarketplaceGameData data) {
        this.data = data;
        this.gameName.setText(data.name);
        this.gameDesc.setText(data.desc);
        this.gameImage.setImage(data.image);
        this.gameIcon.setImage(data.icon);
        this.gameVersion.setText(data.version);
        this.gameSize.setText(data.size + "");

        this.tags.getChildren().clear();
        data.tags.forEach(s -> {
            Button b = new Button(s);
            b.getStyleClass().add("eden-button");
            this.tags.getChildren().add(b);
        });

        String downloadMessage = data.inLibrary ? "goto_library" : "add_library";
        this.downloadButton.setText(Translate.getTranslation(downloadMessage));

        this.languages.getChildren().clear();
        data.languages.forEach(s -> {
            Button b = new Button(s);
            b.getStyleClass().add("eden-button");
            this.languages.getChildren().add(b);
        });
    }

    @FXML
    public void onDownloadPressed() {
        BasicGameData newGame = new BasicGameData(this.data.id, this.data.name, this.data.getIconPath());
        // add to library
        if (!this.data.inLibrary) {
            try {
                if (!API.imp.addToLibrary(AppWindowHandler.currentUserID(), newGame)){
                    PopupUtils.showPopup(Translate.getTranslation("add_library_failed"));
                    return;
                }
            } catch (APIException e) {
                PopupUtils.showPopup(e);
                return;
            }
        }

        Parent screen = GameList.getScreen(newGame);
        AppWindowHandler.setScreen(screen, ViewsPath.GAMES);
    }
}
