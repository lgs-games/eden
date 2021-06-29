package com.lgs.eden.views.gameslist.cell;

import com.lgs.eden.api.games.BasicGameData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.cell.CellHandler;
import com.lgs.eden.views.gameslist.GameList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * A cell of the GameList. Filled with
 * a game : icon + name + id. ID will be used
 * to show the game in the right panel.
 */
public class GameListCell implements CellHandler<BasicGameData> {

    // ------------------------------ STATIC ----------------------------- \\

    public static CellHandler<BasicGameData> load() {
        return CellHandler.load(ViewsPath.GAMES_LIST_CELL);
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private ImageView gameAvatar;
    @FXML
    private Label gameName;
    // info about the game in the view
    private BasicGameData info;

    @FXML
    public void init(BasicGameData data) {
        this.gameName.setText(data.name);
        this.gameName.setMaxWidth(150);
        this.gameAvatar.setImage(data.icon);

        // add change game listener
        this.view.setOnMouseClicked((e) -> this.changeGame());

        // set selected
        setSelected(data.equals(GameList.getCurrentGameData()));

        // save
        this.info = data;
    }

    public void setSelected(boolean selected) {
        if (selected) {
            this.gameName.getStyleClass().set(0, "yellow-text");
        } else {
            this.gameName.getStyleClass().set(0, "white-text");
        }
    }

    // change the current game with this game
    public void changeGame() { AppWindowHandler.setScreen(GameList.getScreen(this.info), ViewsPath.GAMES); }

    // ------------------------------ VIEW ----------------------------- \\

    // our view
    private Pane view;

    @Override
    public Pane getView() { return this.view; }

    @Override
    public void setView(Pane view) { this.view = view; }
}
