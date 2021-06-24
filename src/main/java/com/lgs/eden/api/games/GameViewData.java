package com.lgs.eden.api.games;

import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

/**
 * Wrapper for all the informations returned to show
 * the game view for an user.
 */
public class GameViewData extends BasicGameData {

    public final String version;
    public final BasicNewsData lastNews;
    public final Image background;
    public final int playerAchievements;
    public final int numberOfAchievements;
    public final int friendsPlaying;
    public final int timePlayed;
    public final GameUpdateData update;

    public GameViewData(String id, String name, String icon,
                        String version, BasicNewsData lastNews, String background, int playerAchievements,
                        int numberOfAchievements, int friendsPlaying, int timePlayed, GameUpdateData update) {
        super(id, name, icon);
        this.version = version;
        this.lastNews = lastNews;
        this.background = Utility.loadImage(background);
        this.playerAchievements = playerAchievements;
        this.numberOfAchievements = numberOfAchievements;
        this.friendsPlaying = friendsPlaying;
        this.timePlayed = timePlayed;
        this.update = update;
    }
}
