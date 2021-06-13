package com.lgs.eden.api.games;

import com.lgs.eden.utils.config.OperatingSystem;

/**
 * Wrapper for Eden version result from the API.
 */
public class EdenVersionData extends GameUpdateData {

    public EdenVersionData(String version, String downloadURL, double size) {
        super(version, downloadURL, null, null, size);
    }

    @Override
    public String getRunnable(OperatingSystem os) {
        throw new UnsupportedOperationException("not a normal game");
    }

    @Override
    public String getUninstall(OperatingSystem os) {
        throw new UnsupportedOperationException("not a normal game");
    }

}
