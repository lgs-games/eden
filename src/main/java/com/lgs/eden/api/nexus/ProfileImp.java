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
    public ArrayList<FriendData> searchUsers(String filter, int currentUserID) {
        return null;
    }

    @Override
    public ArrayList<FriendData> getFriendList(int userID, int count) {
        return null;
    }

    @Override
    public ArrayList<FriendData> getRequests(int userID, int count) {
        return null;
    }

    @Override
    public ProfileData getProfileData(int userID, int currentUserID) {
        return null;
    }

    @Override
    public ProfileData changeReputation(int userID, int currentUserID, boolean increase) {
        return null;
    }

    @Override
    public void addFriend(int friendID, int currentUserID) {

    }

    @Override
    public void removeFriend(int friendID, int currentUserID) {

    }

    @Override
    public void acceptFriend(int friendID, int currentUserID) {

    }

    @Override
    public void refuseFriend(int friendID, int currentUserID) {

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

    @Override
    public MessageData sendMessage(int to, int from, String message) {
        return null;
    }

    @Override
    public ArrayList<APIResponseCode> lookForNotifications(int currentUserID) {
        return null;
    }

    @Override
    public void setPlaying(int currentUserID, int gameID) {

    }

    @Override
    public ArrayList<AchievementData> getUserAchievements(int gameID, int currentUserID) {
        return null;
    }
}
