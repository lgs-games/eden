package com.lgs.eden.api.callback;

public interface CallBackAPI {

    /** @see NotificationsCallBack **/
    void setNotificationsCallBack(NotificationsCallBack callBack, int currentUserID);

    /** @see MessagesCallBack **/
    void setMessagesCallBack(MessagesCallBack callBack);

}
