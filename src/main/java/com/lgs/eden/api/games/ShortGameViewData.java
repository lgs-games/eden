package com.lgs.eden.api.games;

/**
 * Short version of GameViewData. Used when update got
 * requested.
 *
 * @see GameViewData
 */
public record ShortGameViewData(int playerAchievements, int friendsPlaying, int timePlayed) {}
