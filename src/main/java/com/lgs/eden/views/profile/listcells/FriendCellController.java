package com.lgs.eden.views.profile.listcells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FriendCellController {

    @FXML
    private Label friend_name;

    @FXML
    private ImageView friend_avatar;

    public void setName(String entry) {this.friend_name.setText(entry);}
    public Label getName() {return friend_name;}

    public void setAvatar(Image pic) {this.friend_avatar.setImage(pic);}
    public Image getAvatar() {return friend_avatar.getImage();}

    /**
     * Triggered when the user clicks on a cell, redirects to this friends profile
     */
    @FXML
    private void onWantProfile() {
        System.out.println("So you want to see his profile :eyes:");
    }

}
