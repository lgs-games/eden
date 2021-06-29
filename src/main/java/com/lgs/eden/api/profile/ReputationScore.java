package com.lgs.eden.api.profile;

/**
 * Reputation score made by an user
 * to another user.
 */
public enum ReputationScore {
    NONE, INCREASED, DECREASED;

    public static ReputationScore parse(int value) {
        if (value == 1) return INCREASED;
        if (value == -1) return DECREASED;
        if (value == 0) return NONE;
        throw new IllegalArgumentException("Invalid value");
    }
}
