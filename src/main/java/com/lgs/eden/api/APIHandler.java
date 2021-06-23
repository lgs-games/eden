package com.lgs.eden.api;

import com.lgs.eden.api.auth.AuthAPI;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.games.*;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.news.NewsAPI;
import com.lgs.eden.api.profile.ProfileAPI;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.friends.messages.MessageData;
import com.lgs.eden.utils.config.Language;
import javafx.collections.ObservableList;

import java.util.ArrayList;

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
    public void logout(int currentUserID) throws APIException { this.login.logout(currentUserID); }

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
    public EdenVersionData getEdenVersion() throws APIException {
        return this.games.getEdenVersion();
    }

    @Override
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, int userID) {
        return this.games.getMarketPlaceGames(begin, count, code, userID);
    }

    @Override
    public GameViewData getGameData(int userID, int gameID) {
        return this.games.getGameData(userID, gameID);
    }

    @Override
    public ObservableList<BasicGameData> getUserGames(int userID) {
        return this.games.getUserGames(userID);
    }

    @Override
    public ShortGameViewData getGameDateUpdate(int userID, int gameID) {
        return this.games.getGameDateUpdate(userID, gameID);
    }

    @Override
    public boolean addToLibrary(int userID, BasicGameData game) {
        return this.games.addToLibrary(userID, game);
    }

    @Override
    public boolean removeFromLibrary(int userID, BasicGameData game) {
        return this.games.removeFromLibrary(userID, game);
    }

    // ------------------------------ NEWS ----------------------------- \\

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, int gameID, Language l) {
        return this.news.getAllNews(begin, count, code, gameID, l);
    }

    // ------------------------------ PROFILE ----------------------------- \\

    @Override
    public ArrayList<APIResponseCode> lookForNotifications(int currentUserID) {
        return this.profile.lookForNotifications(currentUserID);
    }

    @Override
    public ArrayList<AchievementData> getUserAchievements(int gameID, int currentUserID) {
        return this.profile.getUserAchievements(gameID, currentUserID);
    }

    @Override
    public ArrayList<FriendData> searchUsers(String filter, int currentUserID) {
        return this.profile.searchUsers(filter, currentUserID);
    }

    @Override
    public ArrayList<FriendData> getFriendList(int userID, int count) {
        return this.profile.getFriendList(userID, count);
    }

    @Override
    public ArrayList<FriendData> getRequests(int userID, int count) {
        return this.profile.getRequests(userID, count);
    }

    @Override
    public ProfileData getProfileData(int userID, int currentUserID) {
        return this.profile.getProfileData(userID, currentUserID);
    }

    @Override
    public ProfileData changeReputation(int userID, int currentUserID, boolean increase) {
        return this.profile.changeReputation(userID, currentUserID, increase);
    }

    @Override
    public void setPlaying(int currentUserID, int gameID) {
        this.profile.setPlaying(currentUserID, gameID);
    }

    // friends

    @Override
    public void addFriend(int friendID, int currentUserID) {
        this.profile.addFriend(friendID, currentUserID);
    }

    @Override
    public void removeFriend(int friendID, int currentUserID) {
        this.profile.removeFriend(friendID, currentUserID);
    }

    @Override
    public void acceptFriend(int friendID, int currentUserID) {
        this.profile.acceptFriend(friendID, currentUserID);
    }

    @Override
    public void refuseFriend(int friendID, int currentUserID) {
        this.profile.refuseFriend(friendID, currentUserID);
    }

    // conv

    @Override
    public FriendConversationView getMessageWithFriend(int friendID, int currentUserID) {
        return this.profile.getMessageWithFriend(friendID, currentUserID);
    }

    @Override
    public boolean newConversation(int friendID, int currentUserID) {
        return this.profile.newConversation(friendID, currentUserID);
    }

    @Override
    public boolean closeConversation(int friendID, int currentUserID) {
        return this.profile.closeConversation(friendID, currentUserID);
    }

    @Override
    public MessageData sendMessage(int to, int from, String message) {
        return this.profile.sendMessage(to, from, message);
    }
}
