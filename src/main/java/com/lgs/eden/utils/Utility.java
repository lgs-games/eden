package com.lgs.eden.utils;

import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.config.OperatingSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
        return reloadImage(path);
    }

    public static Image reloadImage(String path) {
        try {
            if (path.startsWith("/")) images.put(path, new Image(Objects.requireNonNull(Utility.class.getResourceAsStream(path))));
            else images.put(path, new Image(path));
            return images.get(path);
        } catch (Exception e) {
            throw new IllegalArgumentException("Image not found " + path);
        }
    }

    /**
     * Return file URL path
     */
    @SuppressWarnings("unused")
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
        if (resource == null)
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
                throw new IllegalStateException("Couldn't open link " + link);
            }
        }
    }

    /** returns the user OS **/
    public static OperatingSystem getUserOS() {
        String os = System.getProperty("os.name", "generic").toLowerCase();
        // check for os
        if (os.contains("win"))
            return OperatingSystem.WINDOWS;
        else if (os.contains("nux"))
            return OperatingSystem.LINUX;

        throw new UnsupportedOperationException("OS not supported");
    }

    private static boolean isLink(String source) {
        return source.startsWith("http") || source.startsWith("https");
    }

    public static String getFileAsString(String source) {
        if (isLink(source)) {
            try {
                HttpsURLConnection.setFollowRedirects(false);
                HttpsURLConnection connection = (HttpsURLConnection) new URL(source).openConnection();
                // open
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
                );
                // read
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) buffer.append(line).append("\n");
                // close
                bufferedReader.close();
                return buffer.toString();
            } catch (IOException e) {
                throw new IllegalStateException("Couldn't read remote file");
            }
        } else {
            try {
                URL url = Utility.class.getResource(source);
                if (url == null) throw new IOException();
                return Files.readString(Path.of(url.toURI()), StandardCharsets.UTF_8);
            } catch (URISyntaxException | IOException e) {
                throw new IllegalStateException("Couldn't read remote file");
            }
        }
    }
}
