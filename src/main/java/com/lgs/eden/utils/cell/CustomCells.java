package com.lgs.eden.utils.cell;

import javafx.scene.control.ListCell;

/**
 * Allow to set some custom cell.
 * View is loaded using {@link CellHandler#getView()}
 * and then {@link CellHandler#init(Object)}
 * is called to further into text or so one using the data.
 * @param <T> data type
 */
public class CustomCells<T> extends ListCell<T> {

    // controller of the view
    private final CellHandler<T> controller;

    private T initCalled;

    public CustomCells(CellHandler<T> controller) {
        this.controller = controller;
        this.initCalled = null;
    }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    protected void updateItem(T item, boolean empty) {
        boolean isEmpty = empty || item == null;

        // call to super
        super.updateItem(item, empty);

        // no background
        setBackground(null);
        setText(null);
        // we will only show the view if we got something
        setGraphic(isEmpty ? null : this.controller.getView());

        // show item
        if (!isEmpty && !item.equals(initCalled)) {
            this.controller.init(this.initCalled = item);
        }
    }
}