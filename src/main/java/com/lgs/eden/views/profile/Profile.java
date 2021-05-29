package com.lgs.eden.views.profile;

import com.lgs.eden.utils.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.util.Date;

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
    }

    private void init() {
        // load some cards
        Parent g1 = Utility.loadViewPane("/fxml/profile/card.fxml");
        Parent g2 = Utility.loadViewPane("/fxml/profile/card.fxml");
        Parent g3 = Utility.loadViewPane("/fxml/profile/card.fxml");
        this.recentGames.add(g1, 0,0);
        this.recentGames.add(g2, 1,0);
        this.recentGames.add(g3, 2,0);
    }

}
