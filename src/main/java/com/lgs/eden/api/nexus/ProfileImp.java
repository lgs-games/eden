package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.games.AchievementData;
import com.lgs.eden.api.profile.ProfileAPI;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import io.socket.client.Socket;

import java.util.ArrayList;

/**
 * Nexus imp of Profile
 */
public class ProfileImp extends ImpSocket implements ProfileAPI {

    // constructor
    public ProfileImp(Socket socket) { super(socket); }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    public ArrayList<FriendData> searchUsers(String filter, String currentUserID) {
        return null;
    }

    @Override
    public ArrayList<FriendData> getFriendList(String userID, int count) {
        return null;
    }

    @Override
    public ArrayList<FriendData> getRequests(String userID, int count) {
        return null;
    }

    @Override
    public ProfileData getProfileData(String userID, String currentUserID) {
        return null;
    }

    @Override
    public ProfileData changeReputation(String userID, String currentUserID, boolean increase) {
        return null;
    }

    @Override
    public void addFriend(String friendID, String currentUserID) {

    }

    @Override
    public void removeFriend(String friendID, String currentUserID) {

    }

    @Override
    public void acceptFriend(String friendID, String currentUserID) {

    }

    @Override
    public void refuseFriend(String friendID, String currentUserID) {

    }

    @Override
    public FriendConversationView getMessageWithFriend(String friendID, String currentUserID) {
        return null;
    }

    @Override
    public boolean newConversation(String friendID, String currentUserID) {
        return false;
    }

    @Override
    public boolean closeConversation(String friendID, String currentUserID) {
        return false;
    }

    @Override
    public MessageData sendMessage(String to, String from, String message) {
        return null;
    }

    @Override
    public ArrayList<APIResponseCode> lookForNotifications(String currentUserID) {
        return null;
    }

    @Override
    public void setPlaying(String currentUserID, String gameID) {

    }

    @Override
    public ArrayList<AchievementData> getUserAchievements(String gameID, String currentUserID) {
        return null;
    }
}
