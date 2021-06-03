package com.lgs.eden.api.games;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Wrapper for marketplace game
 */
public class MarketplaceGameData {

    public final int id;
    public final String name;
    public final String desc;
    public final Image icon;
    public final Image image;
    public final ArrayList<String> tags;
    public final ArrayList<String> languages;
    public final GameUpdateData updateData;

    public MarketplaceGameData(int id, String name, String desc, Image icon, Image image, ArrayList<String> tags,
                               ArrayList<String> languages, GameUpdateData updateData) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        this.image = image;
        this.tags = tags;
        this.languages = languages;
        this.updateData = updateData;
    }
}
