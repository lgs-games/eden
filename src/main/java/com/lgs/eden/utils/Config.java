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

    private static final String VERSION = "1.0"; // TODO: change version type to EdenVersion
    private static String USERNAME = "Raphik";
    private static Locale locale = setLocale(Language.EN);

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
    public static Locale setLocale(Language lang) { return new Locale(lang.text); }

}
