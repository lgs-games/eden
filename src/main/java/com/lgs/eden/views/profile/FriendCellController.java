package com.lgs.eden.views.profile;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Translate;
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
 * Controller for friend_cell.fxml
 */
public class FriendCellController implements CellHandler<FriendData> {

    // ------------------------------ STATIC ----------------------------- \\
    /** called after each event **/
    private static Runnable afterEvent;

    public static CellHandler<FriendData> load() {
        afterEvent = () -> {};
        return CellHandler.load(ViewsPath.FRIEND_CELL);
    }

    public static CellHandler<FriendData> load(Runnable r) {
        afterEvent = r;
        return CellHandler.load(ViewsPath.FRIEND_CELL);
    }

    // current friend for the context menu
    private static FriendCellController current;
    private static synchronized void setCurrent(FriendCellController c) {
        current = c;
    }

    // menus
    private static ContextMenu contextMenu;
    private static MenuItem addFriend;
    private static MenuItem removeFriend;
    private static MenuItem acceptFriendRequest;
    private static MenuItem refuseFriendRequest;
    private static MenuItem cancelFriendRequest;
    private static MenuItem tchat;

    /**
     * Show the context menu for an user
     */
    private static void initContextMenu() {
        if (contextMenu != null) return;
        // create a context menu
        contextMenu = new ContextMenu();

        // create menu items
        MenuItem profile = new MenuItem(Translate.getTranslation("see_profile"));
        addFriend = new MenuItem(Translate.getTranslation("add_user"));
        removeFriend = new MenuItem(Translate.getTranslation("remove_user"));
        acceptFriendRequest = new MenuItem(Translate.getTranslation("accept_request"));
        refuseFriendRequest = new MenuItem(Translate.getTranslation("refuse_request"));
        cancelFriendRequest = new MenuItem(Translate.getTranslation("cancel_request"));
        tchat = new MenuItem(Translate.getTranslation("send_message"));

        // listeners
        profile.setOnAction(e -> {
            current.onWantProfile(null);
            afterEvent.run();
        });
        addFriend.setOnAction(e -> {
            current.onAddUser();
            afterEvent.run();
        });
        removeFriend.setOnAction(e -> {
            current.onRemoveUser();
            afterEvent.run();
        });
        acceptFriendRequest.setOnAction(e -> {
            current.onAcceptFriend();
            afterEvent.run();
        });
        refuseFriendRequest.setOnAction(e -> {
            current.onRefuseFriend();
            afterEvent.run();
        });
        cancelFriendRequest.setOnAction(e -> {
            current.onCancelRequest();
            afterEvent.run();
        });
        tchat.setOnAction(e -> {
            current.onWantMessage();
            afterEvent.run();
        });

        // add menu items to menu
        contextMenu.getItems().add(profile);
        contextMenu.getItems().add(addFriend);
        contextMenu.getItems().add(removeFriend);
        contextMenu.getItems().add(acceptFriendRequest);
        contextMenu.getItems().add(refuseFriendRequest);
        contextMenu.getItems().add(cancelFriendRequest);
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

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    @FXML
    public void init(FriendData d) {
        if (init) return;
        this.data = d;
        this.friendName.setText(d.name);

        // avatar
        Circle circle = new Circle(24, 24, 32, Color.BLACK);
        this.friendAvatar.setClip(circle);
        this.friendAvatar.setImage(d.avatar);

        // fill pane with nodes, etc
        // create context menu and menu items as above
        this.view.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                // save
                setCurrent(this);

                // init
                initContextMenu();

                boolean add = false;
                boolean friend = false; // both remove and send message
                boolean acceptFR = false;
                boolean refuseFR = false;
                boolean cancelFR = false;

                // show add / remove friend
                switch (current.data.friendShipStatus) {
                    case FRIENDS -> {
                        add = true;
                        friend = false;
                    }
                    case NONE -> {
                        friend = true;
                    }
                    case USER -> {
                        add = true;
                        friend = true;
                    }
                    case REQUESTED -> {
                        acceptFR = true;
                        refuseFR = true;
                    }
                    case GOT_REQUESTED -> {
                        cancelFR = true;
                    }
                    default -> throw new IllegalStateException("error");
                }

                // disabled if not available
                if (!acceptFR && !cancelFR) {
                    acceptFriendRequest.setDisable(true);
                    refuseFriendRequest.setDisable(true);
                    cancelFriendRequest.setDisable(true);

                    addFriend.setDisable(add);
                    removeFriend.setDisable(friend);
                    tchat.setDisable(friend);
                } else {
                    addFriend.setDisable(true);
                    removeFriend.setDisable(true);
                    tchat.setDisable(true);

                    acceptFriendRequest.setDisable(acceptFR);
                    refuseFriendRequest.setDisable(refuseFR);
                    cancelFriendRequest.setDisable(cancelFR);
                }

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
        if (e == null || e.getButton() == MouseButton.PRIMARY) {
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

    /** Wants to add this user */
    private void onAcceptFriend() {
        Profile.getController().onAcceptFriend(this.data.id);
    }

    /** Wants to add this user */
    private void onRefuseFriend() {
        Profile.getController().onRefuseFriend(this.data.id);
    }

    /** Wants to add this user */
    private void onCancelRequest() {
        Profile.getController().onRefuseFriend(this.data.id);
    }

}
