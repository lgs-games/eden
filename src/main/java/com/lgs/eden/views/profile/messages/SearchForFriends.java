package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.API;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CustomCells;
import com.lgs.eden.views.profile.FriendCellController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Search for user/friend controller
 */
public class SearchForFriends {

    public static Parent getScreen(int userID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.SEARCH.path);
        Parent parent = Utility.loadViewPane(loader);
        SearchForFriends controller = loader.getController();
        controller.init(userID);
        return parent;
    }

    @FXML
    private TextField search;
    @FXML
    private ListView<FriendData> friends;

    private ArrayList<FriendData> friendList;
    private ObservableList<FriendData> friendListObservable;

    private void init(int userID) {
        this.friendList = API.imp.getFriendList(userID);
        this.friendListObservable = FXCollections.observableArrayList(friendList);
        this.friends.setItems(friendListObservable);
        this.friends.setCellFactory(friendDataListView -> new CustomCells<>(FriendCellController.load()));
    }

    @FXML
    public void onSearch(){
        String text = this.search.getText().trim().toLowerCase();

        Platform.runLater(() -> {
            this.friendListObservable.clear();
            this.friendList.forEach((d) -> {
                if (text.isEmpty() || d.name.toLowerCase().contains(text) || (d.id+"").equals(text))
                    this.friendListObservable.add(d);
            });
        });
    }

}
