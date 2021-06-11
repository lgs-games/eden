package com.lgs.eden.api.profile;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;
import com.lgs.eden.utils.Utility;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.Date;

/**
 * Wrapper for profile data result from the API
 */
public class ProfileData {

    public final String username;
    // list of friends
    public final ObservableList<FriendData> friends;
    // recent games, can be empty (size = 0), size <= 3
    public final RecentGameData[] recentGames;
    // id user
    public final int userID;
    // real number of friends, not necessarily the size of
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
    public final Image avatar;
    public final String avatarPath;

    public final FriendShipStatus statusWithLogged;
    public final ReputationScore score;

    public ProfileData(String username, int userID, String avatar,
                       int friendNumber, int reputation, String biography, Date lastSeen,
                       Date memberSinceDate,
                       ObservableList<FriendData> friends, RecentGameData[] recentGames,
                       FriendShipStatus statusWithLogged, ReputationScore score) {
        this.avatarPath = avatar;
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
        this.statusWithLogged = statusWithLogged;
        this.score = score;
    }

    /**
     * Make a new one while changing two fields
     */
    public ProfileData(ProfileData data, int reputation, ReputationScore score) {
        this(data.username, data.userID, data.avatarPath, data.friendNumber, reputation, data.biography,
                data.lastSeen, data.memberSinceDate, data.friends, data.recentGames, data.statusWithLogged, score);
    }

    @Override
    public String toString() {
        return "ProfileData{" +
                "username='" + username + '\'' +
                ", userID=" + userID +
                ", friendNumber=" + friendNumber +
                ", reputation=" + reputation +
                ", biography='" + biography + '\'' +
                ", lastSeen=" + lastSeen +
                ", memberSinceDate=" + memberSinceDate +
                ", avatarPath='" + avatarPath + '\'' +
                ", statusWithLogged=" + statusWithLogged +
                '}';
    }
}
