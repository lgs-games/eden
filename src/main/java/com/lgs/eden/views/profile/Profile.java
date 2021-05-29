package com.lgs.eden.views.profile;

import com.lgs.eden.utils.Utility;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
        return Utility.loadViewPane(loader);
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private int usernameID;
    private int friendsNumber;
    private int reputation;
    private short status;

    private String biography;

    private Date lastSeen;
    private Date memberSinceDate;

}
