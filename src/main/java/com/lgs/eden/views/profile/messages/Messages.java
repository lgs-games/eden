package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.API;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CustomCells;
import com.lgs.eden.views.profile.Profile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

/**
 * Controller for message.fxml view
 */
public class Messages {

    // ------------------------------ STATIC ----------------------------- \\

    private static Messages controller;

    // no friend in particular
    public static Parent getScreen() { return getScreen(-1); }
    // open "friendID" conv
    public static Parent getScreen(int friendID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.MESSAGES.path);
        Parent view = Utility.loadViewPane(loader);
        controller = loader.getController();
        controller.init(friendID);
        return view;
    }

    /** true if this user is the one we are chatting with **/
    public static boolean isCurrentConv(int userID) { return controller.friend.id == userID; }

    /** returns the sender **/
    public static FriendData getSender(int senderID) {
        return controller.friend.id == senderID ? controller.friend : controller.user;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label userName;
    @FXML
    private Label userID;
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

    private void init(int friendID) {
        // you cannot tchat with yourself
        if (friendID == AppWindowHandler.currentUserID()) friendID = -1;
        FriendConversationView conv = API.imp.getMessageWithFriend(friendID, AppWindowHandler.currentUserID());
        if (conv == null) {
            this.friendNameTag.getChildren().clear();
            this.friendNameTag.getChildren().add(new Label(Translate.getTranslation("no_conv")));
            // disable all
            this.sendMessage.setDisable(true);
            this.inputMessage.setDisable(true);
            this.profileButton.setDisable(true);
        } else {
            this.friend = conv.friend;
            this.user = conv.user;

            // ------------------------------ CONVERSATIONS ----------------------------- \\
            this.userList.setItems(conv.conversations);
            this.userList.setCellFactory(cellView -> new CustomCells<>(ConversationCell.load()));

            // ------------------------------ MAIN DATA ----------------------------- \\
            // set message values
            this.userName.setText(conv.friend.name);
            this.userID.setText(String.format("%06d", conv.friend.id));

            // ------------------------------ MESSAGES ----------------------------- \\
            this.messages.setItems(conv.messages);
            this.messages.setCellFactory(cellView -> new CustomCells<>(MessageCell.load()));
        }
    }

    @FXML
    public void goToProfile(){
        AppWindowHandler.setScreen(Profile.reloadWith(this.friend.id), ViewsPath.PROFILE);
    }
}
