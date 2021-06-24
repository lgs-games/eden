package com.lgs.eden.api.nexus;

import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.news.NewsAPI;
import com.lgs.eden.utils.config.Language;
import io.socket.client.Socket;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * Nexus imp of News
 */
public class NewsImp extends ImpSocket implements NewsAPI {

    public NewsImp(Socket socket) {
        super(socket);
    }

    // ------------------------------ METHODS ----------------------------- \\


    @Override
    public BasicNewsData getNews(String id) {
        BasicNewsData prim = new BasicNewsData(
                "Version 3.1.0 released",
                "/news/news1.png",
                "We patched a lot of things and tried to improve" +
                        "the game to make it less easy and more fun to play.",
                "https://lgs-games.com/api/news/test.md",
                Date.from(Instant.now()),
                0
        );
        return prim;
    }

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, String gameID, Language l) {
        return null;
    }
}
