package com.lgs.eden.api.nexus;

import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.news.NewsAPI;
import com.lgs.eden.utils.config.Language;
import io.socket.client.Socket;

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
    public BasicNewsData getNews(String id) {
        return null;
    }

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, String gameID, Language l) {
        return null;
    }
}
