package com.lgs.eden.api.wrapper;

import javafx.scene.image.Image;

public class FriendData {

    private Image avatar;

    private String name;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Image getAvatar() {return avatar;}
    public void setAvatar(Image avatar) {this.avatar = avatar;}

    public FriendData(Image avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

}
