package com.lgs.eden.utils;

/**
 * This contains the paths to the views, it makes the code cleaner
 */
public enum ViewsPath {
    FRAME("/fxml/frame.fxml"),
    FRAME_MAIN("/fxml/frame_main.fxml"),
    LOGIN("/fxml/login.fxml"),
    PROFILE("/fxml/profile.fxml"),
    REGISTER("/fxml/register.fxml"),
    SETTINGS("/fxml/settings.fxml"),
    FRIEND_CELL("/fxml/profile/friendcell.fxml"),
    PROFILE_CARD("/fxml/profile/card.fxml");

    // view path
    public final String path;

    ViewsPath(String path) {
        this.path = path;
    }
}
