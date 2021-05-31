package com.lgs.eden.api;

import com.lgs.eden.api.wrapper.*;
import com.lgs.eden.utils.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Api {

    public static EdenVersionData getEdenVersion() {
        // fake some delay
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new EdenVersionData("1.0.0");
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
    public static LoginResponse login(String username, String pwd) {
        if (!username.equals("Raphik") || !pwd.equals("tester")) {
            return new LoginResponse(-1, -1);
        }
        return new LoginResponse(0, 23);
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

    private static ArrayList<FriendData> getFriendList() {
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

    /** Returns profile Data for an user. todo: Only 23 is supported for now. */
    public static ProfileData getProfileData(int userID) {
        if (userID == 23){
            // todo: that's a fake of API data
            RecentGameData[] recentGamesData = new RecentGameData[]{
                    new RecentGameData(Utility.loadImage("/games/prim-icon.png"), "Prim", 0, RecentGameData.PLAYING),
                    new RecentGameData(Utility.loadImage("/games/enigma-icon.png"), "Enigma", 1020, 30)
            };

            ObservableList<FriendData> friendDataObservableList = FXCollections.observableArrayList();
            friendDataObservableList.addAll(Api.getFriendList());

            int friendNumber = 4; // observable friend list may contains less user that friendNumber

            return new ProfileData(
              23, friendNumber, 9999,  (short) 0,
              "No desc", null, null, friendDataObservableList,
              recentGamesData
            );
        }

        throw new IllegalStateException("Not supported");
    }
}
