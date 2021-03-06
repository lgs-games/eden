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
    public final boolean isDev;

    public ProfileData(String username, String userID, String avatar,
                       int reputation, String biography, Date lastSeen,
                       Date memberSinceDate,
                       ObservableList<FriendData> friends, RecentGameData[] recentGames,
                       boolean online,
                       FriendShipStatus statusWithLogged, ReputationScore score, boolean isDev) {
        this.avatarPath = avatar;
        this.username = username;
        this.avatar = avatar == null ? null : Utility.loadImage(avatar);
        this.friends = friends;
        this.recentGames = recentGames;
        this.userID = userID;
        this.reputation = reputation;
        this.biography = biography;
        this.lastSeen = lastSeen;
        this.memberSinceDate = memberSinceDate;
        this.online = online;
        this.statusWithLogged = statusWithLogged;
        this.score = score;
        this.isDev = isDev;
    }

    /**
     * Make a new one while changing two fields
     */
    public ProfileData(ProfileData data, int reputation, ReputationScore score) {
        this(data.username, data.userID, data.avatarPath, reputation, data.biography,
                data.lastSeen, data.memberSinceDate, data.friends, data.recentGames, data.online,
                data.statusWithLogged, score, data.isDev);
    }

    public ProfileData(ProfileData data, FriendShipStatus status) {
        this(data.username, data.userID, data.avatarPath, data.reputation, data.biography,
                data.lastSeen, data.memberSinceDate, data.friends, data.recentGames, data.online, status, data.score,
                data.isDev);
    }

    public ProfileData(String userID) {
        this(null, userID, null, 0, null, null,
                null, null, null, false, null, null, false);
    }

    public ProfileData(ProfileData data, ObservableList<FriendData> friendList) {
        this(data.username, data.userID, data.avatarPath, data.reputation, data.biography,
                data.lastSeen, data.memberSinceDate, friendList, data.recentGames, data.online, data.statusWithLogged,
                data.score, data.isDev);
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
                ", reputation=" + reputation +
                ", biography='" + biography + '\'' +
                ", lastSeen=" + lastSeen +
                ", memberSinceDate=" + memberSinceDate +
                ", avatarPath='" + avatarPath + '\'' +
                ", statusWithLogged=" + statusWithLogged +
                ", score=" + score +
                ", isDev=" + isDev +
                ", online=" + online +
                '}';
    }

    public ProfileData change(ReputationChangeData r) {
        return new ProfileData(this, r.rep(), r.score());
    }
}
