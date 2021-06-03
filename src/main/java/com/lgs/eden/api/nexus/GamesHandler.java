package com.lgs.eden.api.nexus;

import com.lgs.eden.api.games.EdenVersionData;
import com.lgs.eden.api.games.GameAPI;

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
        return new EdenVersionData("1.0.0");
    }
}
