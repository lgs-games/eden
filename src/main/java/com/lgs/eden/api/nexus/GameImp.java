package com.lgs.eden.api.nexus;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.games.*;
import io.socket.client.Ack;
import io.socket.client.Socket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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
        // check a bit more times
        int cumule = 0;
        while (cumule < 1000){
            try {
                Thread.sleep(100);
                cumule += 100;
            } catch (InterruptedException ignored){}

            try {
                // no connection
                NexusHandler.checkNetwork(this);
                break;
            } catch (APIException ignore){}
        }

        MonitorIO<EdenVersionData> monitor = MonitorIO.createMonitor(this);

        socket.emit("eden", code, os, (Ack) args -> {
            EdenVersionData rep = null;

            if (args.length > 0 && args[0] instanceof JSONObject) {
                try {
                    JSONObject o = (JSONObject) args[0];
                    rep = new EdenVersionData(
                            o.getString("version"),
                            o.getString("url"),
                            -1
                    );

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
    public GameViewData getGameData(String userID, String gameID, String lang, String os) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<GameViewData> monitor = MonitorIO.createMonitor(this);
        socket.emit("game", gameID, lang, os, (Ack) args -> {
            GameViewData rep = null;

            if (args.length > 0 && args[0] instanceof JSONObject) {
                try {
                    JSONObject o = (JSONObject) args[0];
                    rep = new GameViewData(
                            o.getString("game_id"),
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
                                    o.getString("latest"),
                                    o.getString("new_version_url"),
                                    o.getString("game_run"),
                                    o.getString("game_uninstall"),
                                    -1
                            )

                    );
                } catch (JSONException | ParseException e) {
                    rep = null;
                }
            }

            monitor.set(rep);
        });

        // ask for response, can raise Exception
        return monitor.response();
    }

    @Override
    public ObservableList<BasicGameData> getUserGames(String userID) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<ObservableList<BasicGameData>> monitor = MonitorIO.createMonitor(this);
        socket.emit("user-games", (Ack) args -> {
            ObservableList<BasicGameData> rep = null;

            if (args.length > 0 && args[0] instanceof JSONArray) {
                try {
                    JSONArray a = (JSONArray) args[0];
                    rep = FXCollections.observableArrayList();
                    for (int i = 0; i < a.length(); i++) {
                        JSONObject o = (JSONObject) a.get(i);
                        rep.add(
                              new BasicGameData(
                                      o.getString("game_id"),
                                      o.getString("name"),
                                      o.getString("icon")
                              )
                        );
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
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, String userID, String os) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<ArrayList<MarketplaceGameData>> monitor = MonitorIO.createMonitor(this);
        socket.emit("marketplace", begin, count, code, os, (Ack) args -> {
            ArrayList<MarketplaceGameData> rep = null;
            if (args.length > 0 && args[0] instanceof JSONArray) {
                try {
                    JSONArray array = (JSONArray) args[0];
                    rep = new ArrayList<>();
                    // valid
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = (JSONObject) array.get(i);
                        rep.add(new MarketplaceGameData(
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
    public boolean addToLibrary(String userID, BasicGameData game) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<Boolean> monitor = MonitorIO.createMonitor(this);
        socket.emit("library-add", (Ack) args -> monitor.set(NexusHandler.isJobDone(args)));

        // ask for response, can raise Exception
        return monitor.response();
    }

    @Override
    public boolean removeFromLibrary(String userID, BasicGameData game) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<Boolean> monitor = MonitorIO.createMonitor(this);
        socket.emit("library-remove", (Ack) args -> monitor.set(NexusHandler.isJobDone(args)));

        // ask for response, can raise Exception
        return monitor.response();
    }

    @Override
    public ShortGameViewData getGameDateUpdate(String userID, String gameID) {
        return null;
    }
}
