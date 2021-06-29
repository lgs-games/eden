package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.callback.CallBackAPI;
import com.lgs.eden.api.callback.ConversationsCallback;
import com.lgs.eden.api.callback.MessagesCallBack;
import com.lgs.eden.api.callback.NotificationsCallBack;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.api.profile.friends.messages.MessageType;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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
    public ArrayList<APIResponseCode> lookForNotifications(String currentUserID) {
        // tell server we are ready
        socket.emit("new-event");
        return null;
    }

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
            if (list.contains(APIResponseCode.NO_NOTIFICATIONS)){
                callBack.onCall(null);
            } else {
                callBack.onCall(list);
            }
        });

        lookForNotifications(currentUserID);
    }

    @Override
    public void setMessagesCallBack(MessagesCallBack mCallBack, ConversationsCallback convCallBack, FriendConversationView ignored) {
        ((NexusHandler)parent).saveConversationCallback(convCallBack);
        socket.on("message-received", args -> {
            if (mCallBack == null) return;
            if (convCallBack == null) return;
            if (! (args[0] instanceof JSONObject o)) return;

            try {
                JSONObject conv = o.getJSONObject("new_conv");
                JSONObject m = o.getJSONObject("message");

                mCallBack.onCall(new MessageData(
                        m.getString("sender"),
                        m.get("message"),
                        MessageType.parse(m.getInt("type")),
                        NexusHandler.parseSQLDate(m.getString("date")),
                        m.getBoolean("read")
                ));
                convCallBack.onCall(new ConversationData(
                        conv.getString("avatar"),
                        conv.getString("name"),
                        conv.getBoolean("online"),
                        conv.getString("user_id"),
                        conv.getInt("unread")
                ));
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        });
    }
}
