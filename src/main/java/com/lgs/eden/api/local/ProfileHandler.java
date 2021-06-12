package com.lgs.eden.api.local;

import com.lgs.eden.api.profile.RecentGameData;
import com.lgs.eden.api.profile.ReputationScore;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.ProfileAPI;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;
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
    public ArrayList<FriendData> searchUsers(String filter, int currentUserID) {
        init(currentUserID);
        ArrayList<FriendData> r = new ArrayList<>();
        if (filter.isEmpty()) return r;
        for (ProfileData d: users) {
            if (d.username.toLowerCase().contains(filter) || (d.userID+"").equals(filter)){
                r.add(friendFromProfil(d));
            }
        }
        return r;
    }

    @Override
    public ArrayList<FriendData> getFriendList(int userID) {
        if (this.users.isEmpty()) init(userID);

        ObservableList<FriendData> realFriendList = getRealFriendList(userID);
        ArrayList<FriendData> copy = new ArrayList<>();
        for (FriendData d: realFriendList) {
            if(d.friendShipStatus.equals(FriendShipStatus.FRIENDS))
                copy.add(d);
        }
        return copy;
    }

    @Override
    public ProfileData getProfileData(int userID, int currentUserID) {
        init(currentUserID);
        ProfileData userProfile = getUserProfile(userID);
        return new ProfileData(userProfile, FXCollections.observableArrayList(getFriendList(userID)));
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

        // remove old, add new one
        users.remove(new ProfileData(userID));
        users.add(newProfileData);

        return newProfileData;
    }

    @Override
    public void addFriend(int friendID, int currentUserID) {
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
    public void removeFriend(int friendID, int currentUserID) {
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
    public void acceptFriend(int friendID, int currentUserID) {
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
    }

    @Override
    public void refuseFriend(int friendID, int currentUserID) {
        removeFriend(friendID, currentUserID);
    }

    public void initConversations(){

    }

    @Override
    public FriendConversationView getMessageWithFriend(int friendID, int currentUserID) {
        return null;
    }

    @Override
    public boolean newConversation(int friendID, int currentUserID) {
        return false;
    }

    @Override
    public boolean closeConversation(int friendID, int currentUserID) {
        return false;
    }

    // ------------------------------ UTILS ----------------------------- \\

    private final ArrayList<ProfileData> users = new ArrayList<>();
    private int loggedID = -1;

    private ProfileData createFriend(String username, int id, int rep, String since, int nf,
                                     String desc, FriendShipStatus fs,
                                     ReputationScore status, RecentGameData[] games, ProfileData p) {
        if (games == null) games = new RecentGameData[]{};

        ObservableList<FriendData> friends = FXCollections.observableArrayList();
        if (p != null){
            friends.add(friendFromProfil(p, FriendShipStatus.FRIENDS));
        }

        return  new ProfileData(username, id, "/avatars/"+id+".png",
                nf, rep,
                desc,
                new Date(), Date.from(Instant.parse(since)), friends,
                games,
                fs,
                status
        );
    }

    private FriendData friendFromProfil(ProfileData p) {
        return friendFromProfil(p, p.statusWithLogged);
    }

    private FriendData friendFromProfil(ProfileData p, FriendShipStatus status) {
        return new FriendData(p.avatarPath, p.username, false, p.userID, status);
    }

    private void init(int currentUserID){
        if (this.loggedID != -1 && currentUserID == this.loggedID) return;
        loggedID = currentUserID;
        users.clear();

        ProfileData raphik = createFriend("Raphik", 23, 9999,
                "2020-12-03T10:15:30.00Z", 4,
                "Raphiki is a great programmer at ENSIIE engineering school.",
                getFriendShipStatus(23, currentUserID),
                ReputationScore.NONE, new RecentGameData[]{
                        new RecentGameData(Utility.loadImage("/games/prim-icon.png"), "Prim", 0, 1),
                        // new RecentGameData(Utility.loadImage("/games/enigma-icon.png"), "Enigma", 1020, 30)
                },
                null
        );

        ProfileData raphik2 = createFriend("Raphik2", 24, 0, "2021-03-18T10:15:30.00Z", 1,
                "No description yet.", getFriendShipStatus(24, currentUserID),
                ReputationScore.NONE, null, raphik
        );

        ProfileData calistral = createFriend("Calistral", 25, -1, "2020-12-03T10:15:30.00Z", 1,
                "No description yet.", getFriendShipStatus(25, currentUserID),
                ReputationScore.DECREASED, null, raphik
        );

        ProfileData caliki = createFriend("Caliki", 26, 0,
                "2020-12-03T10:15:30.00Z", 1,
                "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really"
                        + "This is a really" + "This is a really" + "This is a really" + "This is a really" + "This is a really",
                getFriendShipStatus(26, currentUserID), ReputationScore.NONE, null, raphik
        );

        ProfileData raphistro = createFriend("Raphistro", 27, 17570, "2020-03-09T10:15:30.00Z", 1,
                "No description yet.", getFriendShipStatus(27, currentUserID), ReputationScore.INCREASED, null, raphik
        );

        users.add(raphik);
        users.add(raphik2);
        users.add(calistral);
        users.add(caliki);
        users.add(raphistro);

        ProfileData xxx = createFriend("XXX", 28, 0,
                "2020-03-09T10:15:30.00Z", 1,
                "No description yet.",
                getFriendShipStatus(28, currentUserID),
                ReputationScore.NONE, null, null
        );
        xxx.friends.add(friendFromProfil(raphik, FriendShipStatus.REQUESTED));

        users.add(xxx);
        users.add(createFriend("YYY", 29, 0,
                "2020-03-09T10:15:30.00Z", 0,
                "No description yet.",
                FriendShipStatus.NONE,
                ReputationScore.NONE, null, null
        ));

        raphik.friends.add(friendFromProfil(raphik2, FriendShipStatus.FRIENDS));
        raphik.friends.add(friendFromProfil(calistral, FriendShipStatus.FRIENDS));
        raphik.friends.add(friendFromProfil(caliki, FriendShipStatus.FRIENDS));
        raphik.friends.add(friendFromProfil(raphistro, FriendShipStatus.FRIENDS));
    }

    private FriendShipStatus getFriendShipStatus(int userID, int currentUserID) {
        if (currentUserID == userID) return FriendShipStatus.USER;

        if (currentUserID == 23 && userID == 28) return FriendShipStatus.GOT_REQUESTED;

        return currentUserID == 23 ? FriendShipStatus.FRIENDS : FriendShipStatus.NONE;
    }

    private ProfileData getUserProfile(int userID) {
        for (ProfileData d: this.users) {
            if (d.userID == userID) return d;
        }
        throw new IllegalArgumentException("not found");
    }

    private ObservableList<FriendData> getRealFriendList(int userID) {
        return getUserProfile(userID).friends;
    }

}
