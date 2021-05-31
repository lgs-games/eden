package com.lgs.eden.api;

import com.lgs.eden.api.wrapper.FriendData;
import com.lgs.eden.utils.Utility;

import java.util.ArrayList;

public class Api {

    public static String getEdenVersion() {
        // fake some delay
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "1.0.1";
    }

    public static String passwordForgotPage(String code) {
        // website only supports en or fr
        code = Api.formatCode(code);
        return "https://lgs-games.com/"+code+"/password_forgot";
    }

    private static String formatCode(String code) {
        if (!code.equals("en") && !code.equals("fr"))
            code = "en";
        return code;
    }

    /**
     * todo
     * Return code, see ApiCodes
     */
    public static int login(String username, String pwd) {
        if (!username.equals("Raphik") || !pwd.equals("tester")) {
            return -1;
        }
        return 0;
    }

    /**
     * todo
     * Return code, see ApiCodes
     */
    public static int register(String username, String pwd, String email) {
        if (!username.equals("admin") || !pwd.equals("azerty") || !email.equals("a@b.c")) {
            return -1;
        }
        return 0;
    }

    public static ArrayList<FriendData> getFriendList() {
        ArrayList<FriendData> friendList = new ArrayList<>();
        FriendData fr1 = new FriendData(Utility.loadImage("/icon64.png"), "Raphik");
        FriendData fr2 = new FriendData(Utility.loadImage("/icon64.png"), "Calistral");
        FriendData fr3 = new FriendData(Utility.loadImage("/icon64.png"), "Caliki");
        FriendData fr4 = new FriendData(Utility.loadImage("/icon64.png"), "Raphistro");

        friendList.add(fr1);
        friendList.add(fr2);
        friendList.add(fr3);
        friendList.add(fr4);
        return friendList;
    }

}
