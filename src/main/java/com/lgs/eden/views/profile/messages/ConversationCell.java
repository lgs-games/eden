package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.application.AppWindowHandler;
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

    private ConversationData item;

    @Override
    public void init(ConversationData item) {
        this.item = item;
        this.friendName.setText(item.name);
        this.friendAvatar.setImage(item.avatar);
        // status
        this.friendStatus.setText(Translate.getTranslation(item.online ? "online" : "offline"));
        this.friendStatus.getStyleClass().set(0, item.online ? "green-text" : "red-text");
        // unread messages
        if (item.unreadMessagesCount > 0) this.newMessages.setText(""+item.unreadMessagesCount);
        else this.newMessages.setVisible(false);
        // background
        if (Messages.isCurrentConv(item.id)){
            this.view.getStyleClass().add("white-box");
        } else {
            this.view.getStyleClass().add("conv-bg");
        }
    }

    /**
     * Wants to send a message to this person
     */
    @FXML
    private void onWantMessage() {
        if (Messages.isCurrentConv(item.id)) return; // no need is already current
        AppWindowHandler.setScreen(Messages.getScreen(this.item.id), ViewsPath.PROFILE);
    }

    // ------------------------------ VIEW ----------------------------- \\

    private Pane view;

    @Override
    public Pane getView() { return this.view; }

    @Override
    public void setView(Pane view) { this.view = view; }

}
