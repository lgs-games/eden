package com.lgs.eden.views.profile;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CellHandler;
import com.lgs.eden.views.profile.messages.Messages;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Controller for friendcell.fxml
 */
public class FriendCellController implements CellHandler<FriendData> {

    // ------------------------------ STATIC ----------------------------- \\

    public static CellHandler<FriendData> load(){ return CellHandler.load(ViewsPath.FRIEND_CELL); }

    // todo: maybe save c in a static variable and do not create
    //  the same menu again and again...
    private static ContextMenu getContextMenu(FriendCellController c) {
        // create a menu
        // context menu
        ContextMenu contextMenu = new ContextMenu();

        // create menu items
        // todo: translations
        MenuItem profile = new MenuItem("See profile");
        MenuItem addFriend = new MenuItem("Add as friend");
        MenuItem removeFriend = new MenuItem("Remove friend");
        MenuItem blockUser = new MenuItem("Block user");
        MenuItem tchat = new MenuItem("Send message");

        // todo: disabled for now, maybe should only be shown when useful
        //  like can't add friend again or remove if not friend, ...
        profile.setOnAction((e) -> c.onWantProfile(null));
        addFriend.setDisable(true);
        removeFriend.setDisable(true);
        blockUser.setDisable(true);
        tchat.setOnAction((e) -> c.onWantMessage());

        // add menu items to menu
        contextMenu.getItems().add(profile);
        contextMenu.getItems().add(addFriend);
        contextMenu.getItems().add(removeFriend);
        contextMenu.getItems().add(blockUser);
        contextMenu.getItems().add(tchat);
        return contextMenu;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label friendName;
    @FXML
    private ImageView friendAvatar;

    /** the view **/
    private Pane view;
    /** friend date **/
    private FriendData data;

    // ------------------------------ METHODS ----------------------------- \\

    @FXML
    public void init(FriendData d){
        this.data = d;
        this.friendName.setText(d.name);

        // avatar
        Circle circle = new Circle(24, 24, 24, Color.BLACK);
        this.friendAvatar.setClip(circle);
        this.friendAvatar.setImage(d.avatar);

        // fill pane with nodes, etc
        // create context menu and menu items as above
        view.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                getContextMenu(this).show(view, event.getScreenX(), event.getScreenY());
            }
        });
    }

    @FXML
    public Pane getView() { return this.view; }

    @Override
    public void setView(Pane view) { this.view = view; }

    // ------------------------------ LISTENERS ----------------------------- \\

    /**
     * Triggered when the user clicks on a cell, redirects to this friends profile
     */
    @FXML
    public void onWantProfile(MouseEvent e) {
        // left click
        if (e == null || e.getButton() == MouseButton.PRIMARY)
        {
            // goto profile
            AppWindowHandler.setScreen(Profile.reloadWith(this.data.id), ViewsPath.PROFILE);
        }
    }

    /**
     * Wants to send a message to this person
     */
    private void onWantMessage() {
        AppWindowHandler.setScreen(Messages.getScreen(this.data.id), ViewsPath.PROFILE);
    }

}
