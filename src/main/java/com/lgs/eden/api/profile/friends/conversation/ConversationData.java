package com.lgs.eden.api.profile.friends.conversation;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;

/**
 * Wrapper for a conversation
 */
public class ConversationData extends FriendData {

    public final int unreadMessagesCount;

    /**
     * Empty conversation with only an ID
     * to pass the equals method.
     */
    public ConversationData(int id) {
        super(null, null, false, id, FriendShipStatus.NONE);
        this.unreadMessagesCount = 0;
    }

    public ConversationData(String avatar, String name, boolean online, int id, int unreadMessagesCount) {
        super(avatar, name, online, id, FriendShipStatus.NONE);
        this.unreadMessagesCount = unreadMessagesCount;
    }
}
