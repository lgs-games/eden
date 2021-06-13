package com.lgs.eden.api.profile;

import javafx.scene.image.Image;

/**
 * Wrapper for recent games data
 */
public final class RecentGameData {

    public static final int PLAYING = -1;

    /** game name **/
    public final Image gameIcon;
    /** game name **/
    public final String gameName;
    /** time played, in hours **/
    public final int timePlayed;
    /** last played, in days **/
    public final int lastPlayed;

    public RecentGameData(Image gameIcon, String gameName, int timePlayed, int lastPlayed) {
        this.gameIcon = gameIcon;
        this.gameName = gameName;
        this.timePlayed = timePlayed;
        this.lastPlayed = lastPlayed;
    }

    public boolean isPlaying() {
        return lastPlayed == PLAYING;
    }
}
