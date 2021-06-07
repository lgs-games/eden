package com.lgs.eden.views.gameslist.cell;

import com.lgs.eden.api.games.BasicGameData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * A cell of the GameList. Filled with
 * a game : icon + name + id. ID will be used
 * to show the game in the right panel.
 */
public class GameListCellController {

    // ------------------------------ STATIC ----------------------------- \\

    public static GameListCellController load() {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_LIST_CELL.path);
        Parent parent = Utility.loadViewPane(loader);
        GameListCellController controller = loader.getController();
        controller.view = parent;
        return controller;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private ImageView gameAvatar;
    @FXML
    private Label gameName;
    // our view
    private Parent view;
    // info about the game in the view
    private BasicGameData info;

    public void init(BasicGameData data){
        this.gameName.setText(data.name);
        this.gameName.setMaxWidth(150);

        this.gameAvatar.setImage(data.icon);

        this.info = data;
    }

    // our view
    public Parent getView() { return view; }
}
