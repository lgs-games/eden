package com.lgs.eden.views.gameslist.cell;

import com.lgs.eden.api.games.BasicGameData;
import javafx.scene.control.ListCell;

/**
 * View for a cell
 */
public class GameListCell extends ListCell<BasicGameData> {

    // controller of the view
    private final GameListCellController controller;

    public GameListCell() {
        this.controller = GameListCellController.load();
    }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    protected void updateItem(BasicGameData item, boolean empty) {
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
