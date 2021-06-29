package com.lgs.eden.api.profile.friends.messages;

/**
 * Types of a message.
 * @see com.lgs.eden.views.profile.messages.Messages : the view that only handle text for now
 */
public enum MessageType {
    TEXT,
    ; // only text for now

    public static MessageType parse(int type) {
        return TEXT;
    }
}
