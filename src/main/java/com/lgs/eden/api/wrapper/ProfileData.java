package com.lgs.eden.api.wrapper;

import javafx.collections.ObservableList;

import java.util.Date;

/**
 * Wrapper for profile Data
 */
public class ProfileData {

    // list of friends
    public final ObservableList<FriendData> friends;
    // recent games, can be empty (size = 0), size <= 3
    public final RecentGameData[] recentGames;
    // id user
    public final int usernameID;
    // real number of friends, not neccessarily the size of
    public final int friendNumber;
    // reputation
    public final int reputation;
    // user status
    public final short status;
    // biography
    public final String biography;
    // last login date
    public final Date lastSeen;
    // member since
    public final Date memberSinceDate;

    public ProfileData(int usernameID,
                       int friendNumber, int reputation, short status, String biography, Date lastSeen,
                       Date memberSinceDate,
                       ObservableList<FriendData> friends, RecentGameData[] recentGames) {
        this.friends = friends;
        this.recentGames = recentGames;
        this.usernameID = usernameID;
        this.friendNumber = friendNumber;
        this.reputation = reputation;
        this.status = status;
        this.biography = biography;
        this.lastSeen = lastSeen;
        this.memberSinceDate = memberSinceDate;
    }
}
