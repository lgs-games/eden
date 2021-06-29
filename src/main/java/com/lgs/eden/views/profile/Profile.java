package com.lgs.eden.views.profile;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CustomCells;
import com.lgs.eden.views.friends.AllFriends;
import com.lgs.eden.views.gameslist.GameList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * Controller for profile.fxml
 */
public class Profile {

    // ------------------------------ STATIC ----------------------------- \\

    /** @return profile screen */
    public static Parent getScreen() {
        return reloadWith(AppWindowHandler.currentUserID());
    }

    private static Profile controller;

    /**
     * @return reloaded profile screen with profile data for userID
     * Should only be called if user is not currentUserID
     */
    public static Parent reloadWith(String userID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE.path);
        Parent parent = Utility.loadViewPane(loader);
        controller = loader.getController();
        controller.init(userID);
        return parent;
    }

    public static Profile getController() {
        if (controller == null) getScreen();
        return controller;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML // profile info
    private Label bio;
    @FXML
    private Label since;
    @FXML
    private Label username;
    @FXML
    private Label lastLogin;
    @FXML
    private ImageView avatar;

    @FXML // reputation
    private Label reputation;
    @FXML
    private Label addOne;
    @FXML
    private Label removeOne;

    @FXML
    private GridPane recentGames;
    @FXML
    private ListView<FriendData> friendDataListView;

    // friends
    @FXML
    private Button addFriend;
    @FXML
    private Button removeFriend;
    @FXML
    private Button acceptFriend;
    @FXML
    private Button refuseFriend;
    @FXML
    private BorderPane friendsPane;

    // current profile data, can be used in listeners
    private ProfileData data;

    /** set up profile view **/
    private void init(String userID) {
        try {
            this.data = API.imp.getProfileData(userID, AppWindowHandler.currentUserID());
        } catch (APIException e) {
            PopupUtils.showPopup(e);
            AppWindowHandler.setScreen(GameList.getScreen(), ViewsPath.GAMES);
            return;
        }

        // ------------------------------ FILL ATTRIBUTES ----------------------------- \\
        this.username.setText(this.data.username); // ex: Raphik
        this.bio.setText(this.data.biography + ""); // bio
        this.lastLogin.setText(Translate.getDate(this.data.lastSeen)); // getDate format
        this.since.setText(Translate.getDate(this.data.memberSinceDate)); // getDate format
        this.avatar.setImage(this.data.avatar); // set avatar

        setReputation(this.data);

        // ------------------------------ ADD/REMOVE FRIEND ----------------------------- \\

        boolean add = false;
        boolean remove = false;
        boolean acceptFriend = false;
        boolean refuseFriend = false;

        switch (this.data.statusWithLogged) {
            case USER: break;
            case REQUESTED: refuseFriend = true; break;
            case GOT_REQUESTED: acceptFriend = true; refuseFriend = true; break;
            case FRIENDS: remove = true; /* can remove */ break;
            case NONE: add = true; /* can add */ break;
        }

        this.addFriend.setVisible(add);
        this.addFriend.setManaged(add);

        this.removeFriend.setManaged(remove);
        this.removeFriend.setVisible(remove);

        this.acceptFriend.setVisible(acceptFriend);
        this.acceptFriend.setManaged(acceptFriend);

        this.refuseFriend.setVisible(refuseFriend);
        this.refuseFriend.setManaged(refuseFriend);

        // ------------------------------ FILL RECENT GAMES ----------------------------- \\

        // show the last 3 games
        if (this.data.recentGames.length > 0) {
            for (int column = 0; column < 3 && column < this.data.recentGames.length; column++) {
                // create
                FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE_CARD.path);
                Parent card = Utility.loadViewPane(loader);
                // fill
                ((RecentGameCard) loader.getController()).init(this.data.recentGames[column]);
                // add
                this.recentGames.add(card, column, 0);
            }
        } else {
            // no recent games played
            Label l = new Label(Translate.getTranslation("game-recent-none"));
            l.getStyleClass().add("empty-message-15");
            this.recentGames.add(l, 0, 0, 3, 1);
        }

        // ------------------------------ FILL FRIEND LIST ----------------------------- \\

        // add friends in the list
        if (this.data.friends.size() > 0) {
            this.friendDataListView.setItems(this.data.friends);
            this.friendDataListView.setCellFactory(friendDataListView -> new CustomCells<>(FriendCellController.load()));
        } else {
            FlowPane p = new FlowPane();
            p.setAlignment(Pos.CENTER);
            p.getChildren().add(new Label(Translate.getTranslation("no_friends_yet")));
            friendsPane.setCenter(p);
        }
    }

    /** Listener of the see all friends label **/
    @FXML
    private void onSeeAllFriends() {
        AppWindowHandler.setScreen(AllFriends.getScreen(this.data.userID), ViewsPath.PROFILE);
    }

    /** Listener of the add friend button **/
    @FXML
    private void onAddFriend() {
        onAddFriend(this.data.userID);
    }
    public void onAddFriend(String friendID) {
        API.imp.addFriend(friendID, AppWindowHandler.currentUserID());
        reload(friendID);
    }

    /** Listener of the remove friend button **/
    @FXML
    private void onRemoveFriend() {
        onRemoveFriend(this.data.userID);
    }
    public void onRemoveFriend(String friendID) {
        API.imp.removeFriend(friendID, AppWindowHandler.currentUserID());
        reload(friendID);
    }

    /** Listener of the +1 rep label **/
    @FXML
    private void onPlusOneRep() {
        // api won't allow it
        if (AppWindowHandler.currentUserID().equals(data.userID)) return;
        ProfileData r = API.imp.changeReputation(data.userID, AppWindowHandler.currentUserID(), true);
        if (r != null) this.setReputation(this.data = r);
    }

    /** Listener of the -1 rep label **/
    @FXML
    private void onMinusOneRep() {
        // api won't allow it
        if (AppWindowHandler.currentUserID().equals(data.userID)) return;

        ProfileData r = API.imp.changeReputation(data.userID, AppWindowHandler.currentUserID(), false);
        if (r != null) this.setReputation(this.data = r);
    }

    private void setReputation(ProfileData data) {
        // reputation
        int rep = Integer.compare(data.reputation, 0);
        String repSigne = rep >= 0 ? "+" : "";
        String repStyle = rep == -1 ? "red-text" : rep == 1 ? "green-text" : "black-text";
        this.reputation.setText(repSigne + data.reputation);
        this.reputation.getStyleClass().set(0, repStyle);

        // disable +1 and -1 visually
        if (AppWindowHandler.currentUserID().equals(this.data.userID)) {
            this.addOne.setDisable(true);
            this.removeOne.setDisable(true);
        } else {
            switch (data.score) {
                case NONE -> {
                    this.addOne.setDisable(false);
                    this.removeOne.setDisable(false);
                }
                case INCREASED -> {
                    this.addOne.setDisable(true);
                    this.removeOne.setDisable(false);
                }
                case DECREASED -> {
                    this.addOne.setDisable(false);
                    this.removeOne.setDisable(true);
                }
            }
        }
    }

    /** Listener of the accept friend button **/
    @FXML
    private void onAcceptFriend() {
        onAcceptFriend(this.data.userID);
    }
    public void onAcceptFriend(String friendID) {
        API.imp.acceptFriend(friendID, AppWindowHandler.currentUserID());
        reload(friendID);
    }

    /** Listener of the accept friend button **/
    @FXML
    private void onRefuseFriend() {
        onRefuseFriend(this.data.userID);
    }
    public void onRefuseFriend(String friendID) {
        API.imp.refuseFriend(friendID, AppWindowHandler.currentUserID());
        reload(friendID);
    }

    // ------------------------------ UTILS ----------------------------- \\

    /** reload view **/
    public void reload(String friendID) {
        AppWindowHandler.setScreen(Profile.reloadWith(friendID), ViewsPath.PROFILE);
    }

    @FXML
    private void dropFocus() { this.bio.getParent().getParent().requestFocus(); }

}
