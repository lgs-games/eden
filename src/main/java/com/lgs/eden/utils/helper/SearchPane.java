package com.lgs.eden.utils.helper;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CustomCells;
import com.lgs.eden.views.profile.FriendCellController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

/**
 * Search for user/friend controller
 */
public class SearchPane {

    /** Called when filter text is set */
    public interface FilterCallback {
        /** returns the filtered list **/
        ArrayList<FriendData> filter(String text);
    }

    public static Parent getScreen(FilterCallback handler) {
        FXMLLoader loader = Utility.loadView(ViewsPath.SEARCH.path);
        Parent parent = Utility.loadViewPane(loader);
        SearchPane controller = loader.getController();
        controller.init(handler);
        return parent;
    }

    @FXML
    private TextField search;
    @FXML
    private ListView<FriendData> users;

    private FilterCallback handler;

    private void init(FilterCallback handler) {
        this.handler = handler;
        ObservableList<FriendData> userListObservable = FXCollections.observableArrayList();
        this.users.setItems(userListObservable);
        this.users.setCellFactory(friendDataListView -> new CustomCells<>(FriendCellController.load()));

        // trigger a first search
        onSearch();
    }

    @FXML
    public void onSearch() {
        String text = this.search.getText().trim().toLowerCase();
        Platform.runLater(() -> {
            ArrayList<FriendData> filter = this.handler.filter(text);
            users.setItems(FXCollections.observableArrayList(filter));
        });
    }

}
