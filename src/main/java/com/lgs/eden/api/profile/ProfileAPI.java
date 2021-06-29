package com.lgs.eden.api.profile;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.games.AchievementData;
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
    ArrayList<FriendData> searchUsers(String filter, String currentUserID) throws APIException;

    /** Returns complete friend list **/
    ArrayList<FriendData> getFriendList(String userID, int count) throws APIException;

    /** friend request received or sent **/
    ArrayList<FriendData> getRequests(String userID, int count) throws APIException;

    /**
     * Returns profile Data for an user.
     */
    ProfileData getProfileData(String userID, String currentUserID) throws APIException;

    /**
     * Change reputation value from currentUserID to
     * userID. Increase up to +1 and decease up to -1.
     *
     * Return CHANGE_REPUTATION_OK or CHANGE_REPUTATION_KO
     */
    ReputationChangeData changeReputation(String userID, String currentUserID, boolean increase) throws APIException;

    // ------------------------------ FRIENDS ----------------------------- \\

    /**
     * Request (from current to user) or accept friendship (from user)
     */
    void addFriend(String friendID, String currentUserID) throws APIException;

    /**
     * Cancel friendship
     */
    void removeFriend(String friendID, String currentUserID) throws APIException;

    /**
     * Accept friend request
     */
    void acceptFriend(String friendID, String currentUserID) throws APIException;

    /**
     * Cancel friend request or refuse
     * friend request.
     */
    void refuseFriend(String friendID, String currentUserID) throws APIException;

    // ------------------------------ CONVERSATIONS ----------------------------- \\

    /**
     * Returns the conversation with a friend. We have the messages
     * with this friend, and we have the list of conversation that this
     * user that with others users.
     *
     * Returns null if no conversations at all.
     */
    FriendConversationView getMessageWithFriend(String friendID, String currentUserID) throws APIException;

    /**
     * Create a new conversation in the list of conversation of this user.
     */
    boolean newConversation(String friendID, String currentUserID) throws APIException;

    /**
     * Set conversation as read
     */
    default void setConversationRead(String friendID, String currentUserID) throws APIException {}

    /**
     * Close (don't delete message but don't show again) a conversation
     * in the list of conversation of this user.
     */
    boolean closeConversation(String friendID, String currentUserID) throws APIException;

    /**
     * Send a message to another user.
     */
    MessageData sendMessage(String to, String from, String message) throws APIException;

    /**
     * Set this game as played by the user
     */
    void setPlaying(String currentUserID, String gameID);

    /**
     * Return user achievements for a game
     */
    ArrayList<AchievementData> getUserAchievements(String gameID, String currentUserID);
}
