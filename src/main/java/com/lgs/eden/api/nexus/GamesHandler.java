package com.lgs.eden.api.nexus;

import com.lgs.eden.api.games.EdenVersionData;
import com.lgs.eden.api.games.GameAPI;
import com.lgs.eden.api.games.GameUpdateData;
import com.lgs.eden.api.games.MarketplaceGameData;

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
    public ArrayList<MarketplaceGameData> getMarketPlaceGames() {
        ArrayList<MarketplaceGameData> games = new ArrayList<>();

        ArrayList<String> tags = new ArrayList<>();
        ArrayList<String> languages = new ArrayList<>();
        GameUpdateData updatePrim = new GameUpdateData(
          "3.1.0",
                88
        );

        MarketplaceGameData prim = new MarketplaceGameData(
                0, "Prim",
                "Prim is a production line game request by Dimitri Watel, teacher at ENSIIE engineering school." +
                        " The main idea is that you will produce resources in order to close a gate. The folklore of " +
                        "the game is the ENSIIE so you will have a lot of ENSIIE-related content. Have fun !",
                "/games/prim-pic.png",
                "/games/prim-icon.png",
                tags,
                languages,
                updatePrim
        );

        games.add(prim);

        return games;
    }
}
