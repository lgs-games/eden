package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CellHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * View for a message cell
 */
public class MessageCell implements CellHandler<MessageData> {

    // ------------------------------ STATIC ----------------------------- \\

    public static CellHandler<MessageData> load() {
        return CellHandler.load(ViewsPath.MESSAGE_CELL);
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private ImageView avatar;
    @FXML
    private Label username;
    @FXML
    private Label date;
    @FXML
    private Label content;

    @Override
    public void init(MessageData item) {
        FriendData sender = Messages.getSender(item.senderID);

        this.username.setText(sender.name);
        this.avatar.setImage(sender.avatar);
        this.date.setText(Translate.getDate(item.date, "h:mm - d%% MMM yyyy"));
        this.content.setText(item.getMessageAsText());
    }

    // ------------------------------ VIEW ----------------------------- \\

    private Pane view;

    @Override
    public Pane getView() { return this.view; }

    @Override
    public void setView(Pane view) { this.view = view; }
}
