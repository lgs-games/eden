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
        FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE.path);
        Parent parent = Utility.loadViewPane(loader);
        Profile controller = loader.getController();
        controller.init(currentUserID);
        return parent;
    }

    // logged user id
    private static int currentUserID = -1;
    public static void setUserID(int userID) { currentUserID = userID; }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    public Label bio;
    @FXML
    public Label userID;
    @FXML
    public Label since;
    @FXML
    public Label username;
    @FXML
    public Label lastLogin;
    @FXML
    public Label reputation;
    @FXML
    private GridPane recentGames;
    @FXML
    private ListView<FriendData> friendDataListView;

    // current profile data, can be used in listeners
    private ProfileData data;

    private void init(int userID) {
        this.data = Api.getProfileData(userID);

        // ------------------------------ FILL ATTRIBUTES ----------------------------- \\
        this.username.setText(this.data.username);
        this.userID.setText(String.format("%06d", this.data.userID));
        this.bio.setText(this.data.biography+"");
        this.lastLogin.setText(Translate.getDate(this.data.lastSeen));
        this.since.setText(Translate.getDate(this.data.memberSinceDate));

        // reputation
        // todo: disabled + and - if self-profile
        if (this.data.reputation > 0){
            this.reputation.setText("+"+this.data.reputation);
            this.reputation.getStyleClass().add("green-text");
        } else if (this.data.reputation < 0){
            this.reputation.setText("-"+this.data.reputation);
            this.reputation.getStyleClass().add("red-text");
        } else {
            this.reputation.setText(this.data.reputation+"");
        }

        // ------------------------------ FILL RECENT GAMES ----------------------------- \\

        // show the last 3 games
        if (data.recentGames.length > 0){
            for (int column = 0; column < 3 && column < data.recentGames.length; column++) {
                // create
                FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE_CARD.path);
                Parent card = Utility.loadViewPane(loader);
                // fill
                ((RecentGameCard)loader.getController()).init(data.recentGames[column]);
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

        if (data.friendNumber > 0) {
            this.friendDataListView.setItems(data.friends);
            this.friendDataListView.setCellFactory(friendDataListView -> new FriendCell());
        }

    }

    /** Listener of the see all friends label **/
    @FXML
    private void onSeeAllFriends(){ AppWindowHandler.setScreen(AllFriends.getScreen(data.userID)); }

    /** Listener of the add friend button **/
    @FXML
    private void onAddFriend() {
        System.out.println("Wanna have some new friends ?");
    }

    /** Listener of the +1 rep label **/
    @FXML
    private void onPlusOneRep() {
        System.out.println("+1 rep for him");
    }

    /** Listener of the -1 rep label **/
    @FXML
    private void onMinusOneRep() {
        System.out.println("-1 rep for him");
    }

}
