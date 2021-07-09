package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CustomCells;
import com.lgs.eden.utils.helper.SearchPane;
import com.lgs.eden.views.profile.Profile;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

/**
 * Controller for message.fxml view
 */
public class Messages {

    // ------------------------------ STATIC ----------------------------- \\

    private static Messages controller;

    // no friend in particular
    public static Parent getScreen() { return getScreen("-1"); }
    // open "friendID" conv
    public static Parent getScreen(String friendID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.MESSAGES.path);
        Parent view = Utility.loadViewPane(loader);
        controller = loader.getController();
        controller.init(friendID);
        return view;
    }

    /** true if this user is the one we are chatting with **/
    public static boolean isCurrentConv(String userID) { return controller.friend.id.equals(userID); }

    /** returns the sender **/
    public static FriendData getSender(String senderID) {
        return controller.friend.id.equals(senderID) ? controller.friend : controller.user;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label userName;
    @FXML
    private ListView<MessageData> messages;
    @FXML
    private TextArea inputMessage;
    @FXML
    private Button sendMessage;
    @FXML
    private ListView<ConversationData> userList;
    @FXML
    private Button profileButton;
    @FXML
    private TextFlow friendNameTag;

    // save friendID
    private FriendData friend;
    private FriendData user;
    private ArrayList<FriendData> friendList;

    private void init(String friendID) {
        // you cannot tchat with yourself
        if (friendID.equals(AppWindowHandler.currentUserID())) friendID = "-1";
        FriendConversationView conv = getConv(friendID);

        // friends for new conversations search
        try {
            this.friendList = API.imp.getFriendList(AppWindowHandler.currentUserID(), -1);
        } catch (APIException e) {
            PopupUtils.showPopup(e);
            this.friendList = new ArrayList<>();
        }
        if (conv == null) {
            // set labels / disabled all buttons
            this.friendNameTag.getChildren().clear();
            this.friendNameTag.getChildren().add(new Label(Translate.getTranslation("no_conv")));
            // disable all
            this.sendMessage.setDisable(true);
            this.inputMessage.setDisable(true);
            this.profileButton.setDisable(true);
        } else {
            // show conv
            showConv(conv);
        }
    }

    // ------------------------------ UTILS ----------------------------- \\

    /**
     * Load conversations (left list view)
     * and load messages (right panel)
     *
     * Then add a callback waiting for messages (and conversations too if the conversation was closed)
     */
    private void showConv(FriendConversationView conv) {
        this.friend = conv.friend();
        this.user = conv.user();

        // ------------------------------ CONVERSATIONS ----------------------------- \\
        this.userList.setItems(conv.conversations());
        this.userList.setCellFactory(cellView -> new CustomCells<>(ConversationCell.load()));
        setSelected();

        // ------------------------------ MAIN DATA ----------------------------- \\
        // set message values
        this.userName.setText(conv.friend().name);

        // ------------------------------ MESSAGES ----------------------------- \\
        this.messages.setItems(conv.messages());
        this.messages.setCellFactory(cellView -> new CustomCells<>(MessageCell.load()));

        if (conv.messages().size() > 0) this.messages.scrollTo(conv.messages().size() - 1);

        // adding callback
        API.imp.setMessagesCallBack((m) -> {
            if (this.messages == null) return;
            Platform.runLater(() -> {
                this.messages.getItems().add(m);
                this.messages.scrollTo(this.messages.getItems().size() - 1);
            });
        }, (c) -> {
            if (this.userList == null) return;
            Platform.runLater(() -> this.updateConversations(c));
        }, conv);
    }

    /**
     * Look for this view data,
     * raise exception if we can't fetch any conversations or open
     * a new open if requested
     */
    private FriendConversationView getConv(String friendID) {
        FriendConversationView conv;
        try {
            conv = API.imp.getMessageWithFriend(friendID, AppWindowHandler.currentUserID());
            if (conv == null && !friendID.equals("-1")){
                // try to open the conv
                boolean b = API.imp.newConversation(friendID, AppWindowHandler.currentUserID());
                if (b) {
                    conv = API.imp.getMessageWithFriend(friendID, AppWindowHandler.currentUserID());
                } else {
                    throw new APIException(APIResponseCode.UNABLE_TO_OPEN_CONV);
                }
            }
        } catch (APIException e) {
            PopupUtils.showPopup(e);
            conv = null;
        }
        return conv;
    }

    /**
     * Update (and add if needed) a conversation in the list
     * view. The number of unread messages could have changed for
     * example.
     */
    private void updateConversations(ConversationData c) {
        int i = 0;
        ObservableList<ConversationData> items = this.userList.getItems();

        for (ConversationData d : items) {
            // change this item
            if (d.id.equals(c.id)) break;
            i++;
        }
        if (i != items.size()) {
            items.remove(i);
        }
        items.add(i, c);

        // set again as current
        if (c.id.equals(friend.id)) {
            setSelected();
            // set current read
            try {
                if (c.unreadMessagesCount != 0) {
                    API.imp.setConversationRead(this.friend.id, AppWindowHandler.currentUserID());
                }
            } catch (APIException ignore) {}
        }
    }

    // ------------------------------ EVENTS ----------------------------- \\

    private void setSelected() {
        for (ConversationData d : this.userList.getItems()) {
            if (d.id.equals(friend.id)) {
                this.userList.scrollTo(d);
                break;
            }
        }
    }

    @FXML
    public void goToProfile() {
        AppWindowHandler.setScreen(Profile.reloadWith(this.friend.id), ViewsPath.PROFILE);
    }

    @FXML
    public void onNewConversationRequest() {
        PopupUtils.showPopup(SearchPane.getScreen(
                (s) -> {
                    ArrayList<FriendData> selected = new ArrayList<>();
                    this.friendList.forEach((d) -> {
                        if (s.isEmpty() || d.name.toLowerCase().contains(s) || (d.id + "").equals(s)) {
                            selected.add(d);
                        }
                    });
                    return selected;
                }
        ));
    }

    public void onSendRequest() {
        String text = this.inputMessage.getText();
        try {
            MessageData m = API.imp.sendMessage(friend.id, user.id, text);
            messages.getItems().add(m);
        } catch (APIException e) {
            PopupUtils.showPopup(e);
        }
    }

}
