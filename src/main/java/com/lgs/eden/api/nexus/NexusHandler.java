package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIHandler;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.callback.ConversationsCallback;
import com.lgs.eden.api.callback.MessagesCallBack;
import com.lgs.eden.api.callback.NotificationsCallBack;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Handler for Nexus API
 */
public class NexusHandler extends APIHandler {

    private static final AtomicReference<String> oldID = new AtomicReference<>();
    private static APIHandler instance;
    private final Socket socket;

    public static APIHandler getInstance() {
        if (instance == null) {
            URI uri = URI.create("http://localhost:3000");
            // URI uri = URI.create("https://lgs-games.com:3000/");
            IO.Options options = IO.Options.builder()
                    .setForceNew(false)
                    .setTimeout(-1)
                    // todo: we may add that
                    // .setReconnectionDelayMax(xxxx)
                    .build();

            Socket socket = IO.socket(uri, options);
            socket.open();
             socket.on(Socket.EVENT_CONNECT, args -> {
                synchronized (oldID){
                    String id = oldID.get();
                    if (id != null){ // first
                        socket.emit("resume", id, (Ack) args1 -> {
                            oldID.set(socket.id()+"");
                        });
                    } else {
                        oldID.set(socket.id()+"");
                    }
                }
             });
            instance = new NexusHandler(socket);
        }
        return instance;
    }

    private NexusHandler(Socket socket) {
        super(new AuthImp(socket), new GameImp(socket), new ProfileImp(socket), new NewsImp(socket));
        this.socket = socket;

        // set parent
        ((ImpSocket)this.games).setParent(this);
    }

    @Override
    public void close() {
        super.close();

        socket.close();
        socket.disconnect();
    }

    // ------------------------------ UTILS ----------------------------- \\

    BasicNewsData parseNews(JSONObject news) throws JSONException, ParseException {
        return ((NewsImp)this.news).parseNews(news);
    }

    // ------------------------------ CALLBACKS ----------------------------- \\

    @Override
    public void setNotificationsCallBack(NotificationsCallBack callBack, String currentUserID) {

    }

    @Override
    public void setMessagesCallBack(MessagesCallBack callBack, ConversationsCallback c, FriendConversationView conv) {

    }

    // ------------------------------ HELP ----------------------------- \\

    public static ArrayList<String> toArrayList(JSONArray array) throws JSONException {
        ArrayList<String> r = new ArrayList<>();
        int length = array.length();
        for (int i = 0; i < length; i++) {
            r.add((String) array.get(i));
        }
        return r;
    }

    /**
     * Raise Exception is SERVER_UNREACHABLE
     */
    public static void checkNetwork(ImpSocket imp) throws APIException {
        // no connection
        if (imp.notConnected()){
            // check a bit more times
            int cumule = 0;
            while (cumule < 2000){
                try {
                    Thread.sleep(100);
                    cumule += 100;
                } catch (InterruptedException ignored){}

                try {
                    // check again
                    if (imp.notConnected()){ throw new APIException(APIResponseCode.SERVER_UNREACHABLE); }
                    return;
                } catch (APIException ignore){}
            }
            throw new APIException(APIResponseCode.SERVER_UNREACHABLE);
        }
    }

    /**
     * Convenience method to check for JOB_DONE response code
     */
    public static Boolean isJobDone(JSONObject o) {
        try {
            return APIResponseCode.fromCode(o.getInt("code")).equals(APIResponseCode.JOB_DONE);
        } catch (JSONException e) {
            return false;
        }
    }
}
