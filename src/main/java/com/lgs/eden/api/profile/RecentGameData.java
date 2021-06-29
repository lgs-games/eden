package com.lgs.eden.api.profile;

import javafx.scene.image.Image;

/**
 * Wrapper for recent games data
 */
public record RecentGameData(Image gameIcon, String gameName, int timePlayed,
                             int lastPlayed) {

    public static final int PLAYING = -1;

    public boolean isPlaying() {
        return lastPlayed == PLAYING;
    }
}
