package com.lgs.eden.api.news;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

import java.util.Date;

/**
 * Wrapper for a news
 */
public class BasicNewsData {

    public static int newsCount; // total number of news

    public final String title;
    public final Image image;
    public final String desc;
    public final String link;
    public final Date date;
    public final int newsID;

    public BasicNewsData(String title, String image, String desc, String link, Date date, int newsID) {
        this.title = title;
        this.image = Utility.loadImage(image);
        this.desc = desc;
        this.link = link;
        this.date = date;
        this.newsID = newsID;
    }

}
