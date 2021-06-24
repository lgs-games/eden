package com.lgs.eden.api.auth;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;

/**
 * Login related part of the API
 */
public interface AuthAPI {

    /**
     * Process login
     * @see APIResponseCode#LOGIN_OK
     * @see APIResponseCode#LOGIN_BANNED
     * @see APIResponseCode#LOGIN_INVALID
     * @see APIResponseCode#LOGIN_NOT_ACTIVATED
     */
    LoginResponseData login(String username, String pwd) throws APIException;

    /**
     * Process logout
     */
    void logout(String userID) throws APIException;

    /**
     * Process register
     * @see APIResponseCode#REGISTER_OK
     * @see APIResponseCode#REGISTER_FAILED
     * @see APIResponseCode#REGISTER_FAILED_LOGIN
     * @see APIResponseCode#REGISTER_FAILED_SIZE
     * @see APIResponseCode#REGISTER_FAILED_EMAIL
     */
    APIResponseCode register(String username, String pwd, String email) throws APIException;

    /**
     * Returns the link to the password forget page
     *
     * Implemented by the APIHandler so that's not required
     * by the API since the handler may never call it.
     *
     * @param languageCode "en" or "fr", ... We will try to return
     *                     a page in this language.
     */
    default String getPasswordForgotLink(String languageCode) {
        throw new UnsupportedOperationException("not implemented");
    }

}
