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

    private static FriendCellController current;
    private static ContextMenu contextMenu;
    private static MenuItem addFriend;
    private static MenuItem removeFriend;
    private static MenuItem tchat;

    /**
     * Show the context menu for an user
     */
    private static void initContextMenu() {
        if (contextMenu != null) return;
        // create a context menu
        contextMenu = new ContextMenu();

        // create menu items
        // todo: translations
        MenuItem profile = new MenuItem("See profile");
        addFriend = new MenuItem("Add as friend");
        removeFriend = new MenuItem("Remove friend");
        tchat = new MenuItem("Send message");

        // listeners
        profile.setOnAction((e) -> current.onWantProfile(null));
        addFriend.setOnAction((e) -> current.onAddUser());
        removeFriend.setOnAction((e) -> current.onRemoveUser());
        tchat.setOnAction((e) -> current.onWantMessage());

        // add menu items to menu
        contextMenu.getItems().add(profile);
        contextMenu.getItems().add(addFriend);
        contextMenu.getItems().add(removeFriend);
        contextMenu.getItems().add(tchat);
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

    private boolean init = false;

    @FXML
    public void init(FriendData d){
        if (init) return;
        this.data = d;
        this.friendName.setText(d.name);

        // avatar
        Circle circle = new Circle(24, 24, 24, Color.BLACK);
        this.friendAvatar.setClip(circle);
        this.friendAvatar.setImage(d.avatar);

        // fill pane with nodes, etc
        // create context menu and menu items as above
        this.view.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                // save
                current = this;

                // init
                initContextMenu();

                boolean disabledAdd = false;
                boolean disabledFriend = true;

                // show add / remove friend
                switch (current.data.friendShipStatus){
                    case FRIENDS: disabledAdd = true; disabledFriend = false; break;
                    case NONE: disabledAdd = false; disabledFriend = true; break;
                    case USER: case REQUESTED:case GOT_REQUESTED:
                        disabledAdd = true; disabledFriend = true;
                        break;
                }
                // disabled if friend
                addFriend.setDisable(disabledAdd);
                removeFriend.setDisable(disabledFriend);
                tchat.setDisable(disabledFriend);

                // show
                contextMenu.show(this.view, event.getScreenX(), event.getScreenY());
            }
        });
        init = true;
    }

    @FXML
    public Pane getView() { return this.view; }

    @Override
    public void setView(Pane view) { this.view = view; }

    // ------------------------------ LISTENERS ----------------------------- \\

    /** Triggered when the user clicks on a cell, redirects to this friends profile */
    @FXML
    public void onWantProfile(MouseEvent e) {
        // left click
        if (e == null || e.getButton() == MouseButton.PRIMARY)
        {
            // goto profile
            AppWindowHandler.setScreen(Profile.reloadWith(this.data.id), ViewsPath.PROFILE);
        }
    }

    /** Wants to send a message to this person */
    private void onWantMessage() {
        AppWindowHandler.setScreen(Messages.getScreen(this.data.id), ViewsPath.PROFILE);
    }

    /** Wants to remove this friend */
    private void onAddUser() {
        Profile.getController().onAddFriend(this.data.id);
    }

    /** Wants to add this user */
    private void onRemoveUser() {
        Profile.getController().onRemoveFriend(this.data.id);
    }

}
