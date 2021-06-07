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
        return getScreen(null);
    }

    public static Parent getScreen(BasicGameData data) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_LIST.path);
        Parent parent = Utility.loadViewPane(loader);
        GameList controller = loader.getController();
        GameList.data = data;
        controller.init();
        return parent;
    }

    private static BasicGameData data;
    public static BasicGameData getCurrentGameData() { return data; }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private TextField search;
    @FXML
    private ListView<BasicGameData> games;

    private void init() {
        // fill game list
        ObservableList<BasicGameData> myGames = Config.getInstalledGames();
        // set items
        this.games.setItems(myGames);
        // try to find if we got a game
        if (data == null && myGames.size() == 0){
            System.out.println("no games");
        } else {
            if (data == null) data = myGames.get(0);
            // change renderer
            this.games.setCellFactory(item -> new GameListCell());
        }
    }
}
