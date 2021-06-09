package com.lgs.eden.api.local;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.games.*;
import com.lgs.eden.api.news.BasicNewsData;
import com.lgs.eden.api.profile.friends.FriendConversationView;
import com.lgs.eden.api.profile.friends.FriendData;
import com.lgs.eden.api.profile.ProfileData;
import javafx.collections.ObservableList;

import java.util.ArrayList;

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
        this.profile = new ProfileHandler();
        this.news = new NewsHandler();
    }

    // ------------------------------ LOGIN ----------------------------- \\

    @Override
    public LoginResponseData login(String username, String pwd) { return this.login.login(username, pwd); }

    @Override
    public void logout() { this.login.logout(); }

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
    public boolean addToLibrary(BasicGameData game) { return this.games.addToLibrary(game); }

    // ------------------------------ NEWS ----------------------------- \\

    @Override
    public ArrayList<BasicNewsData> getAllNews(int begin, int count, String code, int gameID) {
        return this.news.getAllNews(begin, count, code, gameID);
    }

    // ------------------------------ PROFILE ----------------------------- \\

    @Override
    public ArrayList<FriendData> getFriendList(int userID, int loggedID) { return this.profile.getFriendList(userID, loggedID); }

    @Override
    public ProfileData getProfileData(int userID, int loggedID) { return this.profile.getProfileData(userID, loggedID); }

    @Override
    public FriendConversationView getMessageWithFriend(int friendID) { return this.profile.getMessageWithFriend(friendID); }

    @Override
    public void addFriend(int userID, int currentUserID) { this.profile.addFriend(userID, currentUserID); }

    @Override
    public void removeFriend(int userID, int currentUserID) { this.profile.removeFriend(userID, currentUserID); }
}
