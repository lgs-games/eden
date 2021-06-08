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
    ProfileData getProfileData(int userID);

    /**
     * Returns the conversation with a friend. We have the messages
     * with this friend, and we have the list of conversation that this
     * user that with others users.
     */
    FriendConversationView getMessageWithFriend(int friendID);
}
