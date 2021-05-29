package com.lgs.eden.views.profile.listcells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller for friendcell.fxml
 */
public class FriendCellController {

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label friendName;

    @FXML
    private ImageView friendAvatar;

    // ------------------------------ METHODS ----------------------------- \\

    public void setName(String entry) { this.friendName.setText(entry); }

    public void setAvatar(Image pic) { this.friendAvatar.setImage(pic); }

    // ------------------------------ LISTENERS ----------------------------- \\

    /**
     * Triggered when the user clicks on a cell, redirects to this friends profile
     */
    @FXML
    public void onWantProfile() {
        System.out.println("So you want to see his profile :eyes:");
        System.out.println("/see "+friendName.getText());
    }

}
