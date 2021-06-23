package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.games.*;
import io.socket.client.Ack;
import io.socket.client.Socket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Nexus imp of Game
 */
public class GameImp extends ImpSocket implements GameAPI {

    // constructor
    public GameImp(Socket socket) { super(socket); }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    public EdenVersionData getEdenVersion() throws APIException {
        return null;
    }

    @Override
    public GameViewData getGameData(int userID, int gameID) {
        return null;
    }

    @Override
    public ObservableList<BasicGameData> getUserGames(int userID) {
        return FXCollections.observableArrayList();
    }

    @Override
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, int userID) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<ArrayList<MarketplaceGameData>> monitor = MonitorIO.createMonitor(this);
        socket.emit("marketplace", begin, count, (Ack) args -> {
            ArrayList<MarketplaceGameData> rep = null;
            if (args.length > 0 && args[0] instanceof JSONArray) {
                try {
                    JSONArray array = (JSONArray) args[0];
                    rep = new ArrayList<>();
                    // valid
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = (JSONObject) array.get(i);
                        rep.add(new MarketplaceGameData(
                                o.getInt("game_id"),
                                o.getString("name"),
                                o.getString("version"),
                                o.getString("size"),
                                o.getString("desc"),
                                o.getString("icon"),
                                o.getString("image"),
                                NexusHandler.toArrayList(o.getJSONArray("tags")),
                                NexusHandler.toArrayList(o.getJSONArray("languages")),
                                o.getBoolean("in_library")
                        ));
                    }
                } catch (JSONException e) {
                    rep = null;
                }
            }
            monitor.set(rep);
        });

        // ask for response, can raise Exception
        return monitor.response();
    }

    @Override
    public boolean addToLibrary(int userID, BasicGameData game) {
        return false;
    }

    @Override
    public boolean removeFromLibrary(int userID, BasicGameData game) {
        return false;
    }

    @Override
    public ShortGameViewData getGameDateUpdate(int userID, int gameID) {
        return null;
    }
}
