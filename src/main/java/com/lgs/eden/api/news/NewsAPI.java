package com.lgs.eden.api.news;

import java.util.ArrayList;

/**
 * News related part of the API
 */
public interface NewsAPI {

    /**
     * Return all news
     * @param begin from an index
     * and up to
     * @param count views.
     *
     * You can choose the lang and must submit a game ID. Result is sorted by the newest
     * first. {@link BasicNewsData#newsCount} is set with the total number of news for this game.
     */
    ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, int gameID);

}
