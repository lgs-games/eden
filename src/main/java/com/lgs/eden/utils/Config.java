package com.lgs.eden.utils;

import com.lgs.eden.Main;
import com.lgs.eden.api.Api;
import javafx.scene.image.Image;

import java.util.Locale;
import java.util.Objects;

/**
 *
 */
public class Config {

    // ------------------------------ CONSTANTS ----------------------------- \\

    private static final String VERSION = "1.0";
    public static final String APP_NAME = "Eden";
    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 700;

    // ------------------------------ CLASS VARIABLES ----------------------------- \\

    private static String username = "Raphik";
    private static Locale locale = setLocale(Language.EN);

    // ------------------------------ GENERAL METHODS ----------------------------- \\

    /**
     * Checks if there is a new version of the application
     * @return true if the client needs an update, false instead
     */
    public static boolean checkClientVersion() {
        System.out.println("Checking client version...");
        return !VERSION.equals(Api.getApiVersion());
    }

    /**
     * Is used to call the app icon
     * @return an image containing the default app icon
     */
    public static Image appIcon() {return new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icon64.png")));}

    // ------------------------------ LANGUAGE RELATED ----------------------------- \\

    // return locale
    public static Locale getLocale() { return locale; }

    // set language
    public static Locale setLocale(Language lang) { return locale = new Locale(lang.text); }

}
