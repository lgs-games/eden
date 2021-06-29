package com.lgs.eden.utils.config;

/**
 * All available OS
 */
public enum OperatingSystem {
    WINDOWS, LINUX;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
