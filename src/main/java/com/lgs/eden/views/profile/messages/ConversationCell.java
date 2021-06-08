package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CellHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * A conversation cell in messages view.
 */
public class ConversationCell implements CellHandler<ConversationData> {

    // ------------------------------ STATIC ----------------------------- \\

    public static CellHandler<ConversationData> load() {
        return CellHandler.load(ViewsPath.MESSAGES_CELL);
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label friendName;
    @FXML
    private Label friendStatus;
    @FXML
    private ImageView friendAvatar;
    @FXML
    private Button newMessages;

    @Override
    public void init(ConversationData item) {
        String message = Translate.getTranslation(item.online ? "online" : "offline");

        this.friendName.setText(item.name);
        this.friendAvatar.setImage(item.avatar);
        this.friendStatus.setText(message);
        // unread messages
        if (item.unreadMessagesCount > 0){
            this.newMessages.setText(""+item.unreadMessagesCount);
        } else {
            this.newMessages.setVisible(false);
        }
    }

    // ------------------------------ VIEW ----------------------------- \\

    private Pane view;

    @Override
    public Pane getView() { return this.view; }

    @Override
    public void setView(Pane view) { this.view = view; }

}
