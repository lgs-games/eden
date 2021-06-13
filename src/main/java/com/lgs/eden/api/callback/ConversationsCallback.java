package com.lgs.eden.api.callback;

import com.lgs.eden.api.profile.friends.conversation.ConversationData;

/** called when conversation data changed **/
public interface ConversationsCallback {

    void onCall(ConversationData data);

}
