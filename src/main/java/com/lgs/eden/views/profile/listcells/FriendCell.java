package com.lgs.eden.views.profile.listcells;

import com.lgs.eden.api.profile.friends.FriendData;
import javafx.scene.control.ListCell;

/**
 * View for a cell
 */
public class FriendCell extends ListCell<FriendData> {

    // controller of the view
    private final FriendCellController controller;

    public FriendCell() {
        this.controller = FriendCellController.load();
    }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    protected void updateItem(FriendData item, boolean empty) {
        boolean isEmpty = empty || item == null;

        // no background
        setBackground(null);
        setText(null);
        // we will only show the view if we got something
        setGraphic(isEmpty ? null : this.controller.getView());

        // show item
        if (!isEmpty) this.controller.init(item);
    }

}
