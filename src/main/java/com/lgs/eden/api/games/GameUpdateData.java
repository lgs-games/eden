package com.lgs.eden.api.games;

import com.lgs.eden.utils.config.OperatingSystem;

/**
 * Class when API return some informations
 * like the version of the game and
 * how we can download the new version (links).
 */
public class GameUpdateData {

    private final String downloadURL;
    public final String version;
    public final double size;

    public GameUpdateData(String version, String downloadURL, double size) {
        this.version = version;
        this.downloadURL = downloadURL;
        this.size = size;
    }

    public String getURL(OperatingSystem os) {
        // todo: should return the right URL according to the OS
        return downloadURL;
    }
}
