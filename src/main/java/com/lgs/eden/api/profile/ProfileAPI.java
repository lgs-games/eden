package com.lgs.eden.api.profile;

import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;

import java.util.ArrayList;

/**
 * Profile related part of the API
 */
public interface ProfileAPI {

    /** Returns complete friend list **/
    ArrayList<FriendData> getFriendList(int userID);

    /**
     * Returns profile Data for an user.
     */
    ProfileData getProfileData(int userID, int loggedID);

    /**
     * Returns the conversation with a friend. We have the messages
     * with this friend, and we have the list of conversation that this
     * user that with others users.
     *
     * Returns null if no conversations at all.
     */
    FriendConversationView getMessageWithFriend(int friendID);

    /**
     * Request (from current to user) or accept friendship (from user)
     */
    void addFriend(int userID, int currentUserID);

    /**
     * Cancel friendship
     */
    void removeFriend(int userID, int currentUserID);
}
