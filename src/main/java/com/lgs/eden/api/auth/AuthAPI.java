package com.lgs.eden.api.auth;

import com.lgs.eden.api.APIResponseCode;

/**
 * Login related part of the API
 */
public interface AuthAPI {

    /**
     *
     */
    LoginResponseData login(String username, String pwd);

    /**
     *
     */
    void logout(int currentUserID);

    /**
     *
     */
    APIResponseCode register(String username, String pwd, String email);

    /**
     * Returns the link to the password forget page
     * @param languageCode "en" or "fr", ... We will try to return
     *                     a page in this language.
     */
    String getPasswordForgotLink(String languageCode);

}
