package com.lgs.eden.api.profile.friends.conversation;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;

/**
 * Wrapper for a conversation
 */
public class ConversationData extends FriendData {

    public final int unreadMessagesCount;

    public ConversationData(String avatar, String name, boolean online, int id, int unreadMessagesCount) {
        super(avatar, name, online, id, FriendShipStatus.NONE);
        this.unreadMessagesCount = unreadMessagesCount;
    }
}
