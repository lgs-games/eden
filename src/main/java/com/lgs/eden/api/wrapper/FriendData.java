package com.lgs.eden.api.wrapper;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

public class FriendData {

    public final Image avatar;
    public final String name;
    public final boolean online;
    public final int id;

    public FriendData(String avatar, String name, boolean online, int id) {
        this.avatar = Utility.loadImage(avatar);
        this.name = name;
        this.online = online;
        this.id = id;
    }

}
