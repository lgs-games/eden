package com.lgs.eden.utils;

import com.lgs.eden.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;


public class Utility {

    /**
     * Is used to call the app icon
     * @return an image containing the default app icon
     */
    public Image appIcon() {return new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icon64.png")));}


    /**
     * A method made to load the views with only the path
     * @param path path to the view needed
     * @return an FXMLLoader containing the view
     * @throws IllegalStateException if the path is not reachable
     */
    public FXMLLoader loadView(String path) throws IllegalStateException {

        URL resource = Main.class.getResource(path);
        if(resource == null)
            throw new IllegalStateException();

        return new FXMLLoader(resource);
    }

}
