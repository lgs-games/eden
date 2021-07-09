package com.lgs.eden.utils.helper;

import com.lgs.eden.api.APIConstants;
import com.lgs.eden.utils.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    /**
     * Action to submit the login data to the API
     */
    public abstract void onSubmitWithButton();

    /**
     * Action to submit a form by typing ENTER in a TextField
     * Calls the method {@link #onSubmitWithButton()} if the right key is pressed
     *
     * @param event event associated with this action
     */
    @FXML
    public void onSubmitWithEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            onSubmitWithButton();
    }

    // ------------------------------ CHECK ----------------------------- \\

    protected boolean checkUsername(String username) {
        int length = username.length();
        return length < APIConstants.LOGIN_MIN_LENGTH || length > APIConstants.LOGIN_MAX_LENGTH;
    }

    protected boolean checkPassword(String pwd) {
        int length = pwd.length();
        return length < APIConstants.PASSWORD_MIN_LENGTH || length > APIConstants.PASSWORD_MAX_LENGTH;
    }

}
