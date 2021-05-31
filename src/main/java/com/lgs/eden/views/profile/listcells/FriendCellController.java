package com.lgs.eden.views.profile.listcells;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller for friendcell.fxml
 */
public class FriendCellController {

    // ------------------------------ STATIC ----------------------------- \\

    public static FriendCellController load(){
        FXMLLoader loader = Utility.loadView(ViewsPath.FRIEND_CELL.path);
        Parent view = Utility.loadViewPane(loader);
        FriendCellController controller = loader.getController();
        controller.view = view;
        return controller;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label friendName;

    @FXML
    private ImageView friendAvatar;

    /** the view **/
    private Parent view;

    // ------------------------------ METHODS ----------------------------- \\

    public void setName(String entry) { this.friendName.setText(entry); }

    public void setAvatar(Image pic) { this.friendAvatar.setImage(pic); }

    public Parent getView() { return this.view; }

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
