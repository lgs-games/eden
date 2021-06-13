package com.lgs.eden.views.friends;

import com.lgs.eden.api.API;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;
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
import java.util.function.Predicate;

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

    @FXML
    private FlowPane request;
    @FXML
    private FlowPane gotRequested;

    /** Init view with user ID */
    private void init(int userID) {
        ArrayList<FriendData> friendList = API.imp.getFriendList(userID, -1);

        // online only
        make(friendList, (f) -> f.online, this.online, DivName.ONLINE);

        // offline only
        make(friendList, (f) -> !f.online, this.offline, DivName.OFFLINE);

        // get requests
        friendList = API.imp.getRequests(userID, -1);
        make(friendList, (f) -> f.friendShipStatus.equals(FriendShipStatus.REQUESTED), this.request, DivName.REQUEST_DIV);
        make(friendList, (f) -> f.friendShipStatus.equals(FriendShipStatus.GOT_REQUESTED), this.gotRequested, DivName.GOT_REQUESTED_DIV);
    }

    /**
     * Fill friend div
     */
    private void make(ArrayList<FriendData> friendList, Predicate<FriendData> p, FlowPane div, DivName name){
        boolean added = false;
        for (FriendData f : friendList) {
            if (!p.test(f)) continue;
            if (!added) added = true;
            createFriendDiv(f, div);
        }

        if (!added){ div.getChildren().add(getEmpty(name)); }
    }

    // ------------------------------ LISTENERS ----------------------------- \\

    @FXML
    public void searchForUser() {
        PopupUtils.showPopup(SearchPane.getScreen((s) -> API.imp.searchUsers(s, AppWindowHandler.currentUserID())));
    }

    // ------------------------------ UTILS ----------------------------- \\

    enum DivName {
        ONLINE, OFFLINE, REQUEST_DIV, GOT_REQUESTED_DIV;

        @Override
        public String toString() {
            return Translate.getTranslation(name().toLowerCase()).toLowerCase();
        }
    }

    // empty label
    private Node getEmpty(DivName name) {
        // get text
        String text = Translate.getTranslation("no-friends-here") +" ";
        text += name.toString();
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
