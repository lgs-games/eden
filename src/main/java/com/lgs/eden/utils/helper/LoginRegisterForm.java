package com.lgs.eden.utils.helper;

import com.lgs.eden.api.Constants;
import com.lgs.eden.utils.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Convenience class for login/register views
 */
public abstract class LoginRegisterForm {

    // ------------------------------ STATIC ----------------------------- \\

    /** return screen **/
    protected static Parent getScreen(String path) {
        FXMLLoader loader = Utility.loadView(path);
        Parent screen = Utility.loadViewPane(loader);

        LoginRegisterForm controller = loader.getController();
        controller.resetForm();

        return screen;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML // login field
    protected TextField login;

    @FXML // password field
    protected PasswordField password;

    /**
     * Reset form
     */
    public abstract void resetForm();

    // ------------------------------ CHECK ----------------------------- \\

    protected boolean checkUsername(String username) {
        int length = username.length();
        return length < Constants.LOGIN_MIN_LENGTH || length > Constants.LOGIN_MAX_LENGTH;
    }

    protected boolean checkPassword(String pwd) {
        int length = pwd.length();
        return length < Constants.PASSWORD_MIN_LENGTH || length > Constants.PASSWORD_MAX_LENGTH;
    }

}
