package com.lgs.eden.views.profile;

import com.lgs.eden.api.Api;
import com.lgs.eden.api.wrapper.FriendData;
import com.lgs.eden.api.wrapper.ProfileData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.views.friends.AllFriends;
import com.lgs.eden.views.profile.listcells.FriendCell;
import javafx.fxml.FXML;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    /**
     * @return reloaded profile screen with profile data for userID
     * Should only be called if user is not currentUserID
     */
    public static Parent reloadWith(int userID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE.path);
        Parent parent = Utility.loadViewPane(loader);
        Profile controller = loader.getController();
        controller.init(userID);
        return parent;
    }

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

    // current profile data, can be used in listeners
    private ProfileData data;

    /** set up profile view **/
    private void init(int userID) {
        this.data = Api.getProfileData(userID);

        // ------------------------------ FILL ATTRIBUTES ----------------------------- \\
        this.username.setText(this.data.username); // ex: Raphiki
        this.userID.setText(String.format("%06d", this.data.userID)); // ex: 000006
        this.bio.setText(this.data.biography+""); // ...
        this.lastLogin.setText(Translate.getDate(this.data.lastSeen)); // getDate format
        this.since.setText(Translate.getDate(this.data.memberSinceDate)); // getDate format
        this.avatar.setImage(this.data.avatar); // set avatar

        // reputation
        int rep = Integer.compare(this.data.reputation, 0);
        String repSigne = rep == -1 ? "-": rep == 1 ? "+" : "";
        String repStyle = rep == -1 ? "red-text": rep == 1 ? "green-text" : "";
        this.reputation.setText(repSigne+this.data.reputation);
        if(!repStyle.isEmpty()) this.reputation.getStyleClass().add(repStyle);
        // disable +1 and -1 visually
        if (AppWindowHandler.currentUserID() == this.data.userID){
            this.addOne.setDisable(true);
            this.removeOne.setDisable(true);
        }

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
            l.getStyleClass().add("profile-game-label");
            this.recentGames.add(l, 0,0, 3, 1);
        }

        // ------------------------------ FILL FRIEND LIST ----------------------------- \\

        // add friends in the list
        if (this.data.friendNumber > 0) {
            this.friendDataListView.setItems(this.data.friends);
            this.friendDataListView.setCellFactory(friendDataListView -> new FriendCell());
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
        System.out.println("Wanna have some new friends ?");
    }

    /** Listener of the +1 rep label **/
    @FXML
    private void onPlusOneRep() {
        // api won't allow it
        if (AppWindowHandler.currentUserID() == data.userID) return;

        System.out.println("+1 rep for "+data.userID+" ("+data.username+")");
    }

    /** Listener of the -1 rep label **/
    @FXML
    private void onMinusOneRep() {
        // api won't allow it
        if (AppWindowHandler.currentUserID() == data.userID) return;

        System.out.println("-1 rep for "+data.userID+" ("+data.username+")");
    }

}
