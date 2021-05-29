package com.lgs.eden.views.profile.listcells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class FriendCellController {

    @FXML
    private Label name;

    @FXML
    private Image avatar;

    public void setName(String entry) {this.name.setText(entry);}

    public void setAvatar(Image pic) {this.avatar = pic;}

    @FXML
    private void onWantProfile() {
        System.out.println("So you want to see his profile :eyes:");
    }

}
