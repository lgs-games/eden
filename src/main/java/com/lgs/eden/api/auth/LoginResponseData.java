package com.lgs.eden.api.auth;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

/**
 * Wrapper for API Login Response
 */
public class LoginResponseData {

    /** @see APIResponseCode **/
    public final APIResponseCode code;
    /** logged userID username if logged successful **/
    public final String userID;
    /** logged username if logged successful **/
    public final String username;
    /** logged avatar if logged successful **/
    public final Image avatar;
    // path
    public final String avatarPath;

    public LoginResponseData(int code, String userID, String username, String avatar) {
        this.code = APIResponseCode.fromCode(code);
        this.userID = userID;
        this.username = username;
        this.avatar = Utility.loadImage(avatar);
        this.avatarPath = avatar;
    }

    /** Set code and everything else at null/-1/"" */
    public LoginResponseData(int code) {
        this.code = APIResponseCode.fromCode(code);
        this.userID = "-1";
        this.username = "";
        this.avatar = null;
        this.avatarPath = null;
    }

    @Override
    public String toString() {
        return "LoginResponseData{" +
                "code=" + code +
                ", userID=" + userID +
                ", username='" + username + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}
