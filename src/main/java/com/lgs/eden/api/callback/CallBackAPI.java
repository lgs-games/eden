package com.lgs.eden.api.callback;

import com.lgs.eden.api.profile.friends.FriendConversationView;

public interface CallBackAPI {

    /** @see NotificationsCallBack **/
    void setNotificationsCallBack(NotificationsCallBack callBack, int currentUserID);

    /** @see MessagesCallBack
     * @see ConversationsCallback **/
    void setMessagesCallBack(MessagesCallBack callBack, ConversationsCallback c,  FriendConversationView conv);

}
