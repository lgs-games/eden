package com.lgs.eden.api;

import com.lgs.eden.api.wrapper.*;
import com.lgs.eden.utils.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * Todo: fix this class, ...
 */

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
    public static LoginResponseData login(String username, String pwd) {
        if (!username.equals("Raphik") || !pwd.equals("tester")) {
            return new LoginResponseData(-1, -1);
        }
        return new LoginResponseData(0, 23);
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

    /**
     * Returns complete friend list
     */
    public static ArrayList<FriendData> getFriendList() {
        ArrayList<FriendData> friendList = new ArrayList<>();
        FriendData fr1 = new FriendData(Utility.loadImage("/icon64.png"), "Raphik2", false, 24);
        FriendData fr2 = new FriendData(Utility.loadImage("/icon64.png"), "Calistral", false, 25);
        FriendData fr3 = new FriendData(Utility.loadImage("/icon64.png"), "Caliki", false, 26);
        FriendData fr4 = new FriendData(Utility.loadImage("/icon64.png"), "Raphistro", false, 27);

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

            return new ProfileData("Raphik",
              23, friendNumber, 9999,
                    "Raphiki is a great programmer at ENSIIE engineering school.\nCheck HunterFruitti25 on Xbox (:",
                    new Date(), Date.from(Instant.parse("2021-12-03T10:15:30.00Z")), friendDataObservableList,
              recentGamesData
            );
        }

        throw new IllegalStateException("Not supported");
    }

    public static void logout() {

    }
}
