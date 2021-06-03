package com.lgs.eden.api.games;

/**
 * Class when API return some informations
 * like the version of the game and
 * how we can download the new version (links).
 */
public class GameUpdateData {

    public final String version;

    public GameUpdateData(String version) {
        this.version = version;
    }
}
