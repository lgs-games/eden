package com.lgs.eden.api.profile.friends.messages;

import java.util.Date;

/**
 * Wrapper for a message
 */
public class MessageData {

    public final int senderID;
    public final Object messageContent;
    public final MessageType type;
    public final Date date;
    public final boolean read;

    public MessageData(int senderID, Object messageContent, MessageType type, Date date, boolean read) {
        this.senderID = senderID;
        this.messageContent = messageContent;
        this.type = type;
        this.date = date;
        this.read = read;
    }
}
