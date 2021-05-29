package com.lgs.eden.views.profile;

import com.lgs.eden.api.Api;
import com.lgs.eden.api.wrapper.FriendData;
import com.lgs.eden.api.wrapper.RecentGameData;
import com.lgs.eden.utils.ModifiableObservableList;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.views.profile.listcells.FriendCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Date;

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
        controller.init();
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private final RecentGameData[] recentGamesData;
    private int usernameID;
    private int friendNumber = 4;
    private int reputation;
    private short status;

    private String biography;
    private ObservableList<FriendData> friendDataObservableList = new ModifiableObservableList<>();

    private Date lastSeen;
    private Date memberSinceDate;

    @FXML
    private GridPane recentGames;

    @FXML
    private ListView<FriendData> friendDataListView;

    public Profile() {
        // todo: that's a fake of API data
        this.recentGamesData = new RecentGameData[]{
                new RecentGameData(Utility.loadImage("/games/prim-icon.png"), "Prim", 0, RecentGameData.PLAYING),
                new RecentGameData(Utility.loadImage("/games/enigma-icon.png"), "Enigma", 1020, 30)
        };
    }

    private void init() {
        // ------------------------------ FILL RECENT GAMES ----------------------------- \\

        // show the last 3 games
        if (recentGamesData.length > 0){
            for (int column = 0; column < 3 && column < recentGamesData.length; column++) {
                // create
                FXMLLoader loader = Utility.loadView("/fxml/profile/card.fxml");
                Parent card = Utility.loadViewPane(loader);
                // fill
                ((RecentGameCard)loader.getController()).init(recentGamesData[column]);
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
        // ...

        if (friendNumber > 0) {
            ArrayList<FriendData> tmp = Api.getFriendList();
            this.friendDataObservableList.addAll(tmp);
            this.friendDataListView.setItems(friendDataObservableList);
            this.friendDataListView.setCellFactory(friendDataListView -> new FriendCell());
        }

    }

    /** Listener of the see all friends label **/
    @FXML
    private void onSeeAllFriends(){
        System.out.println("So you want to see all your friends...");
    }

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
