package com.lgs.eden.api.games;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Wrapper for marketplace game
 */
public class MarketplaceGameData extends BasicGameData {
    // store here the number of games in the marketplace, indicated by the API in the response
    public static int gameCount;

    public final String desc;
    public final Image image;
    public final ArrayList<String> tags;
    public final ArrayList<String> languages;
    public final GameUpdateData updateData;

    public final boolean inLibrary;

    public MarketplaceGameData(int id, String name, String desc, String icon, String image, ArrayList<String> tags,
                               ArrayList<String> languages, GameUpdateData updateData, boolean inLibrary) {
        super(id, name, icon);
        this.desc = desc;
        this.image = Utility.loadImage(image);
        this.tags = tags;
        this.languages = languages;
        this.updateData = updateData;
        this.inLibrary = inLibrary;
    }
}
