package com.lgs.eden.utils.cell;

import javafx.scene.Parent;

/**
 * Interface in order to use the convenience
 * CustomCell class.
 * @param <T> the type of the data of a cell
 * @see CustomCells
 */
public interface CellHandler<T> {

    /**
     * Returns the view for a cell
     */
    Parent getView();

    /**
     * Called with the item in order to
     * render the view using the item.
     */
    void init(T item);
}
