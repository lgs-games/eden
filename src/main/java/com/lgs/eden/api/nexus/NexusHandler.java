package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIHandler;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.callback.ConversationsCallback;
import com.lgs.eden.api.callback.MessagesCallBack;
import com.lgs.eden.api.callback.NotificationsCallBack;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Handler for Nexus API
 */
public class NexusHandler extends APIHandler {

    private static APIHandler instance;
    private final Socket socket;

    public static APIHandler getInstance() {
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
        if (!imp.socket.connected()){ throw new APIException(APIResponseCode.SERVER_UNREACHABLE); }
    }

    /**
     * Convenience method to check for JOB_DONE response code
     */
    public static Boolean isJobDone(Object[] args) {
        boolean rep = false;

        if (args.length > 0 && args[0] instanceof JSONObject) {
            try {
                JSONObject o = (JSONObject) args[0];
                rep = APIResponseCode.fromCode(o.getInt("code")).equals(APIResponseCode.JOB_DONE);
            } catch (JSONException e) {
                rep = false;
            }
        }
        return rep;
    }
}
