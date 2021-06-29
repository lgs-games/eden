package com.lgs.eden.api.profile.friends;

/**
 * Status of the relation between
 * two users.
 */
public enum FriendShipStatus {
    REQUESTED(0),
    GOT_REQUESTED(1),
    FRIENDS(2),
    NONE(3),
    USER(4) // if user request with himself
    ;

    private final int value;
    FriendShipStatus(int value) { this.value = value; }

    public static FriendShipStatus parse(int value) {
        for (FriendShipStatus s: values()) {
            if (s.value == value) return s;
        }
        throw new IllegalArgumentException("no such value");
    }
}
