package com.lgs.eden.views.friends;

import com.lgs.eden.api.Api;
import com.lgs.eden.api.wrapper.FriendData;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.profile.listcells.FriendCellController;
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

    public FlowPane online;
    public FlowPane offline;

    public static Parent getScreen(int userID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.FRIENDS_LIST.path);
        Parent parent = Utility.loadViewPane(loader);
        AllFriends controller = loader.getController();
        controller.init(userID);
        return parent;
    }

    /** Init view with user ID */
    private void init(int userID) {
        ArrayList<FriendData> friendList = Api.getFriendList(userID);
        boolean added = false;

        // online only
        for (FriendData f : friendList) {
            if (!f.online) continue;
            if (!added) added = true;
            createFriendDiv(f, this.online);
        }

        if (!added){ this.online.getChildren().add(getEmpty()); }
        added = false;

        // offline only
        for (FriendData f : friendList) {
            if (f.online) continue;
            if (!added) added = true;

            createFriendDiv(f, this.offline);
        }

        if (!added){ this.offline.getChildren().add(getEmpty()); }
    }

    // empty label
    private Node getEmpty() {
        Label label = new Label(Translate.getTranslation("no-friends-here"));
        label.getStyleClass().add("no-friends-label");
        return label;
    }

    // create friend div, with border, padding, set values, ... and add it to parent
    private void createFriendDiv(FriendData f, FlowPane parent) {
        FriendCellController controller = FriendCellController.load();
        controller.init(f);
        Parent view = controller.getView();
        view.getStyleClass().add("friend-list-div");
        // add listener
        view.setOnMouseClicked((e) -> controller.onWantProfile());
        parent.getChildren().add(view);
    }

}
