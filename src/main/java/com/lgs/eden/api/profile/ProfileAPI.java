package com.lgs.eden.api.profile;

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

}
