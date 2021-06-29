package com.lgs.eden.api.local;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.games.AchievementData;
import com.lgs.eden.api.profile.*;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;
import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.api.profile.friends.messages.MessageType;
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

    private LocalHandler parent;
    public void setParent(LocalHandler parent) { this.parent = parent; }

    @Override
    public ArrayList<AchievementData> getUserAchievements(String gameID, String currentUserID) {
        ArrayList<AchievementData> d = new ArrayList<>();
        AchievementData hidden = new AchievementData("/games/hidden-achievement.png", "Hidden achievement", "???", false);
        AchievementData a = new AchievementData("/games/prim-achievement-1.png", "Hero", "You won 10 times.", true);
        d.add(a);
        d.add(hidden);
        d.add(hidden);
        d.add(hidden);
        d.add(hidden);
        return d;
    }

    ArrayList<APIResponseCode> lookForNotifications(String currentUserID) {
        init(currentUserID);
        ArrayList<APIResponseCode> r = new ArrayList<>();
        // messages
        for (FriendData d : getFriendList(currentUserID, -1)) {
            int unreadMessagesCount = getUnreadMessagesCount(d.id, currentUserID);
            if (unreadMessagesCount > 0) {
                r.add(APIResponseCode.MESSAGE_RECEIVED);
                break;
            }
        }
        // friends requests
        for (ProfileData d : users) {
            if (d.statusWithLogged.equals(FriendShipStatus.GOT_REQUESTED)) {
                r.add(APIResponseCode.FRIEND_REQUEST);
                break;
            }
        }

        if (r.isEmpty()) return null;
        return r;
    }

    private long elapsed = -1;

    @Override
    public void setPlaying(String currentUserID, String gameID) {
        if (elapsed == -1) elapsed = System.nanoTime();
        // set game
        ProfileData userProfile = getUserProfile(currentUserID);
        RecentGameData[] recentGames = userProfile.recentGames;

        int time = 0;
        if (recentGames.length > 0) time = recentGames[0].timePlayed;

        recentGames = new RecentGameData[]{
                makeEden(time + checkTime(), !gameID.equals("-1") ? -1 : 0)
        };

        // restart
        if (gameID.equals("-1")) elapsed = -1;

        userProfile.recentGames = recentGames;
    }

    private int checkTime() {
        long l = System.nanoTime();
        return Math.round((l - elapsed) / 1000000000f);
    }

    @Override
    public ArrayList<FriendData> searchUsers(String filter, String currentUserID) {
        init(currentUserID);
        ArrayList<FriendData> r = new ArrayList<>();
        if (filter.isEmpty()) return r;
        for (ProfileData d : users) {
            if (d.username.toLowerCase().contains(filter) || (d.userID + "").equals(filter)) {
                r.add(friendFromProfil(d));
            }
        }
        return r;
    }

    @Override
    public ArrayList<FriendData> getFriendList(String userID, int count) {
        if (this.users.isEmpty()) init(userID);
        if (count == -1) count = Integer.MAX_VALUE;

        ObservableList<FriendData> realFriendList = getRealFriendList(userID);
        ArrayList<FriendData> copy = new ArrayList<>();
        int i = 0;
        for (FriendData d : realFriendList) {
            if (i < count && d.friendShipStatus.equals(FriendShipStatus.FRIENDS))
                copy.add(d);
            i++;
        }
        return copy;
    }

    @Override
    public ArrayList<FriendData> getRequests(String userID, int count) {
        if (this.users.isEmpty()) init(userID);
        if (count == -1) count = Integer.MAX_VALUE;
        ArrayList<FriendData> copy = new ArrayList<>();
        for (int i = 0; i < count && i < users.size(); i++) {
            ProfileData d = users.get(i);
            if (d.statusWithLogged.equals(FriendShipStatus.REQUESTED) ||
                    d.statusWithLogged.equals(FriendShipStatus.GOT_REQUESTED))
                copy.add(friendFromProfil(d));
        }
        return copy;
    }

    @Override
    public ProfileData getProfileData(String userID, String currentUserID) {
        init(currentUserID);
        ProfileData userProfile = getUserProfile(userID);
        return new ProfileData(userProfile, FXCollections.observableArrayList(getFriendList(userID, 6)));
    }

    @Override
    public ReputationChangeData changeReputation(String userID, String currentUserID, boolean increase) {
        ProfileData p = getProfileData(userID, currentUserID);

        ReputationScore score = p.score;
        // new values
        int newRep = p.reputation;
        ReputationScore newScore = ReputationScore.NONE;
        switch (score) {
            case NONE:
                if (increase) {
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

        // remove old, add new one
        users.remove(new ProfileData(userID));
        users.add(newProfileData);

        return new ReputationChangeData(newRep, newScore);
    }

    @Override
    public void addFriend(String friendID, String currentUserID) {
        ProfileData userProfile = getUserProfile(currentUserID);

        if (!userProfile.friends.contains(new FriendData(friendID))) {
            ProfileData friendProfile = getUserProfile(friendID);
            // add to the current user friend list
            userProfile.friends.add(friendFromProfil(friendProfile, FriendShipStatus.REQUESTED));
            // update status
            users.remove(new ProfileData(friendID));
            users.add(new ProfileData(friendProfile, FriendShipStatus.REQUESTED));
        }
    }

    @Override
    public void removeFriend(String friendID, String currentUserID) {
        ProfileData friend = getUserProfile(friendID);
        ProfileData user = getUserProfile(currentUserID);

        // remove from friend list
        user.friends.remove(new FriendData(friendID));
        friend.friends.remove(new FriendData(currentUserID));

        // must reset the status of friend with user
        users.remove(new ProfileData(friendID));
        users.add(new ProfileData(friend, FriendShipStatus.NONE));
    }

    @Override
    public void acceptFriend(String friendID, String currentUserID) {
        ProfileData user = getUserProfile(currentUserID);
        ProfileData friend = getUserProfile(friendID);

        // remove from friend list
        user.friends.remove(new FriendData(friendID));
        friend.friends.remove(new FriendData(currentUserID));

        user.friends.add(friendFromProfil(friend, FriendShipStatus.FRIENDS));
        friend.friends.add(friendFromProfil(user, FriendShipStatus.FRIENDS));

        // change status
        users.remove(new ProfileData(friendID));
        users.add(new ProfileData(friend, FriendShipStatus.FRIENDS));

        // trigger
        parent.triggerNotificationCallBack(currentUserID);
    }

    @Override
    public void refuseFriend(String friendID, String currentUserID) {
        removeFriend(friendID, currentUserID);
        // trigger
        parent.triggerNotificationCallBack(currentUserID);
    }

    // ------------------------------ MESSAGES ----------------------------- \\

    private final HashMap<String, ArrayList<MessageData>> messages = new HashMap<>();
    private final HashMap<String, ConversationData> conv = new HashMap<>();

    @Override
    public FriendConversationView getMessageWithFriend(String friendID, String currentUserID) {
        init(currentUserID);

        if (friendID.equals("-1") && conv.isEmpty()) return null;

        if (conv.isEmpty() || (!friendID.equals("-1") && !conv.containsKey(friendID))) {
            newConversation(friendID, currentUserID);
        }

        ObservableList<ConversationData> allConv = FXCollections.observableArrayList(conv.values());

        if (friendID.equals("-1")) {
            friendID = allConv.get(0).id;
        }

        getMessages(friendID).forEach(m -> m.read = true);

        ConversationData conversationData = conv.get(friendID);
        conversationData.unreadMessagesCount = 0;

        this.parent.triggerNotificationCallBack(currentUserID);

        return new FriendConversationView(
                friendFromProfil(getUserProfile(friendID)),
                friendFromProfil(getUserProfile(currentUserID)),
                FXCollections.observableArrayList(getMessages(friendID)),
                allConv
        );
    }

    @Override
    public boolean newConversation(String friendID, String currentUserID) {
        if (conv.containsKey(friendID)) return true;

        ProfileData userProfile = getUserProfile(friendID);
        conv.put(userProfile.userID, new ConversationData(
                userProfile.avatarPath,
                userProfile.username,
                userProfile.online,
                userProfile.userID,
                getUnreadMessagesCount(friendID, currentUserID)
        ));

        return true;
    }

    private int getUnreadMessagesCount(String friendID, String currentUserID) {
        ArrayList<MessageData> messages = getMessages(friendID);
        int count = 0;
        for (MessageData d : messages) {
            if (!currentUserID.equals(d.senderID) && !d.read) count++;
        }
        return count;
    }

    @Override
    public boolean closeConversation(String friendID, String currentUserID) {
        if (!conv.containsKey(friendID)) return false;
        conv.remove(friendID);
        return true;
    }

    @Override
    public MessageData sendMessage(String to, String from, String message) {
        // we don't save "from" since we are in local
        ArrayList<MessageData> messages = getMessages(to);
        MessageData r = new MessageData(
                from,
                message,
                MessageType.TEXT,
                Date.from(Instant.now()),
                false
        );
        messages.add(r);

        // unread count of conversation change
        closeConversation(to, from);
        newConversation(to, from);

        return r;
    }

    @SuppressWarnings("SameParameterValue")
    void sendMessageAsOther(String current, String fake, String message) {
        ArrayList<MessageData> messages = getMessages(fake);
        MessageData r = new MessageData(
                fake,
                message,
                MessageType.TEXT,
                Date.from(Instant.now()),
                false
        );
        messages.add(r);
        closeConversation(fake, current);
        newConversation(fake, current);
        // trigger
        ConversationData conversationData = conv.get(fake);
        this.parent.triggerConversationCallBack(conversationData);
        // clear if everything got "read"
        if (conversationData.unreadMessagesCount == 0) {
            getMessages(fake).forEach(m -> m.read = true);
        }
        this.parent.triggerNotificationCallBack(current);
        this.parent.triggerMessagesCallBack(r);
    }

    private ArrayList<MessageData> getMessages(String with) {
        if (!messages.containsKey(with)) {
            messages.put(with, new ArrayList<>());
        }
        return messages.get(with);
    }

    // ------------------------------ UTILS ----------------------------- \\

    private final ArrayList<ProfileData> users = new ArrayList<>();
    private String loggedID = "-1";

    private ProfileData createFriend(String username, String id, int rep, String since,
                                     String desc, FriendShipStatus fs,
                                     ReputationScore status, RecentGameData[] games, ProfileData p,
                                     boolean online) {
        if (games == null) games = new RecentGameData[]{};

        ObservableList<FriendData> friends = FXCollections.observableArrayList();
        if (p != null) {
            friends.add(friendFromProfil(p, FriendShipStatus.FRIENDS));
        }

        return new ProfileData(username, id, "/avatars/" + id + ".png", rep,
                desc,
                new Date(), Date.from(Instant.parse(since)), friends,
                games,
                online,
                fs,
                status
        );
    }

    private FriendData friendFromProfil(ProfileData p) {
        return friendFromProfil(p, p.statusWithLogged);
    }

    private FriendData friendFromProfil(ProfileData p, FriendShipStatus status) {
        return new FriendData(p.avatarPath, p.username, p.online, p.userID, status);
    }

    private void init(String currentUserID) {
        if (!this.loggedID.equals("-1") && currentUserID.equals(this.loggedID)) return;
        loggedID = currentUserID;
        users.clear();

        ProfileData raphik = createFriend("Raphik", "23", 9999,
                "2020-12-03T10:15:30.00Z",
                "Raphik is a great programmer at ENSIIE engineering school.",
                getFriendShipStatus("23", currentUserID),
                ReputationScore.NONE, new RecentGameData[]{
                        makeEden(0, 1),
                        // new RecentGameData(Utility.loadImage("/games/enigma-icon.png"), "Enigma", 1020, 30)
                },
                null, true
        );

        ProfileData raphik2 = createFriend("Raphik2", "24", 0, "2021-03-18T10:15:30.00Z",
                "No description yet.", getFriendShipStatus("24", currentUserID),
                ReputationScore.NONE, null, raphik, false
        );

        ProfileData Calistral = createFriend("Calistral", "25", -1, "2020-12-03T10:15:30.00Z",
                "No description yet.", getFriendShipStatus("25", currentUserID),
                ReputationScore.DECREASED, null, raphik, false
        );

        ProfileData Caliki = createFriend("Caliki", "26", 0,
                "2020-12-03T10:15:30.00Z",
                "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really",
                getFriendShipStatus("26", currentUserID), ReputationScore.NONE, null, raphik, false
        );

        ProfileData Raphistro = createFriend("Raphistro", "27", 17570, "2020-03-09T10:15:30.00Z",
                "No description yet.", getFriendShipStatus("27", currentUserID), ReputationScore.INCREASED,
                null, raphik, false
        );

        users.add(raphik);
        users.add(raphik2);
        users.add(Calistral);
        users.add(Caliki);
        users.add(Raphistro);

        ProfileData xxx = createFriend("XXX", "28", 0,
                "2020-03-09T10:15:30.00Z",
                "No description yet.",
                getFriendShipStatus("28", currentUserID),
                ReputationScore.NONE, null, null, false
        );
        xxx.friends.add(friendFromProfil(raphik, FriendShipStatus.REQUESTED));

        users.add(xxx);
        users.add(createFriend("YYY", "29", 0,
                "2020-03-09T10:15:30.00Z",
                "No description yet.",
                FriendShipStatus.NONE,
                ReputationScore.NONE, null, null, false
        ));

        raphik.friends.add(friendFromProfil(raphik2, FriendShipStatus.FRIENDS));
        raphik.friends.add(friendFromProfil(Calistral, FriendShipStatus.FRIENDS));
        raphik.friends.add(friendFromProfil(Caliki, FriendShipStatus.FRIENDS));
        raphik.friends.add(friendFromProfil(Raphistro, FriendShipStatus.FRIENDS));
    }

    private RecentGameData makeEden(int timePlayed, int lastPlayed) {
        return new RecentGameData("/games/prim-icon.png", "Prim", timePlayed, lastPlayed);
    }

    private FriendShipStatus getFriendShipStatus(String userID, String currentUserID) {
        if (currentUserID.equals(userID)) return FriendShipStatus.USER;

        if (currentUserID.equals("23") && userID.equals("28")) return FriendShipStatus.GOT_REQUESTED;

        return currentUserID.equals("23") ? FriendShipStatus.FRIENDS : FriendShipStatus.NONE;
    }

    private ProfileData getUserProfile(String userID) {
        for (ProfileData d : this.users) {
            if (d.userID.equals(userID)) return d;
        }
        throw new IllegalArgumentException("not found " + userID);
    }

    private ObservableList<FriendData> getRealFriendList(String userID) {
        return getUserProfile(userID).friends;
    }

}
