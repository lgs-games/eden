package com.lgs.eden.api.profile.friends.messages;

import java.util.Date;
import java.util.Objects;

/**
 * Wrapper for a message
 */
public class MessageData {

    public final int senderID;
    private final Object messageContent;
    public final MessageType type;
    public final Date date;
    public boolean read;

    public MessageData(int senderID, Object messageContent, MessageType type, Date date, boolean read) {
        this.senderID = senderID;
        this.messageContent = messageContent;
        this.type = type;
        this.date = date;
        this.read = read;
    }

    public String getMessageAsText(){ return messageContent.toString(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageData)) return false;

        MessageData that = (MessageData) o;

        if (senderID != that.senderID) return false;
        if (read != that.read) return false;
        if (!Objects.equals(messageContent, that.messageContent))
            return false;
        if (type != that.type) return false;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        int result = senderID;
        result = 31 * result + (messageContent != null ? messageContent.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (read ? 1 : 0);
        return result;
    }
}
