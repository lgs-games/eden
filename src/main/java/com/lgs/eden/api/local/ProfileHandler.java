package com.lgs.eden.api.local;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.profile.ReputationScore;
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
import javafx.geometry.Point2D;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Implementation of ProfileAPI
 */
class ProfileHandler implements ProfileAPI {

    private final HashMap<Integer, ArrayList<FriendData>> friendList = new HashMap<>();

    private final HashMap<String, ArrayList<FriendData>> cache = new HashMap<>();
    private ArrayList<FriendData> users = null;

    public ArrayList<FriendData> searchUsers(String filter, int currentUserID) {
        if (filter.isEmpty()) return new ArrayList<>();
        // return cache
        if (cache.containsKey(filter)) return cache.get(filter);

        if (users == null){
            users = new ArrayList<>();
            users.add(getFriendData(23));
            users.add(getFriendData(24));
            users.add(getFriendData(25));
            users.add(getFriendData(26));
            users.add(getFriendData(27));
            users.add(getFriendData(28));
            users.add(getFriendData(29));
        }

        ArrayList<FriendData> selected = new ArrayList<>();
        // apply filter
        for (FriendData d: users) {
            if (d.name.toLowerCase().contains(filter) || (d.id+"").equals(filter)){
                selected.add(new FriendData(
                        d.getAvatarPath(),
                        d.name,
                        d.online,
                        d.id,
                        evaluateRelationShip(currentUserID, d.id)
                ));
            }
        }

        // cache
        cache.put(filter, selected);
        return selected;
    }

    @Override
    public ArrayList<FriendData> getFriendList(int userID) {
        if (friendList.containsKey(userID)) return friendList.get(userID);

        ArrayList<FriendData> friends = new ArrayList<>();
        if (userID == 23){
            friends.add(getFriendData(24));
            friends.add(getFriendData(25));
            friends.add(getFriendData(26));
            friends.add(getFriendData(27));
        } else if ( userID != 25 ){
            if ( userID != 28 && userID != 29) friends.add(getFriendData(23));
        } else {
            friends.add(getFriendData(23));
            friends.add(getFriendData(24));
        }

        friendList.put(userID, friends);
        return friendList.get(userID);
    }

    private final HashMap<Integer, ProfileData> profiles = new HashMap<>();

    @Override
    public ProfileData getProfileData(int userID, int currentUserID) {
        if (profiles.containsKey(userID)) return profiles.get(userID);

        ObservableList<FriendData> friendDataObservableList = FXCollections.observableArrayList();
        for (FriendData f:this.getFriendList(userID)) {
            // only show friend with the profile shown
            if(!evaluateRelationShip(userID, f.id).equals(FriendShipStatus.FRIENDS)) continue;
            friendDataObservableList.add(
                    new FriendData(
                      f.getAvatarPath(),
                      f.name,
                      f.online,
                      f.id,
                      evaluateRelationShip(currentUserID, f.id)
                    )
            );
        }

        int friendNumber = friendDataObservableList.size(); // observable friend list may contains
        // less user that friendNumber so that not the real value

        RecentGameData[] recentGamesData = new RecentGameData[]{};

        ProfileData r = null;

        if (userID == 23){
            recentGamesData = new RecentGameData[]{
                    new RecentGameData(Utility.loadImage("/games/prim-icon.png"), "Prim", 0, 1),
                    // new RecentGameData(Utility.loadImage("/games/enigma-icon.png"), "Enigma", 1020, 30)
            };

            r = new ProfileData("Raphik", 23, "/avatars/23.png",
                    friendNumber, 9999,
                    "Raphiki is a great programmer at ENSIIE engineering school.",
                    new Date(), Date.from(Instant.parse("2020-12-03T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, currentUserID),
                    ReputationScore.NONE
            );
        } else if (userID == 24){
            r = new ProfileData("Raphik2",24, "/avatars/24.png", friendNumber, 0,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2021-03-18T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, currentUserID),
                    ReputationScore.NONE
            );
        } else if (userID == 25){
            r = new ProfileData("Calistral",25, "/avatars/25.png", friendNumber, -1,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-12-03T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, currentUserID),
                    ReputationScore.DECREASED
            );
        } else if (userID == 26){
            r = new ProfileData("Caliki", 26, "/avatars/26.png", friendNumber, 0,
                    "This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really"
                            +"This is a really"+"This is a really"+"This is a really"+"This is a really"+"This is a really",
                    new Date(), Date.from(Instant.parse("2020-12-03T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, currentUserID),
                    ReputationScore.NONE
            );
        } else if (userID == 27){
            r = new ProfileData("Raphistro",27, "/avatars/27.png", friendNumber, 17570,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-03-09T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, currentUserID),
                    ReputationScore.INCREASED
            );
        } else if (userID == 28){
            r = new ProfileData("XXX",28, "/avatars/27.png", friendNumber, 0,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-03-09T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, currentUserID),
                    ReputationScore.NONE
            );
        } else if (userID == 29){
            r = new ProfileData("YYY",29, "/avatars/25.png", friendNumber, 0,
                    "No description yet.",
                    new Date(), Date.from(Instant.parse("2020-03-09T10:15:30.00Z")), friendDataObservableList,
                    recentGamesData,
                    evaluateRelationShip(userID, currentUserID),
                    ReputationScore.NONE
            );
        }

        if (r == null ) throw new IllegalStateException("Not supported");

        profiles.put(userID, r);
        return r;
    }

