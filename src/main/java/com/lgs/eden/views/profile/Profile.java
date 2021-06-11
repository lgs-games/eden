package com.lgs.eden.views.profile;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.cell.CustomCells;
import com.lgs.eden.views.friends.AllFriends;
import javafx.application.Platform;
import javafx.fxml.FXML;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Controller for profile.fxml
 */
public class Profile {

    // ------------------------------ STATIC ----------------------------- \\

    /**
     * @return profile screen
     */
    public static Parent getScreen() {
        return reloadWith(AppWindowHandler.currentUserID());
    }

    private static Profile controller;

    /**
     * @return reloaded profile screen with profile data for userID
     * Should only be called if user is not currentUserID
     */
    public static Parent reloadWith(int userID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE.path);
        Parent parent = Utility.loadViewPane(loader);
        controller = loader.getController();
        controller.init(userID);
        return parent;
    }

    public static Profile getController() { return controller; }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML // profile info
    private Label bio;
    @FXML
    private Label userID;
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

    // current profile data, can be used in listeners
    private ProfileData data;

    /** set up profile view **/
    private void init(int userID) {
        this.data = API.imp.getProfileData(userID, AppWindowHandler.currentUserID());

        // ------------------------------ FILL ATTRIBUTES ----------------------------- \\
        this.username.setText(this.data.username); // ex: Raphiki
        this.userID.setText(String.format("%06d", this.data.userID)); // ex: 000006
        this.bio.setText(this.data.biography+""); // bio
        this.lastLogin.setText(Translate.getDate(this.data.lastSeen)); // getDate format
        this.since.setText(Translate.getDate(this.data.memberSinceDate)); // getDate format
        this.avatar.setImage(this.data.avatar); // set avatar

        setReputation(this.data);

        // ------------------------------ ADD/REMOVE FRIEND ----------------------------- \\

        boolean add = false;
        boolean remove = false;
        boolean acceptFriend = false;
        boolean refuseFriend = false;

        // todo: handle requests
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
        if (this.data.recentGames.length > 0){
            for (int column = 0; column < 3 && column < this.data.recentGames.length; column++) {
                // create
                FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE_CARD.path);
                Parent card = Utility.loadViewPane(loader);
                // fill
                ((RecentGameCard)loader.getController()).init(this.data.recentGames[column]);
                // add
                this.recentGames.add(card, column,0);
            }
        } else {
            // no recent games played
            Label l = new Label(Translate.getTranslation("game-recent-none"));
            l.getStyleClass().add("empty-message-15");
            this.recentGames.add(l, 0,0, 3, 1);
        }

        // ------------------------------ FILL FRIEND LIST ----------------------------- \\

        // add friends in the list
        if (this.data.friendNumber > 0) {
            this.friendDataListView.setItems(this.data.friends);
            this.friendDataListView.setCellFactory(friendDataListView -> new CustomCells<>(FriendCellController.load()));
        }
    }

    /** Listener of the see all friends label **/
    @FXML
    private void onSeeAllFriends(){
        AppWindowHandler.setScreen(AllFriends.getScreen(this.data.userID), ViewsPath.PROFILE);
    }

    /** Listener of the add friend button **/
    @FXML
    private void onAddFriend() {
        onAddFriend(this.data.userID);
    }
    public void onAddFriend(int friendID){
        API.imp.addFriend(friendID, AppWindowHandler.currentUserID());
        reloadWith(friendID);
    }

    /** Listener of the remove friend button **/
    @FXML
    private void onRemoveFriend() {
        onRemoveFriend(this.data.userID);
    }
    public void onRemoveFriend(int friendID) {
        API.imp.removeFriend(friendID, AppWindowHandler.currentUserID());
        reload(friendID);
    }

    /** Listener of the +1 rep label **/
    @FXML
    private void onPlusOneRep() {
        // api won't allow it
        if (AppWindowHandler.currentUserID() == data.userID) return;
        ProfileData r = API.imp.changeReputation(data.userID, AppWindowHandler.currentUserID(), true);
        if (r != null) this.setReputation(this.data = r);
    }

    /** Listener of the -1 rep label **/
    @FXML
    private void onMinusOneRep() {
        // api won't allow it
        if (AppWindowHandler.currentUserID() == data.userID) return;

        ProfileData r = API.imp.changeReputation(data.userID, AppWindowHandler.currentUserID(), false);
        if (r != null) this.setReputation(this.data = r);
    }

    private void setReputation(ProfileData data) {
        // reputation
        int rep = Integer.compare(data.reputation, 0);
        String repSigne = rep >= 0 ? "+" : "";
        String repStyle = rep == -1 ? "red-text": rep == 1 ? "green-text" : "black-text";
        this.reputation.setText(repSigne+data.reputation);
        this.reputation.getStyleClass().set(0, repStyle);

        // disable +1 and -1 visually
        if (AppWindowHandler.currentUserID() == this.data.userID){
            this.addOne.setDisable(true);
            this.removeOne.setDisable(true);
        } else {
            switch (data.score){
                case NONE:
                    this.addOne.setDisable(false);
                    this.removeOne.setDisable(false);
                    break;
                case INCREASED:
                    this.addOne.setDisable(true);
                    this.removeOne.setDisable(false);
                    break;
                case DECREASED:
                    this.addOne.setDisable(false);
                    this.removeOne.setDisable(true);
                    break;
            }
        }
    }

    /** Listener of the accept friend button **/
    @FXML
    private void onAcceptFriend() {
        onAcceptFriend(this.data.userID);
    }
    public void onAcceptFriend(int friendID) {
        API.imp.acceptFriend(friendID, AppWindowHandler.currentUserID());
        reloadWith(friendID);
    }

    /** Listener of the accept friend button **/
    @FXML
    private void onRefuseFriend() {
        onRefuseFriend(this.data.userID);
    }
    public void onRefuseFriend(int friendID) {
        API.imp.refuseFriend(friendID, AppWindowHandler.currentUserID());
        reloadWith(friendID);
    }

    // ------------------------------ UTILS ----------------------------- \\

    /** reload view **/
    public void reload(int friendID){
        AppWindowHandler.setScreen(Profile.reloadWith(friendID), ViewsPath.PROFILE);
    }

    @FXML
    private void dropFocus(){ this.bio.getParent().getParent().requestFocus(); }

}
