package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.games.AchievementData;
import com.lgs.eden.api.games.BasicGameData;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.nexus.helpers.RequestArray;
import com.lgs.eden.api.nexus.helpers.RequestObject;
import com.lgs.eden.api.profile.ProfileAPI;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.api.profile.RecentGameData;
import com.lgs.eden.api.profile.ReputationScore;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.FriendShipStatus;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import io.socket.client.Socket;
import javafx.collections.FXCollections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public ArrayList<FriendData> getRequests(String userID, int count) throws APIException {
        return getList("friend-requests-list", userID, count);
    }

    @Override
    public ProfileData getProfileData(String userID, String currentUserID) throws APIException {
        return RequestObject.requestObject(this, (o) -> {
            // recent games
            JSONArray a = o.getJSONArray("recent-games");
            RecentGameData[] recent = new RecentGameData[a.length()];
            for (int i = 0; i < a.length() && i < 3; i++) {
                JSONObject g = (JSONObject) a.get(i);
                recent[i] = new RecentGameData(
                        g.getString("icon"),
                        g.getString("name"),
                        g.getInt("time_played"),
                        g.getInt("last_played")
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
                    ReputationScore.parse(o.getInt("reputation-score"))
            );
        }, "get-profile", userID);
    }

    @Override
    public ProfileData changeReputation(String userID, String currentUserID, boolean increase) {
        return null;
    }

    @Override
    public void addFriend(String friendID, String currentUserID) {

    }

    @Override
    public void removeFriend(String friendID, String currentUserID) {

    }

    @Override
    public void acceptFriend(String friendID, String currentUserID) {

    }

    @Override
    public void refuseFriend(String friendID, String currentUserID) {

    }

    @Override
    public ArrayList<APIResponseCode> lookForNotifications(String currentUserID) {
        return null;
    }

    @Override
    public void setPlaying(String currentUserID, String gameID) {

    }

    @Override
    public ArrayList<AchievementData> getUserAchievements(String gameID, String currentUserID) {
        return null;
    }

    // ------------------------------ MESSAGES ----------------------------- \\

    @Override
    public FriendConversationView getMessageWithFriend(String friendID, String currentUserID) {
        return null;
    }

    @Override
    public boolean newConversation(String friendID, String currentUserID) throws APIException {
        return RequestObject.requestObject(this, NexusHandler::isJobDone, "conv-open");
    }

    @Override
    public boolean closeConversation(String friendID, String currentUserID) throws APIException {
        return RequestObject.requestObject(this, NexusHandler::isJobDone, "conv-open");
    }

    @Override
    public MessageData sendMessage(String to, String from, String message) {
        return null;
    }
}
