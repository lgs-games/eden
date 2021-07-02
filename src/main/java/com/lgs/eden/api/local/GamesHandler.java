package com.lgs.eden.api.local;

import com.lgs.eden.api.APIHelper;
import com.lgs.eden.api.games.*;
import com.lgs.eden.api.news.BasicNewsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementation of GameAPI
 */
class GamesHandler implements GameAPI {

    // games
    private ObservableList<BasicGameData> games = null;

    @Override
    public EdenVersionData getEdenVersion(String code, String os) {
        APIHelper.fakeDelay(1000);
        return new EdenVersionData(
                "1.0.0",
                "https://lgs-games.com/assets/exe/eden-setup-1.0.0.exe",
                113
        );
    }

    @Override
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, String userID, String os) {
        initGames(userID); // init
        ArrayList<MarketplaceGameData> games = new ArrayList<>();

        MarketplaceGameData.gameCount = 1;

        ArrayList<String> tags = new ArrayList<>();
        tags.add("1-player");
        tags.add("Environment line");
        tags.add("Original game");
        tags.add("ENSIIE");
        tags.add("Open Source");

        ArrayList<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("Français");

        MarketplaceGameData prim = new MarketplaceGameData(
                "0", "Prim", "3.1.0", "88",
                "Prim is a production line game request by Dimitri Watel, teacher at ENSIIE engineering school." +
                        " The main idea is that you will produce resources in order to close a gate. The folklore of " +
                        "the game is the ENSIIE so you will have a lot of ENSIIE-related content. Have fun !",
                "/games/prim-icon.png", "/games/prim-pic.png",
                tags,
                languages,
                this.games.contains(new BasicGameData("0", "Prim", null))
        );

        games.add(prim);

        return games;
    }

    @Override
    public GameViewData getGameData(String userID, String gameID, String lang, String os) {
        if (gameID.equals("0")) {
            BasicNewsData prim = new BasicNewsData(
                    "Version 3.1.0 released",
                    "/news/news1.png",
                    "We patched a lot of things and tried to improve " +
                            "the game to make it less easy and more fun to play.",
                    "https://lgs-games.com/api/news/test.md",
                    Date.from(Instant.now())
            );
            return new GameViewData(
                    "0", "Prim", "/games/prim-icon.png", "3.1.1", prim,
                    "/games/prim-bg.png",
                    0, 7, 0, 54,
                    new GameUpdateData(
                            "https://lgs-games.com/assets/exe/prim-setup-3.1.1.exe",
                            "prim.exe",
                            "unins000.exe",
                            88
                    )
            );
        } else {
            BasicNewsData enigma = new BasicNewsData(
                    "Enigma (remaster)",
                    "/news/news2.png",
                    "Enigma will come back, full remade! New UI, new functionalities, multiplayer...",
                    "https://lgs-games.com/api/news/test.md",
                    Date.from(Instant.now())
            );
            return new GameViewData(
                    "1", "Enigma", "/games/enigma-icon.png", "2.0.0", enigma,
                    "/games/enigma-bg.jpg",
                    0, 24, 3, 4500,
                    null
            );
        }
    }

    @Override
    public ObservableList<BasicGameData> getUserGames(String userID) {
        initGames(userID);
        return games;
    }

    @Override
    public boolean addToLibrary(String userID, BasicGameData game) {
        initGames(userID);
        if (this.games.contains(game)) return true;
        return this.games.add(game);
    }

    @Override
    public boolean removeFromLibrary(String userID, BasicGameData game) {
        initGames(userID);
        if (!this.games.contains(game)) return false;
        return this.games.remove(game);
    }

    @Override
    public ShortGameViewData getGameDateUpdate(String userID, String gameID) {
        APIHelper.fakeDelay(3000);
        if (gameID.equals("1")) {
            return new ShortGameViewData(
                    2, 1, 4600
            );
        } else {
            return new ShortGameViewData(
                    1, 1, 57
            );
        }
    }

    // ------------------------------ UTILS ----------------------------- \\

    private void initGames(String userID) {
        if (this.games != null) return;
        this.games = FXCollections.observableArrayList();
        if (userID.equals("23")) {
            // this.games.add(new BasicGameData(1, "Enigma", "/games/enigma-icon.png"));
            this.games.add(new BasicGameData("0", "Prim", "/games/prim-icon.png"));
        }
    }
}
