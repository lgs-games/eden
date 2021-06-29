package com.lgs.eden.api.local;

import com.lgs.eden.api.*;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.callback.ConversationsCallback;
import com.lgs.eden.api.callback.MessagesCallBack;
import com.lgs.eden.api.callback.NotificationsCallBack;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * API Realisation
 */
public class LocalHandler extends APIHandler {

    // ------------------------------ SINGLETON ----------------------------- \\

    private static APIHandler instance;

    public static APIHandler getInstance() {
        if (instance == null) {
            instance = new LocalHandler();
        }
        return instance;
    }


    private LocalHandler() {
        super(new AuthHandler(), new GamesHandler(), new ProfileHandler(), new NewsHandler());
        ((ProfileHandler)this.profile).setParent(this);
    }

    // ------------------------------ CALLBACKS ----------------------------- \\

    private NotificationsCallBack newsCallback;
    private MessagesCallBack messagesCallBack;
    private ConversationsCallback conversationsCallback;
    private FriendConversationView conv;

    @Override
    public void setNotificationsCallBack(NotificationsCallBack callBack, String currentUserID) {
        this.newsCallback = callBack;
        triggerNotificationCallBack(currentUserID);
    }

    void triggerNotificationCallBack(String currentUserID) {
        ArrayList<APIResponseCode> apiResponseCodes = this.lookForNotifications(currentUserID);
        if (newsCallback != null) newsCallback.onCall(apiResponseCodes);
    }

    @Override
    public void setMessagesCallBack(MessagesCallBack mc, ConversationsCallback cc, FriendConversationView conv) {
        this.messagesCallBack = mc;
        this.conversationsCallback = cc;
        this.conv = conv;
    }

    void triggerMessagesCallBack(MessageData m) {
        if (messagesCallBack != null) {
            // friend sent a message
            if (m.senderID.equals(conv.friend().id)) messagesCallBack.onCall(m);
        }
    }

    void triggerConversationCallBack(ConversationData m) {
        if (conversationsCallback != null) {
            if (m.id.equals(conv.friend().id)) m.unreadMessagesCount = 0;
            // friend sent a message
            conversationsCallback.onCall(m);
        }
    }

    // ------------------------------ LOGIN ----------------------------- \\

    private Timer checker;

    @Override
    public LoginResponseData login(String username, String pwd) throws APIException {
        LoginResponseData login = this.login.login(username, pwd);
        if (login.code.equals(APIResponseCode.LOGIN_OK)) {

            // init
            getMessageWithFriend("24", "23");
            getMessageWithFriend("25", "23");

            // starts fake message receiver
            this.checker = new Timer();
            this.checker.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // fake some delay
                    APIHelper.fakeDelay(1000);
                    ((ProfileHandler) profile).sendMessageAsOther("23", "24", "Okay!");
                    ((ProfileHandler) profile).sendMessageAsOther("23", "25", "Salut");
                }
            }, 0, 10000);
        }
        return login;
    }

    @Override
    public void logout(String currentUserID)  throws APIException {
        this.checker.cancel();
        this.login.logout(currentUserID);
    }

}
