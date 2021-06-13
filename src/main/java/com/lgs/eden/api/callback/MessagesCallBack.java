package com.lgs.eden.api.callback;

import com.lgs.eden.api.profile.friends.messages.MessageData;

/** called when message is received **/
public interface MessagesCallBack {

    void onCall(MessageData data);

}
