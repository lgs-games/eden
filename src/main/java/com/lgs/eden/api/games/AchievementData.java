package com.lgs.eden.api.games;

import javafx.scene.image.Image;

public class AchievementData {

    /** achievement icon **/
    public final Image achievementIcon;
    /** achievement name **/
    public final String achievementName;
    /** achievement description **/
    public final String description;
    /** true if unlocked **/
    public final boolean unlocked;
    /** true if hidden **/
    public final boolean hidden;


    public AchievementData(Image achievementIcon, String achievementName, String description,
                           boolean unlocked, boolean hidden) {
        this.achievementIcon = achievementIcon;
        this.achievementName = achievementName;
        this.description = description;
        this.unlocked = unlocked;
        this.hidden = hidden;
    }

}
