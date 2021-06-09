package com.lgs.eden.api.local;

import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.ProfileAPI;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.api.profile.RecentGameData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;
import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.api.profile.friends.messages.MessageType;
import com.lgs.eden.utils.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementation of ProfileAPI
 */
class ProfileHandler implements ProfileAPI {

    @Override
    public ArrayList<FriendData> getFriendList(int userID) {
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

    @Override
    public ProfileData getProfileData(int userID, int loggedID) {
        ObservableList<FriendData> friendDataObservableList = FXCollections.observableArrayList();
        friendDataObservableList.addAll(this.getFriendList(userID));

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
                    recentGamesData,
                    loggedID == userID ? FriendShipStatus.USER : FriendShipStatus.FRIENDS
            );
        } else if (userID == 24){
            return new ProfileData("Raphik2",24, "/avatars/24.png", friendNumber, 0,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2021-03-18T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    loggedID == userID ? FriendShipStatus.USER : FriendShipStatus.FRIENDS
            );
        } else if (userID == 25){
            return new ProfileData("Calistral",25, "/avatars/25.png", friendNumber, -1,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-12-03T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    loggedID == 23 ? FriendShipStatus.FRIENDS : FriendShipStatus.NONE
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
                    recentGamesData,
                    loggedID == 23 ? FriendShipStatus.FRIENDS : FriendShipStatus.NONE
            );
        } else if (userID == 27){
            return new ProfileData("Raphistro",27, "/avatars/27.png", friendNumber, 17570,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-03-09T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    loggedID == 23 ? FriendShipStatus.FRIENDS : FriendShipStatus.NONE
            );
        }

        throw new IllegalStateException("Not supported");
    }

    @Override
    public FriendConversationView getMessageWithFriend(int friendID) {
        ObservableList<MessageData> messages = FXCollections.observableArrayList();
        ObservableList<ConversationData> conversations = FXCollections.observableArrayList();

        // "we are faking the pick of the last recent one conv"
        if (friendID == -1) friendID = 24;

        if (friendID == 24){
            messages.add(
                    new MessageData(
                            23,
                            "java.lang.NoSuchMethodException: com.lgs.eden.views.achievements.\nAchievements.<init>()",
                            MessageType.TEXT,
                            Date.from(Instant.now()),
                            true
                    )
            );
            messages.add(
                    new MessageData(
                            24,
                            "new Achievements()",
                            MessageType.TEXT,
                            Date.from(Instant.now()),
                            false
                    )
            );
        } else if (friendID != 27){
            ProfileData profileData = getProfileData(friendID, friendID);
            conversations.add(new ConversationData("/avatars/"+friendID+".png",
                    profileData.username, false, friendID, 0));
        }

        conversations.add(new ConversationData("/avatars/24.png", "Raphik2", true, 24, 1));
        conversations.add(new ConversationData("/avatars/27.png", "Raphistro", false, 27, 0));


        return new FriendConversationView(friendID, messages, conversations);
    }
}
