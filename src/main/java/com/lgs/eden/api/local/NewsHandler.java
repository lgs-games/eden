package com.lgs.eden.api.local;

import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.news.NewsAPI;
import com.lgs.eden.utils.config.Language;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * Handler for news api
 */
public class NewsHandler implements NewsAPI {

    @Override
    public BasicNewsData getNews(String id) {
        return null;
    }

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, String gameID, Language language) {
        ArrayList<BasicNewsData> news = new ArrayList<>();
        BasicNewsData prim = new BasicNewsData(
                "Version 3.1.0 released",
                "/news/news1.png",
                "We patched a lot of things and tried to improve" +
                        "the game to make it less easy and more fun to play.",
                "https://lgs-games.com/api/news/test.md",
                Date.from(Instant.now()),
                0
        );
        BasicNewsData.newsCount = 1;
        news.add(prim);
        return news;
    }

}
