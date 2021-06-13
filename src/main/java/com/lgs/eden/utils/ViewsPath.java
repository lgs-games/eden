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
    FRIEND_CELL("/views/profile/friend_cell.fxml"),
    PROFILE_CARD("/views/profile/card.fxml"),
    FRIENDS_LIST("/views/friends_list.fxml"),
    INVENTORY("/views/inventory.fxml"),
    MESSAGES("/views/profile/messages.fxml"),
    CONVERSATION_CELL("/views/profile/conversation_cell.fxml"),
    MESSAGE_CELL("/views/profile/message_cell.fxml"),

    SEARCH("/views/search.fxml"),

    GAMES(null), // games menu
    GAMES_LIST("/views/gameslist.fxml"), // games menu
    GAMES_LIST_DDL_BOX("/views/game/download_box.fxml"), // games menu
    GAMES_LIST_EMPTY("/views/game/gameslist_empty.fxml"), // games menu
    GAMES_LIST_CELL("/views/game/game_cell.fxml"), // games cell
    GAMES_A_NEWS("/views/game/a_news.fxml"), // all news
    GAMES_ALL_NEWS("/views/game/all_news.fxml"), // all news
    GAMES_ALL_NEWS_CELL("/views/game/all_news_cell.fxml"), // all news cell
    GAMES_SETTINGS("/views/game/settings.fxml"), // settings
    ACHIEVEMENTS("/views/achievements.fxml"), // achievements

    MARKETPLACE("/views/marketplace.fxml"),
    MARKETPLACE_GAME("/views/market/marketplace_game.fxml"),
    ;

    // view path
    public final String path;

    ViewsPath(String path) {
        this.path = path;
    }
}
