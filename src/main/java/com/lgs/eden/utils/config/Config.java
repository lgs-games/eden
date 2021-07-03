package com.lgs.eden.utils.config;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * Locale configuration handler.
 */
public class Config {

    // ------------------------------ CONSTANTS ----------------------------- \\

    public static final String VERSION = "1.0.0";
    public static final String APP_NAME = "Eden";
    public static final int SCREEN_WIDTH = 700, SCREEN_HEIGHT = 500;
    public static final int SCREEN_WIDTH_APP = 1100, SCREEN_HEIGHT_APP = 700;

    // ------------------------------ CLASS VARIABLES ----------------------------- \\

    private static final Preferences preferences = Preferences.userNodeForPackage(Config.class);
    private static String gameFolder;
    private static String storedUsername;
    private static Locale locale;

    // ------------------------------ GENERAL METHODS ----------------------------- \\

    /**
     * Is used to call the app icon
     * @return an image containing the default app icon
     */
    public static Image appIcon() {return Utility.loadImage("/icon64.png");}

    public static String getOS() { return Utility.getUserOS().toString(); }

    // ------------------------------ LANGUAGE RELATED ----------------------------- \\

    // return locale
    public static Locale getLocale() { return locale; }

    // get Language
    public static Language getLanguage() { return Language.valueOf(locale.getLanguage().toUpperCase()); }

    // set language
    public static void setLocale(Language lang) { locale = new Locale(lang.code); }

    // shortcut for currentLanguage.code
    public static String getCode() { return getLanguage().code; }

    // ------------------------------ USERNAME ----------------------------- \\

    /** may store username or removed stored username **/
    public static void lastUsername(String username, boolean store) {
        storedUsername = store ? username : "";
        System.out.println((store ? "Storing username: " : "Removing username :") + storedUsername);
        // do
        if (store) preferences.put("username", username);
        else preferences.put("username", "");
    }

    /** return stored username **/
    public static String getStoredUsername() { return storedUsername; }

    // ------------------------------ GAME FOLDER ----------------------------- \\

    public static String getGameFolder() { return gameFolder; }

    public static void setGameFolder(String newFolder) {
        gameFolder = newFolder;
        // do
        preferences.put("game_folder", newFolder);
    }

    private static String getDefaultGameFolder() { return Utility.getCurrentDirectory()+"/games/"; }

    // ------------------------------ LOAD CONFIG ----------------------------- \\

    /**
     * Load language by default from Config file
     * and load stored_username if we got one.
     */
    public static void init() {
        storedUsername = preferences.get("username", "");
        setLocale(Language.EN);
        gameFolder = preferences.get("game_folder", getDefaultGameFolder());

        // ensure that gameFolder is a valid folder
        File f = new File(gameFolder);
        // the game folder don't exists
        if (!f.exists()) { // we use default one
            gameFolder = Config.getDefaultGameFolder();
            f = new File(gameFolder); // no default one
            if (!f.exists() || !f.isDirectory()) {
                boolean mkdir = f.mkdir(); // we create it
                if (!mkdir) { // can't create, use current directory
                    gameFolder = Utility.getCurrentDirectory();
                }
            }
        }
    }

    // ------------------------------ INSTALL / DOWNLOAD ----------------------------- \\

    private static String downloadRepository = null;

    /**
     * Return download repository path
     */
    public static String getDownloadRepository() {
        if (downloadRepository == null) {
            try {
                downloadRepository = Files.createTempDirectory("eden").toFile().getAbsolutePath();
            } catch (IOException e) {
                System.exit(0);
            }
        }
        return downloadRepository;
    }

    public static boolean isGameInstalled(String gameID, String runnable) {
        String gameFolder = getGameFolder() + gameID + "/" + runnable;
        File folder = new File(gameFolder);
        return folder.exists() && folder.isDirectory();
    }

    public static String getGameVersion(String gameID) {
        String versionFile = getGameFolder() + gameID + "/game.properties";
        File file = new File(versionFile);
        if (file.exists() && file.isFile()) {
            try {
                Properties p = new Properties();
                p.load(new FileReader(file));
                return p.getProperty("version", null);
            } catch (IOException e) {
                System.err.println("could not read version");
                return null;
            }
        } else {
            return null;
        }
    }
}
