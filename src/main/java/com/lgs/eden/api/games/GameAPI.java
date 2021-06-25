package com.lgs.eden.api.games;

import com.lgs.eden.api.APIException;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Games related part of the API
 */
public interface GameAPI {

    /**
     * Simply check EdenVersionData
     */
    EdenVersionData getEdenVersion() throws APIException;

    /**
     * Ask the API for all the game in the marketplace.
     * Should have a pagination + max per page.
     * Client should deal with the fact that the user may already have some games
     * but maybe we could add an "excluded" tag to the API (taking a list of ids).
     * We should send to the API the lang wanted for the descriptions/tags/...
     *
     * Also to helps us build a cache, we should send a "timestamp". The API should have
     * one indicating when the result was last modified. The API should return something like
     * null if no changes or the usual result that we will need to cache.
     */
    ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, String userID, String os) throws APIException;

    /**
     * Returns information such as the name of the game, the version, ...
     * and information such as the number of friends playing, the achievement-related information, ...
     * Mainly used to render for an user, the information about one game.
     */
    GameViewData getGameData(String userID, String gameID, String lang, String os) throws APIException;

    /**
     * Returns basic data about the game that the user add to his library.
     */
    ObservableList<BasicGameData> getUserGames(String userID) throws APIException;

    /**
     * Add a game to the user library. Return false if could not add
     * this game.
     */
    boolean addToLibrary(String userID, BasicGameData game) throws APIException;

    /**
     * Remove a game to the user library. Return false if could not remove
     * this game.
     */
    boolean removeFromLibrary(String userID, BasicGameData game) throws APIException;

    /**
     * Returns some part of the view that will be updated when update is requested.
     */
    ShortGameViewData getGameDateUpdate(String userID, String gameID);
}