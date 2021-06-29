package com.lgs.eden.api.profile.friends.conversation;

import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;

/**
 * Wrapper for a conversation
 */
public class ConversationData extends FriendData {

    public int unreadMessagesCount;

    public ConversationData(String avatar, String name, boolean online, String id, int unreadMessagesCount) {
        super(avatar, name, online, id, FriendShipStatus.NONE);
        this.unreadMessagesCount = unreadMessagesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConversationData that)) return false;
        if (!super.equals(o)) return false;
        return unreadMessagesCount == that.unreadMessagesCount;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + unreadMessagesCount;
        return result;
    }

    @Override
    public String toString() {
        return "ConversationData{" +
                "name='" + name + '\'' +
                ", online=" + online +
                ", id=" + id +
                ", unreadMessagesCount=" + unreadMessagesCount +
                '}';
    }
}
