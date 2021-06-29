package com.lgs.eden.api.profile.friends;

import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import javafx.collections.ObservableList;

/**
 * Wrapper for the message view.
 * We are fetching the message with the user requested
 * and some basic information about the other conversations.
 */
public record FriendConversationView(FriendData friend,
                                     FriendData user,
                                     ObservableList<MessageData> messages,
                                     ObservableList<ConversationData> conversations) {

    @Override
    public String toString() {
        return "FriendConversationView{" +
                "friend=" + friend +
                ", user=" + user +
                ", messages=" + messages +
                ", conversations=" + conversations +
                '}';
    }
}
