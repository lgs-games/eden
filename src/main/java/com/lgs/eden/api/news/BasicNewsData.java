package com.lgs.eden.api.news;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

/**
 * Wrapper for a news
 */
public class BasicNewsData {

    public final String title;
    public final Image image;
    public final String desc;
    public final int newsID;

    public BasicNewsData(String title, String image, String desc, int newsID) {
        this.title = title;
        this.image = Utility.loadImage(image);
        this.desc = desc;
        this.newsID = newsID;
    }
}
