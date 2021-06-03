package com.lgs.eden.api.games;

import java.util.ArrayList;

/**
 * Games related part of the API
 */
public interface GameAPI {

    /**
     * Simply check EdenVersionData
     */
    EdenVersionData getEdenVersion();

    /**
     * Ask the API for all the game in the marketplace.
     * todo: Should have a pagination + max per page.
     * Client should deal with the fact that the user may already have some games
     * but maybe we could add an "excluded" tag to the API (taking a list of ids).
     * We should send to the API the lang wanted for the descriptions/tags/...
     *
     * Also to helps us build a cache, we should send a "timestamp". The API should have
     * one indicating when the result was last modified. The API should return something like
     * null if no changes or the usual result that we will need to cache.
     */
    ArrayList<MarketplaceGameData> getMarketPlaceGames();

}