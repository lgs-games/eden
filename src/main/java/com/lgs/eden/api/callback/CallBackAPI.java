package com.lgs.eden.api.callback;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.profile.friends.FriendConversationView;

import java.util.ArrayList;

public interface CallBackAPI {

    /**
     * Returns code according to what kind
     * of notifications got fired or simply returns null
     * if none.
     */
    ArrayList<APIResponseCode> lookForNotifications(String currentUserID);

    /** @see NotificationsCallBack **/
    void setNotificationsCallBack(NotificationsCallBack callBack, String currentUserID);

    /** @see MessagesCallBack
     * @see ConversationsCallback **/
    void setMessagesCallBack(MessagesCallBack callBack, ConversationsCallback c,  FriendConversationView conv);

}
