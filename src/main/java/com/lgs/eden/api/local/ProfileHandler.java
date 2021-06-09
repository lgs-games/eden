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
import java.util.HashMap;

/**
 * Implementation of ProfileAPI
 */
class ProfileHandler implements ProfileAPI {

    private final HashMap<Integer, ArrayList<FriendData>> friendList = new HashMap<>();

    @Override
    public ArrayList<FriendData> getFriendList(int userID, int loggedID) {
        if (friendList.containsKey(userID)) return friendList.get(userID);

        ArrayList<FriendData> friends = new ArrayList<>();
        if (userID == 23){
            friends.add(getFriendData(24));
            friends.add(getFriendData(25));
            friends.add(getFriendData(26));
            friends.add(getFriendData(27));
        } else if ( userID != 25 ){
            friends.add(getFriendData(23));
        } else {
            friends.add(getFriendData(23));
            friends.add(getFriendData(24));
        }

        friendList.put(userID, friends);
        return friendList.get(userID);
    }

    @Override
    public ProfileData getProfileData(int userID, int loggedID) {
        ObservableList<FriendData> friendDataObservableList = FXCollections.observableArrayList();
        for (FriendData f:this.getFriendList(userID, loggedID)) {
            // only show friend with the profile shown
            if(!evaluateRelationShip(userID, f.id).equals(FriendShipStatus.FRIENDS)) continue;
            friendDataObservableList.add(
                    new FriendData(
                      f.getAvatarPath(),
                      f.name,
                      f.online,
                      f.id,
                      evaluateRelationShip(loggedID, f.id)
                    )
            );
        }

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
                    evaluateRelationShip(userID, loggedID)
            );
        } else if (userID == 24){
            return new ProfileData("Raphik2",24, "/avatars/24.png", friendNumber, 0,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2021-03-18T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, loggedID)
            );
        } else if (userID == 25){
            return new ProfileData("Calistral",25, "/avatars/25.png", friendNumber, -1,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-12-03T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, loggedID)
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
                    evaluateRelationShip(userID, loggedID)
            );
        } else if (userID == 27){
            return new ProfileData("Raphistro",27, "/avatars/27.png", friendNumber, 17570,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-03-09T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, loggedID)
            );
        }

        throw new IllegalStateException("Not supported");
    }

    @Override
    public void addFriend(int userID, int currentUserID){
        ArrayList<FriendData> friendList = getFriendList(currentUserID, userID);
        friendList.add(getFriendData(userID));
    }

    @Override
    public void removeFriend(int userID, int currentUserID){
        ArrayList<FriendData> friendList = getFriendList(userID, currentUserID);
        friendList.remove(getFriendData(currentUserID));

        friendList = getFriendList(currentUserID, userID);
        friendList.remove(getFriendData(userID));
    }

    @Override
    public void acceptFriend(int userID, int currentUserID) {
        ArrayList<FriendData> friendList = getFriendList(currentUserID, userID);
        friendList.add(getFriendData(userID));
    }

    @Override
    public void refuseFriend(int userID, int currentUserID) {
        removeFriend(userID, currentUserID);
    }

    // ------------------------------ Messages ----------------------------- \\

    @Override
    public FriendConversationView getMessageWithFriend(int friendID, int loggedID) {
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

        return new FriendConversationView(getFriendData(friendID), getFriendData(loggedID), messages, conversations);
    }

    // ------------------------------ UTILS ----------------------------- \\

    private FriendData getFriendData(int userID) {
        switch (userID){
            case 23: return new FriendData("/avatars/23.png", "Raphik", false, 23, FriendShipStatus.FRIENDS);
            case 24: return new FriendData("/avatars/24.png", "Raphik2", false, 24, FriendShipStatus.FRIENDS);
            case 25: return new FriendData("/avatars/25.png", "Calistral", false, 25, FriendShipStatus.FRIENDS);
            case 26: return new FriendData("/avatars/26.png", "Caliki", false, 26, FriendShipStatus.FRIENDS);
            case 27: return new FriendData("/avatars/27.png", "Raphistro", false, 27, FriendShipStatus.FRIENDS);
        }
        throw new IllegalArgumentException("not supported userID");
    }

    private FriendShipStatus evaluateRelationShip(int userID, int loggedID) {
        if(loggedID == userID) return FriendShipStatus.USER;

        boolean one = inFriendList(userID, loggedID);
        boolean two = inFriendList(loggedID, userID);

        if (one && two) return FriendShipStatus.FRIENDS;
        if (one) return FriendShipStatus.GOT_REQUESTED;
        if (two) return FriendShipStatus.REQUESTED;

        return FriendShipStatus.NONE;
    }

    private boolean inFriendList(int userID, int loggedID){
        ArrayList<FriendData> friendList = getFriendList(userID, loggedID);
        return friendList.contains(new FriendData(null, null, false, loggedID, FriendShipStatus.NONE));
    }
}
