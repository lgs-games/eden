package com.lgs.eden.views.gameslist;

import com.lgs.eden.api.API;
import com.lgs.eden.api.games.BasicGameData;
import com.lgs.eden.api.games.GameViewData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.gameslist.cell.GameListCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    private Label gameName;
    @FXML
    private Label gameVersion;
    @FXML
    private ImageView lastNewsImage;
    @FXML
    private Label lastNewsTitle;
    @FXML
    private Label lastNewsDesc;
    @FXML
    private Label achievementCount;
    @FXML
    private Label achievementMax;
    @FXML
    private Label friendsPlaying;
    @FXML
    private Label timePlayed;
    @FXML
    private TextField search;
    @FXML
    private ListView<BasicGameData> games;

    // store game data
    private GameViewData gameData;

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

            // ------------------------------ GAME VIEW ----------------------------- \\
            this.gameData = API.imp.getGameData(AppWindowHandler.currentUserID(), data.id);
            this.gameName.setText(this.gameData.name);
            this.gameVersion.setText(this.gameData.version);
            // news
            this.lastNewsTitle.setText(this.gameData.lastNews.title);
            this.lastNewsDesc.setText(this.gameData.lastNews.desc);
            this.lastNewsImage.setImage(this.gameData.lastNews.image);
            // achievements
            this.achievementCount.setText(""+this.gameData.playerAchievements);
            this.achievementMax.setText(""+this.gameData.numberOfAchievements);
            // others
            this.friendsPlaying.setText(""+this.gameData.friendsPlaying);
            this.timePlayed.setText(""+this.gameData.timePlayed);
        }
    }
}
