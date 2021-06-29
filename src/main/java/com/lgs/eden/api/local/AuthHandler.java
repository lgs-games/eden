package com.lgs.eden.api.local;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.auth.AuthAPI;
import com.lgs.eden.api.auth.LoginResponseData;

/**
 * Implementation of LoginAPI
 */
class AuthHandler implements AuthAPI {

    @Override
    public LoginResponseData login(String username, String pwd) {
        if (username.equals("Raphiki")) {
            // not activated
            return new LoginResponseData(12);
        }

        if (username.equals("Raphikis")) {
            // banned
            return new LoginResponseData(13);
        }

        if (username.equals("Raphik") && pwd.equals("tester")) {
            // okay
            return new LoginResponseData(10, "23", username, "/avatars/23.png");
        }

        if (username.equals("Raphik2") && pwd.equals("tester")) {
            // okay
            return new LoginResponseData(10, "24", username, "/avatars/24.png");
        }

        // invalid
        return new LoginResponseData(11);
    }

    @Override
    public void logout(String userID) {
    }

    @Override
    public APIResponseCode register(String username, String pwd, String email) {
        if (username.equals("admin") && pwd.equals("azerty") && email.equals("a@b.c")) {
            return APIResponseCode.REGISTER_OK;
        }

        if (username.equals("Raphik")) {
            return APIResponseCode.REGISTER_FAILED_LOGIN;
        }

        if (email.equals("a@b.d")) {
            return APIResponseCode.REGISTER_FAILED_EMAIL;
        }

        return APIResponseCode.REGISTER_FAILED;
    }

}
