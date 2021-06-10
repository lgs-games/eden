package com.lgs.eden.api.profile;

import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;

import java.util.ArrayList;

/**
 * Profile related part of the API
 */
public interface ProfileAPI {

    /** Returns complete friend list **/
    ArrayList<FriendData> getFriendList(int userID, int currentUserID);

    /**
     * Returns profile Data for an user.
     */
    ProfileData getProfileData(int userID, int currentUserID);

    /**
     * Returns the conversation with a friend. We have the messages
     * with this friend, and we have the list of conversation that this
     * user that with others users.
     *
     * Returns null if no conversations at all.
     */
    FriendConversationView getMessageWithFriend(int friendID, int currentUserID);

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

    /**
     * Create a new conversation in the list of conversation of this user.
     */
    boolean newConversation(int friendID, int currentUserID);

    /**
     * Close (don't delete message but don't show again) a conversation
     * in the list of conversation of this user.
     */
    boolean closeConversation(int friendID, int currentUserID);
}
