package com.lgs.eden.views.profile.listcells;

import com.lgs.eden.api.wrapper.FriendData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class FriendCell extends ListCell<FriendData> {

    private final Node graphic;

    private final FriendCellController controller;

    public FriendCell() {
        FXMLLoader loader = Utility.loadView(ViewsPath.FRIEND_CELL.path);
        this.graphic = Utility.loadViewPane(loader);
        this.controller = loader.getController();
    }

    @Override
    protected void updateItem(FriendData item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            controller.setName(item.getName());
            controller.setAvatar(item.getAvatar());
            setGraphic(graphic);
        }
    }

}
