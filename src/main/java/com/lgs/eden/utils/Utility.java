package com.lgs.eden.utils;

import com.lgs.eden.Main;
import javafx.scene.image.Image;

import java.util.Objects;

public class Utility {

    public Image appIcon() {
        return new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icon64.png")));
    }

}
