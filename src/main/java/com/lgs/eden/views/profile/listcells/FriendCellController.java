package com.lgs.eden.views.profile.listcells;

import com.lgs.eden.api.wrapper.FriendData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.profile.Profile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    /** friend date **/
    private FriendData data;

    // ------------------------------ METHODS ----------------------------- \\

    public void init(FriendData d){
        this.data = d;
        this.friendName.setText(d.name);
        this.friendAvatar.setImage(d.avatar);
    }

    public Parent getView() { return this.view; }

    // ------------------------------ LISTENERS ----------------------------- \\

    /**
     * Triggered when the user clicks on a cell, redirects to this friends profile
     */
    @FXML
    public void onWantProfile() {
        AppWindowHandler.setScreen(Profile.reloadWith(this.data.id), ViewsPath.PROFILE);
    }

}
