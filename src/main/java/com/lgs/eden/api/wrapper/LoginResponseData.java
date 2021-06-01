package com.lgs.eden.api.wrapper;

/**
 * Wrapper for API Login Response
 */
public class LoginResponseData {

    /** result code, check todo: APIResponseCode **/
    public final int code;
    /** logged ID or -1 if not **/
    public final int userID;

    public LoginResponseData(int code, int userID) {
        this.code = code;
        this.userID = userID;
    }
}
