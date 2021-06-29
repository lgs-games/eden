package com.lgs.eden.api.profile;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;
import com.lgs.eden.utils.Utility;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.Date;

/**
 * Wrapper for profile data result from the API
 */
public class ProfileData {

    public final String username;
    // list of friends
    public final ObservableList<FriendData> friends;
    // recent games, can be empty (size = 0), size <= 3
    public RecentGameData[] recentGames;
    // id user
    public final String userID;
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
    // online ?
    public final boolean online;

    public final FriendShipStatus statusWithLogged;
    public final ReputationScore score;

    public ProfileData(String username, String userID, String avatar,
                       int friendNumber, int reputation, String biography, Date lastSeen,
                       Date memberSinceDate,
                       ObservableList<FriendData> friends, RecentGameData[] recentGames,
                       boolean online,
                       FriendShipStatus statusWithLogged, ReputationScore score) {
        this.avatarPath = avatar;
        this.username = username;
        this.avatar = avatar == null ? null : Utility.loadImage(avatar);
        this.friends = friends;
        this.recentGames = recentGames;
        this.userID = userID;
        this.friendNumber = friendNumber;
        this.reputation = reputation;
        this.biography = biography;
        this.lastSeen = lastSeen;
        this.memberSinceDate = memberSinceDate;
        this.online = online;
        this.statusWithLogged = statusWithLogged;
        this.score = score;
    }

    /**
     * Make a new one while changing two fields
     */
    public ProfileData(ProfileData data, int reputation, ReputationScore score) {
        this(data.username, data.userID, data.avatarPath, data.friendNumber, reputation, data.biography,
                data.lastSeen, data.memberSinceDate, data.friends, data.recentGames, data.online, data.statusWithLogged, score);
    }

    public ProfileData(ProfileData data, FriendShipStatus status) {
        this(data.username, data.userID, data.avatarPath, data.friends.size(), data.reputation, data.biography,
                data.lastSeen, data.memberSinceDate, data.friends, data.recentGames, data.online, status, data.score);
    }

    public ProfileData(String userID) {
        this(null, userID, null, 0, 0, null, null,
                null, null, null, false, null, null);
    }

    public ProfileData(ProfileData data, ObservableList<FriendData> friendList) {
        this(data.username, data.userID, data.avatarPath, friendList.size(), data.reputation, data.biography,
                data.lastSeen, data.memberSinceDate, friendList, data.recentGames, data.online, data.statusWithLogged, data.score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileData that)) return false;
        return userID.equals(that.userID);
    }

    @Override
    public int hashCode() {
        return userID != null ? userID.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProfileData{" +
                "username='" + username + '\'' +
                ", friends=" + friends +
                ", recentGames=" + Arrays.toString(recentGames) +
                ", userID=" + userID +
                ", friendNumber=" + friendNumber +
                ", reputation=" + reputation +
                ", biography='" + biography + '\'' +
                ", lastSeen=" + lastSeen +
                ", memberSinceDate=" + memberSinceDate +
                ", avatarPath='" + avatarPath + '\'' +
                ", statusWithLogged=" + statusWithLogged +
                ", score=" + score +
                ", online=" + online +
                '}';
    }
}
