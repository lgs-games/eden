package com.lgs.eden.views.profile.messages;

import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CellHandler;
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

    @Override
    public void init(ConversationData item) {

    }

    // ------------------------------ VIEW ----------------------------- \\

    private Pane view;

    @Override
    public Pane getView() { return this.view; }

    @Override
    public void setView(Pane view) { this.view = view; }

}
