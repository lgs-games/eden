package com.lgs.eden.api.games;

import com.lgs.eden.utils.config.OperatingSystem;

/**
 * Class when API return some informations
 * like the version of the game and
 * how we can download the new version (links).
 */
public class GameUpdateData {

    private final String downloadURL;
    private final String runnable;
    private final String uninstall;

    public final String version;
    public final double size;

    public GameUpdateData(String version, String downloadURL, String runnable, String uninstall, double size) {
        this.version = version;
        this.downloadURL = downloadURL;
        this.runnable = runnable;
        this.uninstall = uninstall;
        this.size = size;
    }

    // todo: should return the right ... according to the OS

    public String getURL(OperatingSystem os) {
        return downloadURL;
    }

    public String getRunnable(OperatingSystem os) {
        return runnable;
    }

    public String getUninstall(OperatingSystem os) {
        return uninstall;
    }
}
