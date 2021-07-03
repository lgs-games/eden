package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.games.AchievementData;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.nexus.helpers.RequestArray;
import com.lgs.eden.api.nexus.helpers.RequestObject;
import com.lgs.eden.api.profile.*;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;
import com.lgs.eden.api.profile.friends.conversation.ConversationData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.api.profile.friends.messages.MessageType;
import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.collections.FXCollections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;

/**
 * Nexus imp of Profile
 */
public class ProfileImp extends ImpSocket implements ProfileAPI {

    // constructor
    public ProfileImp(Socket socket) { super(socket); }

    private ArrayList<FriendData> getList(String query, Object ... args) throws APIException {
        return RequestArray.requestArray(this, this::parseFriendData, query, args);
    }

    private FriendData parseFriendData(JSONObject o) throws JSONException {
        return new FriendData(
                o.getString("avatar"),
                o.getString("name"),
                o.getBoolean("online"),
                o.getString("user_id"),
                FriendShipStatus.parse(o.getInt("status"))
        );
    }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    public ArrayList<FriendData> searchUsers(String filter, String currentUserID) throws APIException {
        return getList("search-user", filter);
    }

    @Override
    public ArrayList<FriendData> getFriendList(String userID, int count) throws APIException {
        return getList("friend-list", userID, count);
    }

    @Override
    public ArrayList<FriendData> getRequested(String userID, int count) throws APIException {
        return getList("friend-requested-list", userID, count);
    }

    @Override
    public ArrayList<FriendData> getGotRequested(String userID, int count) throws APIException {
        return getList("friend-got-requested-list", userID, count);
    }

    @Override
    public ProfileData getProfileData(String userID, String currentUserID) throws APIException {
        ProfileData profileData = RequestObject.requestObject(this, (o) -> {
            if (o.has("code")) {
                return new ProfileData(null);
            }
            // recent games
            JSONArray a = o.getJSONArray("recent_games");
            RecentGameData[] recent = new RecentGameData[a.length()];
            for (int i = 0; i < a.length() && i < 3; i++) {
                JSONObject g = (JSONObject) a.get(i);
                recent[i] = new RecentGameData(
                        g.getString("icon"),
                        g.getString("name"),
                        g.getInt("time_played") / 3600,
                        g.getLong("last_played")
                );
            }

            // friends
            a = o.getJSONArray("friends");
            ArrayList<FriendData> friends = new ArrayList<>();
            for (int i = 0; i < a.length(); i++) {
                friends.add(parseFriendData((JSONObject) a.get(i)));
            }

            return new ProfileData(
                    o.getString("username"),
                    o.getString("id_user"),
                    o.getString("avatar"),
                    o.getInt("reputation"),
                    o.getString("biography"),
                    NexusHandler.parseSQLDate(o.getString("last_seen")),
                    NexusHandler.parseSQLDate(o.getString("member_since")),
                    FXCollections.observableArrayList(friends),
                    recent,
                    o.getBoolean("online"),
                    FriendShipStatus.parse(o.getInt("status")),
                    ReputationScore.parse(o.getInt("reputation_score"))
            );
        }, "get-profile", userID);

        if (profileData.userID == null) throw new APIException(APIResponseCode.USER_ID_NOT_FOUND);
        return profileData;
    }

