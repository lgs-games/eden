package com.lgs.eden.api.profile;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.messages.MessageData;

import java.util.ArrayList;

/**
 * Profile related part of the API
 */
public interface ProfileAPI {

    /**
     * Returns a list of users from a filter.
     */
    ArrayList<FriendData> searchUsers(String filter, int currentUserID);

    /** Returns complete friend list **/
    ArrayList<FriendData> getFriendList(int userID, int count);

    /** friend request received or sent **/
    ArrayList<FriendData> getRequests(int userID, int count);

    /**
     * Returns profile Data for an user.
     */
    ProfileData getProfileData(int userID, int currentUserID);

    /**
     * Change reputation value from currentUserID to
     * userID. Increase up to +1 and decease up to -1.
     *
     * Return CHANGE_REPUTATION_OK or CHANGE_REPUTATION_KO
     */
    ProfileData changeReputation(int userID, int currentUserID, boolean increase);

    // ------------------------------ FRIENDS ----------------------------- \\

    /**
     * Request (from current to user) or accept friendship (from user)
     */
    void addFriend(int friendID, int currentUserID);

    /**
     * Cancel friendship
     */
    void removeFriend(int friendID, int currentUserID);

    /**
     * Accept friend request
     */
    void acceptFriend(int friendID, int currentUserID);

    /**
     * Cancel friend request or refuse
     * friend request.
     */
    void refuseFriend(int friendID, int currentUserID);

    // ------------------------------ CONVERSATIONS ----------------------------- \\

    /**
     * Returns the conversation with a friend. We have the messages
     * with this friend, and we have the list of conversation that this
     * user that with others users.
     *
     * Returns null if no conversations at all.
     */
    FriendConversationView getMessageWithFriend(int friendID, int currentUserID);

    /**
     * Create a new conversation in the list of conversation of this user.
     */
    boolean newConversation(int friendID, int currentUserID);

    /**
     * Close (don't delete message but don't show again) a conversation
     * in the list of conversation of this user.
     */
    boolean closeConversation(int friendID, int currentUserID);

    /**
     * Send a message to another user.
     */
    MessageData sendMessage(int to, int from, String message);

    /**
     * Returns code according to what kind
     * of notifications got fired or simply returns null
     * if none.
     */
    ArrayList<APIResponseCode> lookForNotifications(int currentUserID);

    /**
     * Set this game as played by the user
     */
    void setPlaying(int currentUserID, int gameID);
}
