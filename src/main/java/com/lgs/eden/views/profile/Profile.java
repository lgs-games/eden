package com.lgs.eden.views.profile;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

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
        FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE.path);
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

    /** Listener of the see all friends label **/
    @FXML
    private void onSeeAllFriends(){
        System.out.println("So you want to see all your friends...");
    }

    /** Listener of the add friend button **/
    @FXML
    private void onAddFriend() {
        System.out.println("Wanna have some new friends ?");
    }

    /** Listener of the +1 rep label **/
    @FXML 
    private void onPlusOneRep() {
        System.out.println("+1 rep for him");
    }

    /** Listener of the -1 rep label **/
    @FXML
    private void onMinusOneRep() {
        System.out.println("-1 rep for him");
    }

}
