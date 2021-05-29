package com.lgs.eden.utils;

import java.util.ResourceBundle;

/**
 * Handle translations-related utilities
 */
public final class Translate {

    /** return a translation from a key, don't start with a % like in JavaFX **/
    public static String getTranslation(String key){
        try {
            ResourceBundle i18n = ResourceBundle.getBundle("i18n", Config.getLocale());
            return i18n.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

}
