package com.lgs.eden.api.profile;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

/**
 * Wrapper for recent games data
 */
public final class RecentGameData {

    public static final int PLAYING = -1;

    /** game icon **/
    public final Image gameIcon;
    /** game name **/
    public final String gameName;
    /** time played, in hours **/
    public final int timePlayed;
    /** last played, in days **/
    public final long lastPlayed;

    public RecentGameData(String gameIcon, String gameName, int timePlayed, long lastPlayed) {
        this.gameIcon = gameIcon == null ? null : Utility.loadImage(gameIcon);
        this.gameName = gameName;
        this.timePlayed = timePlayed;
        this.lastPlayed = lastPlayed;
    }

    public boolean isPlaying() {
        return lastPlayed == PLAYING;
    }
}
