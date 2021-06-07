package com.lgs.eden.api.local;

import com.lgs.eden.api.games.*;
import com.lgs.eden.api.news.BasicNewsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Implementation of GameAPI
 */
class GamesHandler implements GameAPI {

    @Override
    public EdenVersionData getEdenVersion() {
        // fake some delay
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new EdenVersionData("1.0.0", 147);
    }

    @Override
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code) {
        ArrayList<MarketplaceGameData> games = new ArrayList<>();

        MarketplaceGameData.gameCount = 10;

        ArrayList<String> tags = new ArrayList<>();
        tags.add("1-player");
        tags.add("Environment line");
        tags.add("Original game");
        tags.add("ENSIIE");
        tags.add("Open Source");

        ArrayList<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("Français");

        GameUpdateData updatePrim = new GameUpdateData("3.1.0", 88);

        MarketplaceGameData prim = new MarketplaceGameData(
                0, "Prim",
                "Prim is a production line game request by Dimitri Watel, teacher at ENSIIE engineering school." +
                        " The main idea is that you will produce resources in order to close a gate. The folklore of " +
                        "the game is the ENSIIE so you will have a lot of ENSIIE-related content. Have fun !",
                "/games/prim-icon.png","/games/prim-pic.png",
                tags,
                languages,
                updatePrim
        );

        games.add(prim);

        return games;
    }

    @Override
    public GameViewData getGameData(int userID, int gameID) {
        if (gameID == 0){
            BasicNewsData prim = new BasicNewsData(
                    "Version 3.1.0 released",
                    "/news/news1.png",
                    "We patched a lot of things and tried to improve" +
                            "the game to make it less easy and more fun to play.",
                    0
            );
            return new GameViewData(
                    0, "Prim", "/games/prim-icon.png", "3.1.0", prim,
                    0, 7, 0, 54
            );
        } else {
            BasicNewsData enigma = new BasicNewsData(
                    "Enigma (remaster)",
                    "/news/news2.png",
                    "Enigma will come back, full remade! New UI, new functionalities, multiplayer...",
                    1
            );
            return new GameViewData(
                    1, "Enigma", "/games/enigma-icon.png", "2.0.0", enigma,
                    0, 24, 3, 4500
            );
        }
    }

    public ObservableList<BasicGameData> getUserGames(int userID) {
        ObservableList<BasicGameData> games = FXCollections.observableArrayList();
        games.add(new BasicGameData(1, "Enigma", "/games/enigma-icon.png"));
        games.add(new BasicGameData(0, "Prim", "/games/prim-icon.png"));
        return games;
    }
}
