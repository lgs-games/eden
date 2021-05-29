package com.lgs.eden.utils;

import com.lgs.eden.application.WindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *
 */
public final class Utility {

    /**
     * Loads images from the path given
     * @param path path of the needed image
     * @return the Image asked
     * @throws NullPointerException if the path is not reachable
     */
    public static Image loadImage(String path) throws NullPointerException {
        return new Image(Objects.requireNonNull(Utility.class.getResourceAsStream(path)));
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
}
