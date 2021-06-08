package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.API;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.conversation.ConversationData;
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
    public static boolean isCurrentConv(int userID) { return controller.friendID == userID; }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label userName;
    @FXML
    private Label userID;
    @FXML
    private ListView<?> messages;
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
    private int friendID;

    private void init(int friendID) {
        // you cannot tchat with yourself
        if (friendID == AppWindowHandler.currentUserID()) friendID = -1;
        FriendConversationView conv = API.imp.getMessageWithFriend(friendID);
        if (conv == null){
            this.friendNameTag.getChildren().clear();
            this.friendNameTag.getChildren().add(new Label(Translate.getTranslation("no_conv")));

            // disable all
            this.sendMessage.setDisable(true);
            this.inputMessage.setDisable(true);
            this.profileButton.setDisable(true);
        } else {
            this.friendID = conv.friendID;
            this.userList.setItems(conv.conversations);
            this.userList.setCellFactory(cellView -> new CustomCells<>(ConversationCell.load()));

            FriendData friendData = null;
            for (FriendData d:conv.conversations) {
                if (d.id == conv.friendID){
                    friendData = d;
                    break;
                }
            }
            // useless, checked in the API
            if (friendData == null) friendData = conv.conversations.get(0);

            // set message values
            this.userName.setText(friendData.name);
            this.userID.setText(String.format("%06d", friendData.id));
        }
    }

    @FXML
    public void goToProfile(){
        AppWindowHandler.setScreen(Profile.reloadWith(this.friendID), ViewsPath.PROFILE);
    }
}
