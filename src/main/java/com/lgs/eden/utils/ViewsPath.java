package com.lgs.eden.utils;

/**
 * This contains the paths to the views, it makes the code cleaner
 */
public enum ViewsPath {
    FRAME("/fxml/frame.fxml", "Frame"),
    LOGIN("/fxml/login.fxml", "Log in"),
    PROFILE("/fxml/profil.fxml", "Profile"),
    REGISTER("/fxml/register.fxml", "Register"),
    SETTINGS("/fxml/settings.fxml", "Settings");

    // view path
    public final String path;
    // view name
    public final String name;

    ViewsPath(String path, String name) {
        this.path = path;
        this.name = name;
    }

    // show name

    @Override
    public String toString() {
        return this.name;
    }

}
