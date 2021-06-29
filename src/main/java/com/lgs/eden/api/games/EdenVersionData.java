package com.lgs.eden.api.games;

/**
 * Wrapper for Eden version result from the API.
 */
public class EdenVersionData extends GameUpdateData {

    public EdenVersionData(String version, String downloadURL, double size) {
        super(version, downloadURL, null, null, size);
    }

}
