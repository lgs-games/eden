package com.lgs.eden.api.wrapper;

import com.lgs.eden.utils.Utility;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.Date;

/**
 * Wrapper for profile Data
 */
public class ProfileData {

    public final String username;
    // list of friends
    public final ObservableList<FriendData> friends;
    // recent games, can be empty (size = 0), size <= 3
    public final RecentGameData[] recentGames;
    // id user
    public final int userID;
    // real number of friends, not neccessarily the size of
    public final int friendNumber;
    // reputation
    public final int reputation;
    // biography
    public final String biography;
    // last login date
    public final Date lastSeen;
    // member since
    public final Date memberSinceDate;
    // avatar path
    public Image avatar;

    public ProfileData(String username, int userID, String avatar,
                       int friendNumber, int reputation, String biography, Date lastSeen,
                       Date memberSinceDate,
                       ObservableList<FriendData> friends, RecentGameData[] recentGames) {
        this.username = username;
        this.avatar = Utility.loadImage(avatar);
        this.friends = friends;
        this.recentGames = recentGames;
        this.userID = userID;
        this.friendNumber = friendNumber;
        this.reputation = reputation;
        this.biography = biography;
        this.lastSeen = lastSeen;
        this.memberSinceDate = memberSinceDate;
    }
}