    @Override
    public LoginResponseData editProfile(String username, String avatar, String desc) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        try {
            // username / desc
            if (username == null) username = "";
            if (desc == null) desc = "";
            // avatar
            if (avatar != null) {
                final int UNIT = 10000;

                FileInputStream i = new FileInputStream(avatar);
                byte[] bytes = i.readAllBytes();
                String image = Base64.getEncoder().encodeToString(bytes);
                int length = image.length();
                CountDownLatch latch = new CountDownLatch((int) Math.ceil(length / (float) UNIT));

                // disconnect ?
                Emitter.Listener listener = args -> { while (latch.getCount() > 0) latch.countDown(); };
                this.socket.once(Socket.EVENT_CONNECT_ERROR, listener);
                this.socket.once(Socket.EVENT_DISCONNECT, listener);

                // job
                for (int j = 0, k = 0; j < length; j+= UNIT, k++) {
                    int upperBound = Math.min(j + UNIT, length);
                    socket.emit("load-avatar", k, image.substring(j, upperBound), (Ack) args -> latch.countDown());
                }
                // wait
                latch.await();
            }

            return RequestObject.requestObject(this,
                    (o) -> {
                        if (o.has("code")) return null;
                        return new LoginResponseData(
                                10,
                                o.getString("user_id"),
                                o.getString("username"),
                                o.getString("avatar")
                        );
                    },
                    "edit-profile", username, avatar, desc);
        } catch (IOException | InterruptedException e) {
            throw new APIException(APIResponseCode.JOB_NOT_DONE);
        }
    }

    @Override
    public ReputationChangeData changeReputation(String userID, String currentUserID, boolean increase) throws APIException {
        return RequestObject.requestObject(this,
                (o) -> new ReputationChangeData(o.getInt("reputation"), ReputationScore.parse(o.getInt("reputation_score"))),
                "reputation-set", userID, increase);
    }

    @Override
    public void addFriend(String friendID, String currentUserID) throws APIException {
        RequestObject.requestObject(this, NexusHandler::isJobDone, "add-friend", friendID);
    }

    @Override
    public void removeFriend(String friendID, String currentUserID) throws APIException {
        RequestObject.requestObject(this, NexusHandler::isJobDone, "remove-friend", friendID);
    }

    @Override
    public void acceptFriend(String friendID, String currentUserID) throws APIException {
        RequestObject.requestObject(this, NexusHandler::isJobDone, "accept-friend", friendID);
    }

    @Override
    public void refuseFriend(String friendID, String currentUserID) throws APIException {
        RequestObject.requestObject(this, NexusHandler::isJobDone, "refuse-friend", friendID);
    }

    @Override
    public void setPlaying(String currentUserID, String gameID) {
        // notify that we started (or stopped) playing
        socket.emit("playing", gameID);
    }

    @Override
    public ArrayList<AchievementData> getUserAchievements(String gameID, String currentUserID, String lang, String os) throws APIException {
        return RequestArray.requestArray(this,
                (o) -> new AchievementData(
                        o.getString("icon"), o.getString("name"), o.getString("desc"),
                        o.getBoolean("unlocked")
                ),"achievements", gameID, lang, os);
    }

    // ------------------------------ MESSAGES ----------------------------- \\

    @Override
    public FriendConversationView getMessageWithFriend(String friendID, String currentUserID) throws APIException {
        FriendConversationView r = RequestObject.requestObject(this, (o) -> {
            if (o.has("code")) return new FriendConversationView();
            return new FriendConversationView(
                    parseFriendData(o.getJSONObject("friend")),
                    parseFriendData(o.getJSONObject("user")),
                    FXCollections.observableArrayList(NexusHandler.toArrayList(
                            o.getJSONArray("messages"),
                            (m) -> new MessageData(
                                    m.getString("sender"),
                                    m.get("content"),
                                    MessageType.parse(m.getInt("type")),
                                    m.getString("date"),
                                    m.getBoolean("read")
                            )
                    )),
                    FXCollections.observableArrayList(NexusHandler.toArrayList(
                            o.getJSONArray("conversations"),
                            (c) -> new ConversationData(
                                    c.getString("avatar"),
                                    c.getString("name"),
                                    c.getBoolean("online"),
                                    c.getString("user_id"),
                                    c.getInt("unread")
                            )
                    ))
            );
        },"messages-with", friendID);

        if (r.friend() == null) return null;
        return r;
    }

    @Override
    public boolean newConversation(String friendID, String currentUserID) throws APIException {
        return RequestObject.requestObject(this, NexusHandler::isJobDone, "conv-open", friendID);
    }

    @Override
    public boolean closeConversation(String friendID, String currentUserID) throws APIException {
        return RequestObject.requestObject(this, NexusHandler::isJobDone, "conv-close", friendID);
    }

    @Override
    public void setConversationRead(String friendID, String currentUserID) {
        ConversationData r = null;
        try {
            r = RequestObject.requestObject(this, (o) -> new ConversationData(
                    o.getString("avatar"),
                    o.getString("name"),
                    o.getBoolean("online"),
                    o.getString("user_id"),
                    o.getInt("unread")
            ), "conv-read", friendID);
        } catch (APIException ignore) {}

        if (r != null) {
            ((NexusHandler)parent).getConvCallBack().onCall(r);
        }
    }

    @Override
    public MessageData sendMessage(String to, String from, String message) throws APIException {
        return RequestObject.requestObject(this,
                (m) -> new MessageData(
                        m.getString("sender"),
                        m.get("content"),
                        MessageType.parse(m.getInt("type")),
                        m.getString("date"),
                        m.getBoolean("read")
                ), "message-send", to, message);
    }
}
