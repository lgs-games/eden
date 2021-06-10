package com.lgs.eden.views.friends;

import com.lgs.eden.api.API;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CellHandler;
import com.lgs.eden.views.profile.FriendCellController;
import com.lgs.eden.utils.helper.SearchPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

/**
 * List of all friends
 */
public class AllFriends {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(int userID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.FRIENDS_LIST.path);
        Parent parent = Utility.loadViewPane(loader);
        AllFriends controller = loader.getController();
        controller.init(userID);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private FlowPane online;
    @FXML
    private FlowPane offline;

    /** Init view with user ID */
    private void init(int userID) {
        ArrayList<FriendData> friendList = API.imp.getFriendList(userID);
        boolean added = false;

        // online only
        for (FriendData f : friendList) {
            if (!f.online) continue;
            if (!added) added = true;
            createFriendDiv(f, this.online);
        }

        if (!added){ this.online.getChildren().add(getEmpty(true)); }
        added = false;

        // offline only
        for (FriendData f : friendList) {
            if (f.online) continue;
            if (!added) added = true;

            createFriendDiv(f, this.offline);
        }

        if (!added){ this.offline.getChildren().add(getEmpty(false)); }
    }

    // ------------------------------ LISTENERS ----------------------------- \\

    @FXML
    public void searchForUser() {
        PopupUtils.showPopup(SearchPane.getScreen((s) -> API.imp.searchUsers(s, AppWindowHandler.currentUserID())));
    }

    // ------------------------------ UTILS ----------------------------- \\

    // empty label
    private Node getEmpty(boolean isOnline) {
        // get text
        String text = Translate.getTranslation("no-friends-here") +" ";
        text += Translate.getTranslation(isOnline ? "online" : "offline").toLowerCase();
        // create label
        Label label = new Label(text);
        label.getStyleClass().add("empty-message-15");
        return label;
    }

    // create friend div, with border, padding, set values, ... and add it to parent
    private void createFriendDiv(FriendData f, FlowPane parent) {
        CellHandler<FriendData> controller = FriendCellController.load();
        controller.init(f);
        Parent view = controller.getView();
        view.getStyleClass().add("friend-list-div");
        parent.getChildren().add(view);
    }

}
