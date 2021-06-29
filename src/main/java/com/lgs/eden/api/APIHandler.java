package com.lgs.eden.api;

import com.lgs.eden.api.auth.AuthAPI;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.callback.ConversationsCallback;
import com.lgs.eden.api.games.*;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.news.NewsAPI;
import com.lgs.eden.api.profile.ProfileAPI;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.api.profile.ReputationChangeData;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Convenient implementation of the API
 */
public abstract class APIHandler implements API {

    protected final AuthAPI login;
    protected final GameAPI games;
    protected final ProfileAPI profile;
    protected final NewsAPI news;

    // ------------------------------ IMP ----------------------------- \\
    //
    // We are writing a bit more code than expected because
    // we want to split this class into sub classes.

    protected APIHandler(AuthAPI login, GameAPI games, ProfileAPI profile, NewsAPI news) {
        this.login = login;
        this.games = games;
        this.profile = profile;
        this.news = news;
    }

    public void init(){}
    public void close(){}

    // ------------------------------ LOGIN ----------------------------- \\

    @Override
    public LoginResponseData login(String username, String pwd) throws APIException {
        return this.login.login(username, pwd);
    }

    @Override
    public void logout(String currentUserID) throws APIException { this.login.logout(currentUserID); }

    @Override
    public APIResponseCode register(String username, String pwd, String email) throws APIException {
        return this.login.register(username, pwd, email);
    }

    @Override
    public String getPasswordForgotLink(String languageCode) {
        // website only supports en or fr
        languageCode = APIHelper.formatCode(languageCode);
        return API.WEBSITE_URL + languageCode + "/password_forgot";
    }

    // ------------------------------ GAMES ----------------------------- \\

    @Override
    public EdenVersionData getEdenVersion(String code, String os) throws APIException {
        return this.games.getEdenVersion(code, os);
    }

    @Override
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, String userID, String os) throws APIException {
        return this.games.getMarketPlaceGames(begin, count, code, userID, os);
    }

    @Override
    public GameViewData getGameData(String userID, String gameID, String lang, String os) throws APIException {
        return this.games.getGameData(userID, gameID, lang, os);
    }

    @Override
    public ObservableList<BasicGameData> getUserGames(String userID) throws APIException {
        return this.games.getUserGames(userID);
    }

    @Override
    public ShortGameViewData getGameDateUpdate(String userID, String gameID) throws APIException {
        return this.games.getGameDateUpdate(userID, gameID);
    }

    @Override
    public boolean addToLibrary(String userID, BasicGameData game) throws APIException {
        return this.games.addToLibrary(userID, game);
    }

    @Override
    public boolean removeFromLibrary(String userID, BasicGameData game) throws APIException {
        return this.games.removeFromLibrary(userID, game);
    }

    // ------------------------------ NEWS ----------------------------- \\

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String gameID, String lang, String os) throws APIException {
        return this.news.getAllNews(begin, count, gameID, lang, os);
    }

    @Override
    public BasicNewsData getNews(String newsID) throws APIException {
        return this.news.getNews(newsID);
    }

    // ------------------------------ PROFILE ----------------------------- \\

    @Override
    public ArrayList<AchievementData> getUserAchievements(String gameID, String currentUserID) {
        return this.profile.getUserAchievements(gameID, currentUserID);
    }

    @Override
    public ArrayList<FriendData> searchUsers(String filter, String currentUserID) throws APIException {
        return this.profile.searchUsers(filter, currentUserID);
    }

    @Override
    public ArrayList<FriendData> getFriendList(String currentUserID, int count) throws APIException {
        return this.profile.getFriendList(currentUserID, count);
    }

    @Override
    public ArrayList<FriendData> getRequests(String userID, int count) throws APIException {
        return this.profile.getRequests(userID, count);
    }

    @Override
    public ProfileData getProfileData(String userID, String currentUserID) throws APIException {
        return this.profile.getProfileData(userID, currentUserID);
    }

    @Override
    public ReputationChangeData changeReputation(String userID, String currentUserID, boolean increase) throws APIException {
        return this.profile.changeReputation(userID, currentUserID, increase);
    }

    @Override
    public void setPlaying(String currentUserID, String gameID) {
        this.profile.setPlaying(currentUserID, gameID);
    }

    // friends

    @Override
    public void addFriend(String friendID, String currentUserID) throws APIException {
        this.profile.addFriend(friendID, currentUserID);
    }

    @Override
    public void removeFriend(String friendID, String currentUserID) throws APIException {
        this.profile.removeFriend(friendID, currentUserID);
    }

    @Override
    public void acceptFriend(String friendID, String currentUserID) throws APIException {
        this.profile.acceptFriend(friendID, currentUserID);
    }

    @Override
    public void refuseFriend(String friendID, String currentUserID) throws APIException {
        this.profile.refuseFriend(friendID, currentUserID);
    }

    // conv

    @Override
    public FriendConversationView getMessageWithFriend(String friendID, String currentUserID) throws APIException {
        return this.profile.getMessageWithFriend(friendID, currentUserID);
    }

    @Override
    public boolean newConversation(String friendID, String currentUserID) throws APIException {
        return this.profile.newConversation(friendID, currentUserID);
    }

    @Override
    public boolean closeConversation(String friendID, String currentUserID) throws APIException {
        return this.profile.closeConversation(friendID, currentUserID);
    }

    @Override
    public MessageData sendMessage(String to, String from, String message) throws APIException {
        return this.profile.sendMessage(to, from, message);
    }
}
