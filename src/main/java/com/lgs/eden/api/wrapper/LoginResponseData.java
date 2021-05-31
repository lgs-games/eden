package com.lgs.eden.api.wrapper;

public class LoginResponseData {

    public final int code;
    public final int userID;

    public LoginResponseData(int code, int userID) {
        this.code = code;
        this.userID = userID;
    }
}
