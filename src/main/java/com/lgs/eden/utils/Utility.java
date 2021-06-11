package com.lgs.eden.utils;

import com.lgs.eden.application.ApplicationCloseHandler;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.config.OperatingSystem;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *
 */
public final class Utility {

    private static final HashMap<String, Image> images = new HashMap<>();

    /**
     * Loads images from the path given
     * @param path path of the needed image
     * @return the Image asked
     * @throws NullPointerException if the path is not reachable
     */
    public static Image loadImage(String path) throws NullPointerException {
        if (images.containsKey(path)) return images.get(path);
        try {
            images.put(path, new Image(Objects.requireNonNull(Utility.class.getResourceAsStream(path))));
            return images.get(path);
        } catch (Exception e) {
            throw new IllegalArgumentException("Image not found "+path);
        }
    }

    /**
     * Return file URL path
     */
    public static String getURL(String path) {
        return Objects.requireNonNull(Utility.class.getResource(path)).getPath();
    }

    /**
     * A method made to load the views with only the path
     * @param path path to the view needed
     * @return an FXMLLoader containing the view
     * @throws IllegalStateException if the path is not reachable
     */
    public static FXMLLoader loadView(String path) throws IllegalStateException {
        // get resource
        URL resource = Utility.class.getResource(path);
        if(resource == null)
            throw new IllegalStateException();

        // get locale
        Locale locale = Config.getLocale();

        // loader fxml
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setResources(ResourceBundle.getBundle("i18n", locale));
        return loader;
    }

    /**
     * Same as load view but return the FXML component
     * @see #loadView
     */
    public static Parent loadViewPane(String path) throws IllegalStateException {
        return Utility.loadViewPane(Utility.loadView(path));
    }

    /**
     * Same as load view, but take a loader and return the FXML component
     * @see #loadView
     */
    public static Parent loadViewPane(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException | IllegalStateException e) {
            throw new IllegalStateException(e);
        }
    }

    /** All path will use the Unix style */
    public static String formatPath(String gameFolder) {
        return gameFolder.replace("\\", "/");
    }

    /** return current directory **/
    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }

    /** open a link in browser or throws an exception **/
    public static void openInBrowser(String link) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (URISyntaxException | IOException ignoreMeTooBlink) {
                throw new IllegalStateException("Couldn't open link "+link);
            }
        }
    }

    /** returns the user OS **/
    public static OperatingSystem getOS() {
        // todo: code this
        return OperatingSystem.WINDOWS;
    }

    /** install eden, close program **/
    public static void installEden(String installer) {
        OperatingSystem os = getOS();
        if (os.equals(OperatingSystem.WINDOWS)){
            try {
                // and close
                ApplicationCloseHandler.close(false);

                // start exe
                ProcessBuilder process = new ProcessBuilder(installer, "/VERYSILENT", "/MERGETASKS=\"desktopicon,postinstall\"");
                process.directory(new File(new File(installer).getParent()));
                process.start();

                // kill program
                System.exit(0);
            } catch (IOException ex) {
                System.out.println("Unable to install new version.");
                // PopupUtils.showPopup(Translate.getTranslation("Unable to install new version."));
            }
        } else {
            throw new UnsupportedOperationException("not yet");
        }
    }
}
