package com.lgs.eden.api.games;

/**
 * Class when API return some informations
 * like the version of the game and
 * how we can download the new version (links).
 */
public class GameUpdateData {

    public final String downloadURL;
    public final String runnable;
    public final String uninstall;

    public final String version;
    public final double size;

    public GameUpdateData(String version, String downloadURL, String runnable, String uninstall, double size) {
        this.version = version;
        this.downloadURL = downloadURL;
        this.runnable = runnable;
        this.uninstall = uninstall;
        this.size = size;
    }

}
