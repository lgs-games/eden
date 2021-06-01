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
            return new LoginResponseData(-1);
        }
        return new LoginResponseData(0, 23, username, "/avatars/23.png");
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
    public static ArrayList<FriendData> getFriendList(int userID) {
        ArrayList<FriendData> friendList = new ArrayList<>();

        FriendData fr0 = new FriendData("/avatars/23.png", "Raphik", false, 23);
        FriendData fr1 = new FriendData("/avatars/24.png", "Raphik2", false, 24);
        FriendData fr2 = new FriendData("/avatars/25.png", "Calistral", false, 25);
        FriendData fr3 = new FriendData("/avatars/26.png", "Caliki", false, 26);
        FriendData fr4 = new FriendData("/avatars/27.png", "Raphistro", false, 27);

        if (userID == 23){
            friendList.add(fr1);
            friendList.add(fr2);
            friendList.add(fr3);
            friendList.add(fr4);
        } else {
            friendList.add(fr0);
        }
        return friendList;
    }

    /** Returns profile Data for an user. fixme: Only 23 to 27 are supported for now, as test values. */
    public static ProfileData getProfileData(int userID) {
        ObservableList<FriendData> friendDataObservableList = FXCollections.observableArrayList();
        friendDataObservableList.addAll(Api.getFriendList(userID));

        int friendNumber = friendDataObservableList.size(); // observable friend list may contains
        // less user that friendNumber so that not the real value

        RecentGameData[] recentGamesData = new RecentGameData[]{};

        if (userID == 23){
            recentGamesData = new RecentGameData[]{
                    new RecentGameData(Utility.loadImage("/games/prim-icon.png"), "Prim", 0, RecentGameData.PLAYING),
                    new RecentGameData(Utility.loadImage("/games/enigma-icon.png"), "Enigma", 1020, 30)
            };

            return new ProfileData("Raphik",23, "/avatars/23.png",
                    friendNumber, 9999,
                    "Raphiki is a great programmer at ENSIIE engineering school.",
                    new Date(), Date.from(Instant.parse("2020-12-03T10:15:30.00Z")), friendDataObservableList,
              recentGamesData
            );
        } else if (userID == 24){
            return new ProfileData("Raphik2",24, "/avatars/24.png", friendNumber, 0,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2021-03-18T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData
            );
        } else if (userID == 25){
            return new ProfileData("Calistral",25, "/avatars/25.png", friendNumber, -1,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-12-03T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData
            );
        } else if (userID == 26){
            return new ProfileData("Caliki", 26, "/avatars/26.png", friendNumber, 0,
                    "This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really",
                    new Date(), Date.from(Instant.parse("2020-12-03T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData
            );
        } else if (userID == 27){
            return new ProfileData("Raphistro",27, "/avatars/27.png", friendNumber, 17570,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-03-09T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData
            );
        }

        throw new IllegalStateException("Not supported");
    }

    public static void logout() {

    }
}
