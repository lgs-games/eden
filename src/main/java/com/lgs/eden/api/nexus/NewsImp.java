package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.news.NewsAPI;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.nexus.helpers.MonitorIO;
import io.socket.client.Ack;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Nexus imp of News
 */
public class NewsImp extends ImpSocket implements NewsAPI {

    public NewsImp(Socket socket) {
        super(socket);
    }

    // ------------------------------ METHODS ----------------------------- \\


    @Override
    public BasicNewsData getNews(String newsID) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<BasicNewsData> monitor = MonitorIO.createMonitor(this);
        socket.emit("news", newsID, (Ack) args -> {
            BasicNewsData rep = null;

            if (args.length > 0 && args[0] instanceof JSONObject) {
                try {
                    JSONObject o = (JSONObject) args[0];
                    rep = parseNews(o);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    rep = null;
                }
            }

            monitor.set(rep);
        });

        // ask for response, can raise Exception
        return monitor.response();
    }

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String gameID, String lang, String os) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<ArrayList<BasicNewsData>> monitor = MonitorIO.createMonitor(this);
        socket.emit("all-news", begin, count, gameID, lang, os, (Ack) args -> {
            ArrayList<BasicNewsData> rep = null;

            if (args.length > 0 && args[0] instanceof JSONObject) {
                try {
                    rep = new ArrayList<>();

                    JSONObject pack = (JSONObject) args[0];
                    JSONArray a = pack.getJSONArray("entries");
                    for (int i = 0; i < a.length(); i++) {
                        JSONObject o = (JSONObject) a.get(i);
                        rep.add(parseNews(o));
                    }
                    BasicNewsData.newsCount = pack.getInt("total");
                } catch (JSONException | ParseException e){
                    rep = null;
                }
            }

            monitor.set(rep);
        });

        // ask for response, can raise Exception
        return monitor.response();
    }

    public BasicNewsData parseNews(JSONObject o) throws JSONException, ParseException {
        return new BasicNewsData(
                o.getString("title"),
                o.getString("image"),
                o.getString("catch-phrase"),
                o.getString("link"),
                new SimpleDateFormat("yyyy-MM-dd").parse(o.getString("date"))
        );
    }
}
