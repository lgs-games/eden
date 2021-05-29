package com.lgs.eden.views.profile;

import com.lgs.eden.api.Api;
import com.lgs.eden.api.wrapper.RecentGameData;
import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for profile.fxml
 */
public class Profile {

    // ------------------------------ STATIC ----------------------------- \\

    /**
     * @return profile screen
     */
    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView("/fxml/profile.fxml");
        Parent parent = Utility.loadViewPane(loader);
        Profile controller = loader.getController();
        controller.init();
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private final RecentGameData[] recentGamesData;
    private int usernameID;
    private int friendsNumber;
    private int reputation;
    private short status;

    private String biography;

    private Date lastSeen;
    private Date memberSinceDate;

    @FXML
    private GridPane recentGames;

    public Profile() {
        // todo: that's a fake of API data
        this.recentGamesData = new RecentGameData[]{
                new RecentGameData(Utility.loadImage("/games/prim-icon.png"), "Prim", 0, RecentGameData.PLAYING),
                new RecentGameData(Utility.loadImage("/games/enigma-icon.png"), "Enigma", 1020, 30)
        };
    }

    private void init() {
        int gamePlayedCount = 2; // todo: fake

        // show the last 3 games
        if (recentGamesData.length > 0){
            for (int column = 0; column < 3 && column < gamePlayedCount; column++) {
                // create
                FXMLLoader loader = Utility.loadView("/fxml/profile/card.fxml");
                Parent card = Utility.loadViewPane(loader);
                // fill
                ((RecentGameCard)loader.getController()).init(recentGamesData[column]);
                // add
                this.recentGames.add(card, column,0);
            }
        } else {
            // no recent games played
            Label l = new Label(Translate.getTranslation("game-recent-none"));
            l.getStyleClass().add("profile-game-label");
            this.recentGames.add(l, 0,0, 3, 1);
        }

    }

}
