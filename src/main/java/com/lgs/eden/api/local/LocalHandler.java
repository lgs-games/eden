package com.lgs.eden.api.local;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIHelper;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.callback.NotificationsCallBack;
import com.lgs.eden.api.games.*;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.utils.config.Language;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * API Realisation
 */
public class LocalHandler implements API {

    // ------------------------------ SINGLETON ----------------------------- \\

    private static API instance;

    public static API getInstance() {
        if (instance == null){
            instance = new LocalHandler();
        }
        return instance;
    }

    // ------------------------------ IMP ----------------------------- \\
    //
    // We are writing a bit more code than expected because
    // we want to split this class into sub classes.

    private final AuthHandler login;
    private final GamesHandler games;
    private final ProfileHandler profile;
    private final NewsHandler news;

    public LocalHandler() {
        this.login = new AuthHandler();
        this.games = new GamesHandler();
        this.profile = new ProfileHandler(this);
        this.news = new NewsHandler();
    }

    // ------------------------------ CALLBACKS ----------------------------- \\

    private NotificationsCallBack callBack;

    @Override
    public void setNotificationsCallBack(NotificationsCallBack callBack, int currentUserID) {
        this.callBack = callBack;
        triggerNotificationCallBack(currentUserID);
    }

    void triggerNotificationCallBack(int currentUserID){
        ArrayList<APIResponseCode> apiResponseCodes = this.lookForNotifications(currentUserID);
        if (callBack != null) callBack.onCall(apiResponseCodes);
    }

    // ------------------------------ LOGIN ----------------------------- \\

    private Timer checker;

    @Override
    public LoginResponseData login(String username, String pwd) {
        LoginResponseData login = this.login.login(username, pwd);
        if (login.code.equals(APIResponseCode.LOGIN_OK)){
            // starts fake message receiver
            this.checker = new Timer();
            this.checker.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // fake some delay
                    APIHelper.fakeDelay(1000);
                    // init
                    getMessageWithFriend(24, 23);
                    profile.sendMessageAsOther(23, 24, "Okay!");
                }
            }, 0, 10000);
        }
        return login;
    }

    @Override
    public void logout(int currentUserID) {
        this.checker.cancel();
        this.login.logout(currentUserID);
    }

    @Override
    public APIResponseCode register(String username, String pwd, String email) { return this.login.register(username, pwd, email);   }

    @Override
    public String getPasswordForgotLink(String languageCode) {
        return this.login.getPasswordForgotLink(languageCode);
    }

    // ------------------------------ GAMES ----------------------------- \\

    @Override
    public EdenVersionData getEdenVersion() { return this.games.getEdenVersion(); }

    @Override
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, int userID) {
        return this.games.getMarketPlaceGames(begin, count, code, userID);
    }

    @Override
    public GameViewData getGameData(int userID, int gameID) { return this.games.getGameData(userID, gameID); }

    @Override
    public ObservableList<BasicGameData> getUserGames(int userID) { return this.games.getUserGames(userID); }

    @Override
    public ShortGameViewData getGameDateUpdate(int userID, int gameID) { return this.games.getGameDateUpdate(userID, gameID); }

    @Override
    public boolean addToLibrary(int userID, BasicGameData game) { return this.games.addToLibrary(userID, game); }

    @Override
    public boolean removeFromLibrary(int userID, BasicGameData game) { return this.games.removeFromLibrary(userID, game); }

    // ------------------------------ NEWS ----------------------------- \\

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, int gameID, Language l) {
        return this.news.getAllNews(begin, count, code, gameID, l);
    }

    // ------------------------------ PROFILE ----------------------------- \\

    @Override
    public ArrayList<APIResponseCode> lookForNotifications(int currentUserID) { return this.profile.lookForNotifications(currentUserID); }

    @Override
    public ArrayList<FriendData> searchUsers(String filter, int currentUserID) { return this.profile.searchUsers(filter, currentUserID); }

    @Override
    public ArrayList<FriendData> getFriendList(int userID, int count) { return this.profile.getFriendList(userID, count); }

    @Override
    public ArrayList<FriendData> getRequests(int userID, int count) {
        return this.profile.getRequests(userID, count);
    }

    @Override
    public ProfileData getProfileData(int userID, int currentUserID) { return this.profile.getProfileData(userID, currentUserID); }

    @Override
    public ProfileData changeReputation(int userID, int currentUserID, boolean increase) {
        return this.profile.changeReputation(userID, currentUserID, increase);
    }

    // friends

    @Override
    public void addFriend(int friendID, int currentUserID) { this.profile.addFriend(friendID, currentUserID); }

    @Override
    public void removeFriend(int friendID, int currentUserID) { this.profile.removeFriend(friendID, currentUserID); }

    @Override
    public void acceptFriend(int friendID, int currentUserID) { this.profile.acceptFriend(friendID, currentUserID); }

    @Override
    public void refuseFriend(int friendID, int currentUserID) { this.profile.refuseFriend(friendID, currentUserID); }

    // conv

    @Override
    public FriendConversationView getMessageWithFriend(int friendID, int currentUserID) { return this.profile.getMessageWithFriend(friendID, currentUserID); }

    @Override
    public boolean newConversation(int friendID, int currentUserID) { return this.profile.newConversation(friendID, currentUserID); }

    @Override
    public boolean closeConversation(int friendID, int currentUserID) { return this.profile.closeConversation(friendID, currentUserID); }

    @Override
    public MessageData sendMessage(int to, int from, String message) {
        return this.profile.sendMessage(to, from, message);
    }
}
