package com.lgs.eden.api;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * List of all codes returned by the API
 */
public enum APIResponseCode {
    SERVER_UNREACHABLE(0),
    TOO_MANY_REQUESTS(1),
    NOT_AVAILABLE(2),
    INVALID_API_USAGE(5),
    JOB_DONE(7),
    JOB_NOT_DONE(8),

    LOGIN_OK(10),
    LOGIN_INVALID(11),
    LOGIN_NOT_ACTIVATED(12),
    LOGIN_BANNED(13),
    LOGIN_ALREADY(14),

    REGISTER_OK(20),
    REGISTER_FAILED(21),
    REGISTER_FAILED_LOGIN(22),
    REGISTER_FAILED_EMAIL(23),
    REGISTER_FAILED_SIZE(24),

    CHANGE_REPUTATION_OK(272),
    CHANGE_REPUTATION_KO(273),

    NO_NOTIFICATIONS(555),
    FRIEND_REQUEST(556),
    MESSAGE_RECEIVED(557),
    FRIEND_REQUEST_ACCEPTED(558),

    USER_ID_NOT_FOUND(1000),
    UNABLE_TO_OPEN_CONV(1001),
    ;

    public final int code;

    APIResponseCode(int code) {
        this.code = code;
    }

    // ------------------------------ fromCode ----------------------------- \\

    // use a HashMap to make things faster
    private static final HashMap<Integer, APIResponseCode> map;

    // init
    static {
        map = new HashMap<>();
        for (APIResponseCode r : APIResponseCode.values()) {
            map.put(r.code, r);
        }
    }

    /** returns APIResponseCode from code  **/
    public static APIResponseCode fromCode(int code) {
        if (!map.containsKey(code)) throw new NoSuchElementException("No APIResponseCode for code '" + code + "'.");
        return map.get(code);
    }

}
