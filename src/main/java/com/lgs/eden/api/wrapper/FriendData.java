package com.lgs.eden.api.wrapper;

import javafx.scene.image.Image;

public class FriendData {

    public final Image avatar;
    public final String name;
    public final boolean online;
    public final int id;

    public FriendData(Image avatar, String name, boolean online, int id) {
        this.avatar = avatar;
        this.name = name;
        this.online = online;
        this.id = id;
    }

}