    @Override
    public ProfileData changeReputation(int userID, int currentUserID, boolean increase) {
        ProfileData p = getProfileData(userID, currentUserID);

        ReputationScore score = p.score;
        // new values
        int newRep = p.reputation;
        ReputationScore newScore = ReputationScore.NONE;
        switch (score){
            case NONE:
                if (increase){
                    newRep++;
                    newScore = ReputationScore.INCREASED;
                } else {
                    newRep--;
                    newScore = ReputationScore.DECREASED;
                }
                break;
            case INCREASED:
                if (increase) return null;
                else {
                    newRep--;
                }
                break;
            case DECREASED:
                if (!increase) return null;
                else {
                    newRep++;
                }
                break;
        }

        ProfileData newProfileData = new ProfileData(p, newRep, newScore);

        profiles.put(userID, newProfileData);

        return newProfileData;
    }

    @Override
    public void addFriend(int friendID, int currentUserID){
        ArrayList<FriendData> friendList = getFriendList(currentUserID);
        friendList.add(getFriendData(friendID));
    }

    @Override
    public void removeFriend(int friendID, int currentUserID){
        ArrayList<FriendData> friendList = getFriendList(friendID);
        friendList.remove(getFriendData(currentUserID));

        friendList = getFriendList(currentUserID);
        friendList.remove(getFriendData(friendID));
    }

    @Override
    public void acceptFriend(int friendID, int currentUserID) {
        ArrayList<FriendData> friendList = getFriendList(currentUserID);
        friendList.add(getFriendData(friendID));
    }

    @Override
    public void refuseFriend(int friendID, int currentUserID) {
        removeFriend(friendID, currentUserID);
    }

    // ------------------------------ Messages ----------------------------- \\

    @Override
    public FriendConversationView getMessageWithFriend(int friendID, int currentUserID) {
        ObservableList<MessageData> messages = FXCollections.observableArrayList();
        ObservableList<ConversationData> conversations = FXCollections.observableArrayList();

        // get conversations
        conversations.addAll(getConversations(currentUserID));

        // can't find one
        if (friendID == -1 && conversations.isEmpty()) return null;

        // take the first conversation id one
        if (friendID == -1){
            ConversationData conversationData = conversations.get(0);
            friendID = conversationData.id;
        }

        if (conversations.isEmpty() || !conversations.contains(new ConversationData(friendID))){
             // we add a new one
            if (!newConversation(friendID, currentUserID)){
               throw new IllegalStateException("error");
            }
        }

        // load messages
        messages.addAll(getUserMessagesWith(friendID, currentUserID));
        messages.forEach(m -> m.read = true);

        closeConversation(friendID, currentUserID);
        if (!newConversation(friendID, currentUserID)){
            throw new IllegalStateException("error");
        }
        conversations.clear();
        conversations.addAll(getConversations(currentUserID));

        return new FriendConversationView(getFriendData(friendID), getFriendData(currentUserID), messages,
                conversations);
    }

