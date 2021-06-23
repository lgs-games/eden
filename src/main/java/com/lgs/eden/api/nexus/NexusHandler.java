package com.lgs.eden.api.nexus;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIHandler;
import com.lgs.eden.api.callback.ConversationsCallback;
import com.lgs.eden.api.callback.MessagesCallBack;
import com.lgs.eden.api.callback.NotificationsCallBack;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;

/**
 * Handler for Nexus API
 */
public class NexusHandler extends APIHandler {

    private static API instance;
    private final Socket socket;

    public static API getInstance() {
        if (instance == null) {
            URI uri = URI.create("http://localhost:3000");
            IO.Options options = IO.Options.builder()
                    .setForceNew(false)
                    .setTimeout(-1)
                    .build();

            Socket socket = IO.socket(uri, options);
            socket.open();
            instance = new NexusHandler(socket);
        }
        return instance;
    }

    private NexusHandler(Socket socket) {
        super(new AuthImp(socket), new GameImp(socket), new ProfileImp(socket), new NewsImp(socket));
        this.socket = socket;
    }

    // ------------------------------ CALLBACKS ----------------------------- \\

    @Override
    public void setNotificationsCallBack(NotificationsCallBack callBack, int currentUserID) {

    }

    @Override
    public void setMessagesCallBack(MessagesCallBack callBack, ConversationsCallback c, FriendConversationView conv) {

    }
}
