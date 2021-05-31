package com.lgs.eden.views.profile.listcells;

import com.lgs.eden.api.wrapper.FriendData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 * View for a cell
 */
public class FriendCell extends ListCell<FriendData> {

    // controller of the view
    private final FriendCellController controller;

    public FriendCell() {
        this.controller = FriendCellController.load();
        // add listener
        this.setOnMouseClicked((e)->this.controller.onWantProfile());
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
        if (!isEmpty) {
            this.controller.setName(item.name);
            this.controller.setAvatar(item.avatar);
        }
    }

}