    @Override
    public boolean newConversation(int friendID, int currentUserID) {
        ArrayList<ConversationData> conversations = getConversations(currentUserID);
        FriendData friendData = getFriendData(friendID);
        ConversationData conv = new ConversationData("/avatars/"+friendID+".png",
                friendData.name, friendData.online, friendID,
                getUnreadMessagesCount(friendID, currentUserID)
        );
        conversations.add(0, conv);
        return true;
    }

    @Override
    public boolean closeConversation(int friendID, int currentUserID) {
        ArrayList<ConversationData> conversations = getConversations(currentUserID);
        return conversations.remove(new ConversationData(friendID));
    }

    private final HashMap<Integer, ArrayList<ConversationData>> conversations = new HashMap<>();

    private ArrayList<ConversationData> getConversations(int loggedID) {
        if (this.conversations.containsKey(loggedID))
            return this.conversations.get(loggedID);

        ArrayList<ConversationData> conversations = new ArrayList<>();

        if (loggedID == 23) {
            FriendData friendData = getFriendData(24);
            ConversationData conv = new ConversationData("/avatars/"+24+".png",
                    friendData.name, friendData.online, 24,
                    getUnreadMessagesCount(24, 23)
            );
            conversations.add(conv);
            friendData = getFriendData(27);
            conv = new ConversationData("/avatars/"+27+".png",
                    friendData.name, friendData.online, 27,
                    getUnreadMessagesCount(27, 23)
            );
            conversations.add(conv);
        }

        this.conversations.put(loggedID, conversations);
        return this.conversations.get(loggedID);
    }

    private int getUnreadMessagesCount(int friendID, int loggedID) {
        ArrayList<MessageData> messages = getUserMessagesWith(friendID, loggedID);
        int count = 0;
        for (MessageData d: messages) {
            if (!d.read) count++;
        }
        // System.out.println("v ("+friendID+","+loggedID+")="+count);
        return count;
    }

    private final HashMap<Point2D, ArrayList<MessageData>> messages = new HashMap<>();

    private ArrayList<MessageData> getUserMessagesWith(int friendID, int loggedID) {
        Point2D key = new Point2D(friendID, loggedID);
        if (this.messages.containsKey(key))
            return this.messages.get(key);

        ArrayList<MessageData> messages = new ArrayList<>();

        if (friendID == 24 && loggedID == 23) {
            messages.add(
                    new MessageData(
                            23,
                            "java.lang.NoSuchMethodException: com.lgs.eden.views.achievements.\nAchievements.<init>()",
                            MessageType.TEXT,
                            Date.from(Instant.now()),
                            true
                    )
            );
            messages.add(new MessageData(24, "new Achievements()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 2()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 3()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 4()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 5()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 6()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 7()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 8()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 9()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 10()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 11()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 12()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 13()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 14()", MessageType.TEXT, Date.from(Instant.now()), false));
            messages.add(new MessageData(24, "new 215()", MessageType.TEXT, Date.from(Instant.now()), false));
        }

        this.messages.put(key, messages);
        return this.messages.get(key);
    }

    // ------------------------------ UTILS ----------------------------- \\

    private FriendData getFriendData(int userID) {
        switch (userID){
            case 23: return new FriendData("/avatars/23.png", "Raphik", true, 23, FriendShipStatus.FRIENDS);
            case 24: return new FriendData("/avatars/24.png", "Raphik2", true, 24, FriendShipStatus.FRIENDS);
            case 25: return new FriendData("/avatars/25.png", "Calistral", false, 25, FriendShipStatus.FRIENDS);
            case 26: return new FriendData("/avatars/26.png", "Caliki", false, 26, FriendShipStatus.FRIENDS);
            case 27: return new FriendData("/avatars/27.png", "Raphistro", false, 27, FriendShipStatus.FRIENDS);
            case 28: return new FriendData("/avatars/27.png", "XXX", false, 28, FriendShipStatus.FRIENDS);
            case 29: return new FriendData("/avatars/25.png", "YYY", false, 29, FriendShipStatus.FRIENDS);
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
        ArrayList<FriendData> friendList = getFriendList(userID);
        return friendList.contains(new FriendData(null, null, false, loggedID, FriendShipStatus.NONE));
    }
}
