package com.lgs.eden.views.gameslist;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.friends.AllFriends;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * A cell of the GameList. Filled with
 * a game : icon + name + id. ID will be used
 * to show the game in the right panel.
 */
public class GameListCell {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_LIST_CELL.path);
        Parent parent = Utility.loadViewPane(loader);
        GameListCell controller = loader.getController();
        controller.init();
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    public ImageView gameAvatar;
    public Label gameName;

    private void init(){

    }
}
