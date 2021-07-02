package com.lgs.eden.api.profile.friends.messages;

import java.util.Objects;

/**
 * Wrapper for a message
 */
public class MessageData {

    public final String senderID;
    private final Object messageContent;
    public final MessageType type;
    public final String date;
    public boolean read;

    public MessageData(String senderID, Object messageContent, MessageType type, String date, boolean read) {
        this.senderID = senderID;
        this.messageContent = messageContent;
        this.type = type;
        this.date = date;
        this.read = read;
    }

    public String getMessageAsText(){ return messageContent.toString(); }

    @Override
    public String toString() {
        return "MessageData{" +
                "senderID=" + senderID +
                ", messageContent=" + getMessageAsText() +
                ", date=" + date +
                ", read=" + read +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageData that)) return false;
        if (!senderID.equals(that.senderID)) return false;
        if (read != that.read) return false;
        if (!Objects.equals(messageContent, that.messageContent))
            return false;
        if (type != that.type) return false;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        int result = senderID != null ? senderID.hashCode() : 0;
        result = 31 * result + (messageContent != null ? messageContent.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (read ? 1 : 0);
        return result;
    }
}
