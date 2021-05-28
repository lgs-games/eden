package com.lgs.eden.utils;

import com.lgs.eden.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;


public class Utility {


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

        URL resource = Utility.class.getResource(path);
        if(resource == null)
            throw new IllegalStateException();

        return new FXMLLoader(resource);
    }


}
