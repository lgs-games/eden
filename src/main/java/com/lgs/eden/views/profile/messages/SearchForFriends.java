package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.API;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CustomCells;
import com.lgs.eden.views.profile.FriendCellController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObservableList<FriendData> originalList;

    private void init(int userID) {
        ArrayList<FriendData> friendList = API.imp.getFriendList(userID);
        this.originalList = FXCollections.observableArrayList(friendList);
        this.friends.setItems(getFriendListCopy());
        this.friends.setCellFactory(friendDataListView -> new CustomCells<>(FriendCellController.load()));
    }

    @FXML
    public void onSearch(){
        String text = this.search.getText().trim().toLowerCase();
        if (text.isEmpty()){
            this.friends.setItems(getFriendListCopy());
        } else {
            ObservableList<FriendData> copy = FXCollections.observableArrayList();
            for (FriendData d: originalList) {
                if (d.name.toLowerCase().contains(text) || (d.id+"").equals(text)) {
                    copy.add(d);
                }
            }
            this.friends.setItems(copy);
        }
    }

    public ObservableList<FriendData> getFriendListCopy(){
        ObservableList<FriendData> copy = FXCollections.observableArrayList();
        copy.addAll(originalList);
        return copy;
    }

}
