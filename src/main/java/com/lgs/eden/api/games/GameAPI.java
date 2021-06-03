package com.lgs.eden.api.games;

import java.util.ArrayList;

/**
 * Games related part of the API
 */
public interface GameAPI {

    EdenVersionData getEdenVersion();

    ArrayList<MarketplaceGameData> getMarketPlaceGames();

}