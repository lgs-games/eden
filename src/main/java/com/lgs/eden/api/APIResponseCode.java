package com.lgs.eden.api;

import java.util.NoSuchElementException;

/**
 * List of all codes returned by the API
 */
public enum APIResponseCode {
    CONNECTION_FAILED(0),
    TOO_MANY_REQUESTS(1),

    LOGIN_OK(10),
    LOGIN_INVALID(11),
    LOGIN_NOT_ACTIVATED(12),
    LOGIN_BANNED(13),

    REGISTER_OK(20),
    REGISTER_FAILED(21),
    REGISTER_FAILED_EMAIL(22),
    REGISTER_FAILED_LOGIN(23),
    REGISTER_FAILED_SIZE(24),

    CHANGE_REPUTATION_OK(272),
    CHANGE_REPUTATION_KO(273),

    NO_NOTIFICATIONS(555),
    FRIEND_REQUEST(556),
    MESSAGE_RECEIVED(557),
    ;

    public final int code;

    APIResponseCode(int code) {
        this.code = code;
    }

    /** returns APIResponseCode from code  **/
    public static APIResponseCode fromCode(int code) {
        for (APIResponseCode r : APIResponseCode.values()) {
            if (r.code == code) return r;
        }
        throw new NoSuchElementException("No APIResponseCode for code '"+code+"'.");
    }

}
