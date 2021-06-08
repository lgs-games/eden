package com.lgs.eden.api.local;

import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.news.NewsAPI;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class NewsHandler implements NewsAPI {

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, int gameID) {
        ArrayList<BasicNewsData> news = new ArrayList<>();
        BasicNewsData n1 = new BasicNewsData(
                "test1", "/news/news2", "This is a test",
                Date.from(Instant.parse("2020-12-03T10:15:30.00Z")),
                1
        );
        BasicNewsData.newsCount = 1;
        news.add(n1);
        return news;
    }

}
