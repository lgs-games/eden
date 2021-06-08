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

    public final int friendID;
    public final ObservableList<MessageData> messages;
    public final ObservableList<ConversationData> conversations;

    public FriendConversationView(int friendID, ObservableList<MessageData> messages, ObservableList<ConversationData> conversations) {
        this.friendID = friendID;
        this.messages = messages;
        this.conversations = conversations;
    }
}
