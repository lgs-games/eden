package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.callback.CallBackAPI;
import com.lgs.eden.api.callback.ConversationsCallback;
import com.lgs.eden.api.callback.MessagesCallBack;
import com.lgs.eden.api.callback.NotificationsCallBack;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Nexus imp of CallBack
 */
public class CallBackImp extends ImpSocket implements CallBackAPI {

    // constructor
    public CallBackImp(Socket socket) {
        super(socket);
    }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    public void setNotificationsCallBack(NotificationsCallBack callBack, String currentUserID) {
        socket.on("new-event", args -> {
            if (callBack == null) return;
            // parse result
            ArrayList<APIResponseCode> list = new ArrayList<>();
            for (Object o : args) {
                if (o instanceof Integer){
                    try {
                        list.add(APIResponseCode.fromCode((int) o));
                    } catch (NoSuchElementException ignore){}
                }
            }
            // fire callback
            callBack.onCall(list);
        });
    }

    @Override
    public void setMessagesCallBack(MessagesCallBack callBack, ConversationsCallback c, FriendConversationView conv) {

    }
}
