package com.lgs.eden.api.profile.friends;

import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import javafx.collections.ObservableList;

/**
 * Wrapper for the message view.
 * We are fetching the message with the user requested
 * and some basic information about the other conversations.
 */
public class FriendConversationView {

    public final FriendData friend;
    public final FriendData user;
    public final ObservableList<MessageData> messages;
    public final ObservableList<ConversationData> conversations;

    public FriendConversationView(FriendData friend, FriendData user,
                                  ObservableList<MessageData> messages, ObservableList<ConversationData> conversations) {
        this.friend = friend;
        this.user = user;
        this.messages = messages;
        this.conversations = conversations;
    }
}
