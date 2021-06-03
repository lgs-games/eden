package com.lgs.eden.api.auth;

import com.lgs.eden.utils.Utility;
import javafx.scene.image.Image;

/**
 * Wrapper for API Login Response
 */
public class LoginResponseData {

    /** result code, check todo: APIResponseCode **/
    public final int code;
    /** logged userID username if logged successful **/
    public final int userID;
    /** logged username if logged successful **/
    public final String username;
    /** logged avatar if logged successful **/
    public final Image avatar;

    public LoginResponseData(int code, int userID, String username, String avatar) {
        this.code = code;
        this.userID = userID;
        this.username = username;
        this.avatar = Utility.loadImage(avatar);
    }

    /** Set code and everything else at null/-1/"" */
    public LoginResponseData(int code) {
        this.code = code;
        this.userID = -1;
        this.username = "";
        this.avatar = null;
    }
}
