package com.lgs.eden.api.nexus;

import com.lgs.eden.api.API;
import com.lgs.eden.api.auth.AuthAPI;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.helper.APIHelper;

/**
 * Implementation of LoginAPI
 */
class AuthHandler implements AuthAPI {

    @Override
    public LoginResponseData login(String username, String pwd) {
        if (!username.equals("Raphik") || !pwd.equals("tester")) {
            return new LoginResponseData(-1);
        }
        return new LoginResponseData(0, 23, username, "/avatars/23.png");
    }

    @Override
    public void logout() {
    }

    @Override
    public int register(String username, String pwd, String email) {
        if (!username.equals("admin") || !pwd.equals("azerty") || !email.equals("a@b.c")) {
            return -1;
        }
        return 0;
    }

    @Override
    public String getPasswordForgotLink(String languageCode) {
        // website only supports en or fr
        languageCode = APIHelper.formatCode(languageCode);
        return API.WEBSITE_URL+languageCode+"/password_forgot";
    }

}
