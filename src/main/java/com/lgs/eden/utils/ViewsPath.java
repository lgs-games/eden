package com.lgs.eden.utils;

/**
 * This contains the paths to the views, it makes the code cleaner
 */
public enum ViewsPath {
    FRAME("/views/frame.fxml"),
    FRAME_MAIN("/views/frame_main.fxml"),

    UPDATE("/views/update.fxml"),

    LOGIN("/views/login.fxml"),
    REGISTER("/views/register.fxml"),
    SETTINGS("/views/settings.fxml"),

    PROFILE("/views/profile.fxml"), // profile menu
    FRIEND_CELL("/views/profile/friendcell.fxml"),
    PROFILE_CARD("/views/profile/card.fxml"),
    FRIENDS_LIST("/views/friends_list.fxml"),
    INVENTORY("/views/inventory.fxml"),

    GAMES(null), // games menu

    MARKETPLACE("/views/marketplace.fxml"),
    MARKETPLACE_GAME("/views/market/marketplace_game.fxml"),
    ;

    // view path
    public final String path;

    ViewsPath(String path) {
        this.path = path;
    }
}
