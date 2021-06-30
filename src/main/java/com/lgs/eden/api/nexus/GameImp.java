package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.games.*;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.nexus.helpers.RequestArray;
import com.lgs.eden.api.nexus.helpers.RequestObject;
import io.socket.client.Socket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Nexus imp of Game
 */
public class GameImp extends ImpSocket implements GameAPI {

    // constructor
    public GameImp(Socket socket) { super(socket); }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    public EdenVersionData getEdenVersion(String code, String os) throws APIException {
        return RequestObject.requestObject(this, o -> new EdenVersionData(
                o.getString("version"),
                o.getString("url"),
                -1
        ), "eden", code, os);
    }

    @Override
    public GameViewData getGameData(String userID, String gameID, String lang, String os) throws APIException {
        return RequestObject.requestObject(this, o -> new GameViewData(
                o.get("game_id")+"",
                o.getString("name"),
                o.getString("icon"),
                o.getString("version"),
                ((NexusHandler)parent).parseNews(o.getJSONObject("last_news_id")),
                o.getString("background"),
                o.getInt("player_achievements"),
                o.getInt("number_of_achievements"),
                o.getInt("friends_playing"),
                o.getInt("time_played"),
                new GameUpdateData(
                        o.getString("new_version_url"),
                        o.getString("game_run"),
                        o.getString("game_uninstall"),
                        -1
                )

        ), "game", gameID, lang, os);
    }

    @Override
    public ObservableList<BasicGameData> getUserGames(String userID) throws APIException {
        return FXCollections.observableArrayList(
                RequestArray.requestArray(this, o -> new BasicGameData(
                        o.getString("game_id"),
                        o.getString("name"),
                        o.getString("icon")
                ), "user-games")
        );
    }

    @Override
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, String userID, String os) throws APIException {
        return RequestArray.requestArray(this, o -> new MarketplaceGameData(
                o.getString("game_id"),
                o.getString("name"),
                o.getString("version"),
                o.getString("size"),
                o.getString("desc"),
                o.getString("icon"),
                o.getString("image"),
                NexusHandler.toArrayList(o.getJSONArray("tags")),
                NexusHandler.toArrayList(o.getJSONArray("languages")),
                o.getBoolean("in_library")
        ), "marketplace", begin, count, code, os);
    }

    @Override
    public boolean addToLibrary(String userID, BasicGameData game) throws APIException {
        return RequestObject.requestObject(this, NexusHandler::isJobDone, "library-add", game.id);
    }

    @Override
    public boolean removeFromLibrary(String userID, BasicGameData game) throws APIException {
        return RequestObject.requestObject(this, NexusHandler::isJobDone, "library-remove", game.id);
    }

    @Override
    public ShortGameViewData getGameDateUpdate(String userID, String gameID) throws APIException {
        return RequestObject.requestObject(this, o -> new ShortGameViewData(
                o.getInt("player_achievements"),
                o.getInt("friends_playing"),
                o.getInt("time_played")
        ), "update-data", gameID);
    }
}
