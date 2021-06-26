package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.news.NewsAPI;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.nexus.helpers.RequestObject;
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
        return RequestObject.requestObject(this, this::parseNews, "news", newsID);
    }

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String gameID, String lang, String os) throws APIException {
        return RequestObject.requestObject(this, pack -> {
            ArrayList<BasicNewsData> rep = new ArrayList<>();
            JSONArray a = pack.getJSONArray("entries");
            for (int i = 0; i < a.length(); i++) {
                JSONObject o = (JSONObject) a.get(i);
                rep.add(parseNews(o));
            }
            BasicNewsData.newsCount = pack.getInt("total");
            return rep;
        }, "all-news", begin, count, gameID, lang, os);
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
