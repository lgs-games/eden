package com.lgs.eden.api.profile.friends;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

/**
 * FriendData wrapper for API Result.
 *
 * We need an ID in order to show the profile,
 * online to sort with online/offline in the friend list and
 * image/avatar to show the friend.
 */
public class FriendData {

    public final Image avatar;
    public final String name;
    public final boolean online;
    public final String id;
    public final FriendShipStatus friendShipStatus;

    private final String path;

    public FriendData(String avatar, String name, boolean online, String id, FriendShipStatus friendShipStatus) {
        this.avatar = avatar == null ? null : Utility.loadImage(avatar);
        this.path = avatar;
        this.name = name;
        this.online = online;
        this.id = id;
        this.friendShipStatus = friendShipStatus;
    }

    public FriendData(String id) {
        this(null, null, false, id, null);
    }

    @SuppressWarnings("unused")
    public String getAvatarPath() { return path; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendData that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() { return id != null ? id.hashCode() : 0; }

    @Override
    public String toString() {
        return "FriendData{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", friendShipStatus=" + friendShipStatus +
                '}';
    }
}
