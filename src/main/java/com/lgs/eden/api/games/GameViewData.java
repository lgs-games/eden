package com.lgs.eden.api.games;

import com.lgs.eden.api.news.BasicNewsData;

/**
 * Wrapper for all the informations returned to show
 * the game view for an user.
 */
public class GameViewData extends BasicGameData {

    public final String version;
    public final BasicNewsData lastNews;
    public final int playerAchievements;
    public final int numberOfAchievements;
    public final int friendsPlaying;
    public final int timePlayed;

    public GameViewData(int id, String name, String icon,
                        String version, BasicNewsData lastNews, int playerAchievements,
                        int numberOfAchievements, int friendsPlaying, int timePlayed) {
        super(id, name, icon);
        this.version = version;
        this.lastNews = lastNews;
        this.playerAchievements = playerAchievements;
        this.numberOfAchievements = numberOfAchievements;
        this.friendsPlaying = friendsPlaying;
        this.timePlayed = timePlayed;
    }
}
