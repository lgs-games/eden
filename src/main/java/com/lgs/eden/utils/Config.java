package com.lgs.eden.utils;

import com.lgs.eden.api.Api;
import javafx.scene.image.Image;

import java.util.Locale;

/**
 *
 */
public class Config {

    // ------------------------------ CONSTANTS ----------------------------- \\

    private static final String VERSION = "1.0";
    public static final String APP_NAME = "Eden";
    public static final int SCREEN_WIDTH = 700, SCREEN_HEIGHT = 500;
    public static final int SCREEN_WIDTH_APP = 1100, SCREEN_HEIGHT_APP = 700;

    // ------------------------------ CLASS VARIABLES ----------------------------- \\

    private static String stored_username = "Raphik";
    private static Locale locale = setLocale(Language.EN);

    // ------------------------------ GENERAL METHODS ----------------------------- \\

    /**
     * Checks if there is a new version of the application
     * @return true if the client needs an update, false instead
     */
    public static boolean checkClientVersion() {
        System.out.println("Checking client version...");
        return !VERSION.equals(Api.getEdenVersion().version);
    }

    /**
     * Is used to call the app icon
     * @return an image containing the default app icon
     */
    public static Image appIcon() {return Utility.loadImage("/icon64.png");}

    // ------------------------------ LANGUAGE RELATED ----------------------------- \\

    // return locale
    public static Locale getLocale() { return locale; }

    // get Language
    public static Language getLanguage() { return Language.valueOf(locale.getLanguage().toUpperCase()); }

    // set language
    public static Locale setLocale(Language lang) { return locale = new Locale(lang.code); }

    // ------------------------------ USERNAME ----------------------------- \\

    /** may store username or removed stored username **/
    public static void lastUsername(String username, boolean store) {
        stored_username = store ? username : "";
        System.out.println((store?"Storing username: ":"Removing username :") + stored_username);
    }

    /** return stored username **/
    public static String getStored_username() { return stored_username; }

    /**
     * todo:
     * Load language by default from Config file
     * and load stored_username if we got one.
     */
    public static void init() {

    }
}
