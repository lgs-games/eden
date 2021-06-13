package com.lgs.eden.api.games;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

public class AchievementData {

    /** achievement icon **/
    public final Image icon;
    /** achievement name **/
    public final String name;
    /** achievement description **/
    public final String description;
    /** true if unlocked **/
    public final boolean unlocked;

    public AchievementData(String icon, String name, String description, boolean unlocked) {
        this.icon = icon == null ? null : Utility.loadImage(icon);
        this.name = name;
        this.description = description;
        this.unlocked = unlocked;
    }

}
