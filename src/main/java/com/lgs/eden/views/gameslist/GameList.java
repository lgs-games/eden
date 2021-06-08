package com.lgs.eden.views.gameslist;

import com.lgs.eden.api.API;
import com.lgs.eden.api.games.BasicGameData;
import com.lgs.eden.api.games.GameViewData;
import com.lgs.eden.api.games.ShortGameViewData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.ApplicationCloseHandler;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.gameslist.cell.GameListCell;
import com.lgs.eden.views.gameslist.news.AllNews;
import com.lgs.eden.views.gameslist.news.News;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
        controller = loader.getController();
        controller.data = data;
        controller.init();
        return parent;
    }

    // current game data
    public static BasicGameData getCurrentGameData() { return controller.gameData; }

    // current controller
    private static GameList controller;
    public static GameList getController() { return controller; }

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
    @FXML
    private BorderPane gameViewPane;
    @FXML
    private Button back;
    @FXML
    private Button updateButton;
    @FXML
    private ImageView gameBackground;

    // store game data
    private BasicGameData data; // basic
    private GameViewData gameData; // complete

    // list of games
    private ObservableList<BasicGameData> myGames;
    // store old screen when changed
    private final ArrayList<Node> backupCenter = new ArrayList<>();

    private void init() {
        // fill game list
        this.myGames = API.imp.getUserGames(AppWindowHandler.currentUserID());
        // set items
        this.games.setItems(FXCollections.observableArrayList());
        search();
        // try to find if we got a game
        if (data == null && this.myGames.size() == 0) {
            // todo: empty
            System.out.println("no games");
        } else {
            if (data == null) data = this.myGames.get(0);
            // change renderer
            this.games.setCellFactory(item -> new GameListCell());

            // ------------------------------ GAME VIEW ----------------------------- \\
            // fetch game data
            this.gameData = API.imp.getGameData(AppWindowHandler.currentUserID(), data.id);

            // set view texts
            this.gameName.setText(this.gameData.name);
            this.gameVersion.setText(this.gameData.version);
            this.gameBackground.setImage(this.gameData.background);
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

    public void goToSubMenu(Parent view){
        // backup
        this.backupCenter.add(this.gameViewPane.getCenter());
        // set view in center
        this.gameViewPane.setCenter(view);
        // show back
        this.back.setVisible(true);
    }

    // ------------------------------ Listeners ----------------------------- \\

    // ------------------------------ Search ----------------------------- \\

    @FXML
    public void searchKey(KeyEvent e){
        if (e.getCode().equals(KeyCode.ESCAPE)){
            unFocusSearch();
        }
    }

    private void unFocusSearch() {
        // focusing on someone else
        Platform.runLater(() -> this.search.getParent().requestFocus());
    }

    @FXML
    public void search(){
        unFocusSearch();
        // get input
        String text = this.search.getText().trim().toLowerCase();
        // if no input, select all
        if (text.isEmpty()) {
            this.games.getItems().clear();
            this.games.getItems().addAll(this.myGames);
            fillWithBlanksSinceBug(myGames.size());
        }
        else {
            this.games.getItems().clear();
            FilteredList<BasicGameData> filtered = this.myGames.filtered((e) -> e.name.toLowerCase().contains(text));
            this.games.getItems().addAll(filtered);
            fillWithBlanksSinceBug(filtered.size());
        }
    }

    //todo: first JavaFX bug :(
    // the list seems to show old rendering when the size
    // just to two from one or go back to two.
    private void fillWithBlanksSinceBug(int size) {
        if (size == 1) size = 18;
        else if (size == 2) size = 15;
        for (int i = 0; i < size; i++) {
            this.games.getItems().add(null);
        }
    }

    // ------------------------------ MOVE ----------------------------- \\

    // go back
    @FXML
    public void backToMain(){
        // get last and remove
        int last = this.backupCenter.size() - 1;
        Node node = this.backupCenter.get(last);
        this.backupCenter.remove(last);
        // go back
        this.gameViewPane.setCenter(node);
        // empty
        if (last == 0) {
            this.back.setVisible(false);
            //fix bug, do not give focus to another button
            this.back.getParent().requestFocus();
        }
    }

    // all news
    @FXML
    public void seeAllNews(){ this.goToSubMenu(AllNews.getScreen(this.data.id)); }

    // one news
    @FXML
    public void showLastNews(){
        this.goToSubMenu(News.getScreen(this.gameData.lastNews));
    }

    @FXML
    public void showGameSettings() {
        this.goToSubMenu(GameSettings.getScreen(this.data.id));
    }

    // ------------------------------ UPDATE GAME DATA ----------------------------- \\

    // request game information update
    private volatile boolean calledUpdate = false;
    @FXML
    public void onUpdateRequest(){
        // get rid of focus
        this.updateButton.getParent().requestFocus();
        // only one per one
        if (this.calledUpdate) return;
        this.calledUpdate = true;
        // image
        ImageView image = (ImageView) this.updateButton.getGraphic();

        // call
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RotateUpdate(image), 0, 150);
        ApplicationCloseHandler.startUpdateThread(timer, () -> {
            ShortGameViewData view = API.imp.getGameDateUpdate(AppWindowHandler.currentUserID(), this.gameData.id);
            // changes values
            Platform.runLater(() -> {
                this.achievementCount.setText(""+view.playerAchievements);
                this.friendsPlaying.setText(""+view.friendsPlaying);
                this.timePlayed.setText(""+view.timePlayed);
                // done
                this.calledUpdate = false;
                image.setRotate(0); // reset
                // close
                ApplicationCloseHandler.closeUpdateThread();
            });
        });
    }

    /**
     * Rotate loading image
     */
    private static class RotateUpdate extends TimerTask {

        private final ImageView image;
        private int rotation = 0;

        public RotateUpdate(ImageView image) { this.image = image; }

        @Override
        public void run() { Platform.runLater(() -> image.setRotate(rotation += 50)); }
    }
}
