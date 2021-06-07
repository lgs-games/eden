package com.lgs.eden.views.gameslist;

import com.lgs.eden.api.games.BasicGameData;
import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.gameslist.cell.GameListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Pane with the list of games
 * at the left and a game at the right.
 */
public class GameList {
    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_LIST.path);
        Parent parent = Utility.loadViewPane(loader);
        GameList controller = loader.getController();
        controller.init();
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private TextField search;
    @FXML
    private ListView<BasicGameData> games;

    private void init() {
        // fill game list
        ObservableList<BasicGameData> myGames = Config.getInstalledGames();
        this.games.setItems(myGames);
        this.games.setCellFactory(item -> new GameListCell());
    }
}
