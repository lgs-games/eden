package com.lgs.eden.utils.cell;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 * Interface in order to use the convenience
 * CustomCell class.
 * @param <T> the type of the data of a cell
 * @see CustomCells
 */
public interface CellHandler<T> {

    // ------------------------------ INSTANCE ----------------------------- \\

    /** Set the view for a cell **/
    void setView(Pane view);

    /** Returns the view for a cell **/
    Pane getView();

    /**
     * Called with the item in order to
     * render the view using the item.
     */
    void init(T item);

    // ------------------------------ STATIC ----------------------------- \\

    static <T> CellHandler<T> load(ViewsPath viewsPath) {
        FXMLLoader loader = Utility.loadView(viewsPath.path);
        Parent view = Utility.loadViewPane(loader);
        CellHandler<T> controller = loader.getController();
        controller.setView((Pane) view);
        return controller;
    }
}
