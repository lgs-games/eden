package com.lgs.eden.api.helper;

import com.lgs.eden.utils.Language;

/**
 * Methods to factorize some code
 * in the API related methods.
 */
public class APIHelper {

    /**
     * Check if the code is "en" or "fr". If not one of those, return "en". The website
     * is only available in one of those.
     */
    public static String formatCode(String code) {
        // not an allowed code
        if (!code.equals(Language.FR.code) && !code.equals(Language.EN.code))
            code = Language.EN.code; // then "en"
        return code; // return
    }

}
