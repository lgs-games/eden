package com.lgs.eden.api.news;

import com.lgs.eden.api.APIException;
import com.lgs.eden.utils.config.Language;

import java.util.ArrayList;

/**
 * News related part of the API
 */
public interface NewsAPI {

    /**
     * Returns a news by ID
     */
    BasicNewsData getNews(String newsID) throws APIException;

    /**
     * Return all news, begin from an index, and up to count views.
     * You can choose the lang and must submit a game ID. Result is sorted by the newest
     * first. {@link BasicNewsData#newsCount} is set with the total number of news for this game.
     */
    ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, String gameID, Language l);

}
