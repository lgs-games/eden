package com.lgs.eden.api.games;

/**
 * Short version of GameViewData. Used when update got
 * requested.
 * @see GameViewData
 */
public class ShortGameViewData {

    public final int playerAchievements;
    public final int friendsPlaying;
    public final int timePlayed;

    public ShortGameViewData(int playerAchievements, int friendsPlaying, int timePlayed) {

        this.playerAchievements = playerAchievements;
        this.friendsPlaying = friendsPlaying;
        this.timePlayed = timePlayed;
    }
}
