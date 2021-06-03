package com.lgs.eden.api.nexus;

import com.lgs.eden.api.API;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.games.EdenVersionData;
import com.lgs.eden.api.profile.FriendData;
import com.lgs.eden.api.profile.ProfileData;

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

    public LocalHandler() {
        this.login = new AuthHandler();
        this.games = new GamesHandler();
        this.profile = new ProfileHandler();
    }

    // ------------------------------ LOGIN ----------------------------- \\

    @Override
    public LoginResponseData login(String username, String pwd) { return this.login.login(username, pwd); }

    @Override
    public void logout() { this.login.logout(); }

    @Override
    public int register(String username, String pwd, String email) { return this.login.register(username, pwd, email);   }

    @Override
    public String getPasswordForgotLink(String languageCode) {
        return this.login.getPasswordForgotLink(languageCode);
    }

    // ------------------------------ GAMES ----------------------------- \\

    @Override
    public EdenVersionData getEdenVersion() { return this.games.getEdenVersion(); }

    // ------------------------------ PROFILE ----------------------------- \\

    @Override
    public ArrayList<FriendData> getFriendList(int userID) { return this.profile.getFriendList(userID); }

    @Override
    public ProfileData getProfileData(int userID) { return this.profile.getProfileData(userID); }
}
