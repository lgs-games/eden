package com.lgs.eden.views.login;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.ApplicationCloseHandler;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.application.WindowController;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.helper.LoginRegisterForm;
import com.lgs.eden.views.register.Register;
import com.lgs.eden.views.settings.Settings;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;

/**
 * Controller for Login.
 */
public class Login extends LoginRegisterForm {

    // ------------------------------ STATIC ----------------------------- \\

    /**
     * @return Login screen
     **/
    public static Parent getScreen() {
        return LoginRegisterForm.getScreen(ViewsPath.LOGIN.path);
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private CheckBox rememberMe;

    public Login() {
    }

    @Override
    public void resetForm() {
        // get stored if we do have one
        String stored_username = Config.getStoredUsername();
        this.login.setText(stored_username);
        this.rememberMe.setSelected(!stored_username.isEmpty());
        this.password.setText("");
    }

    // ------------------------------ METHODS ----------------------------- \\

    @FXML
    @Override
    public void onSubmitWithButton() {
        String username = this.login.getText();
        String pwd = this.password.getText();
        StringBuilder error = new StringBuilder(); // for error message

        // testing username, password compatibility with the API
        // todo: translate
        if (checkUsername(username)) error.append("wrong username\n");
        if (checkPassword(pwd)) error.append("wrong password\n");

        if (error.toString().isEmpty()) { // no error
            try {
                // add user
                LoginResponseData response = API.imp.login(username, pwd);
                if (response.code.equals(APIResponseCode.LOGIN_OK)) { // this is an user id
                    // so we are good
                    AppWindowHandler.changeToAppWindow(response);
                    ApplicationCloseHandler.setLogged(true);
                } else {
                    PopupUtils.showPopup(Translate.getTranslation(response.code));
                }

                // store or not username
                Config.lastUsername(username, rememberMe.isSelected());
            } catch (APIException e) {
                PopupUtils.showPopup(e);
            }
        }

        if (!error.toString().isBlank()) {
            PopupUtils.showPopup(error.toString());
        }
    }

    /**
     * Action to redirect the user to the link to recover his password
     */
    @FXML
    public void onForgotPassword() { // rename @ignore if you use it
        Utility.openInBrowser(API.imp.getPasswordForgotLink(Config.getCode()));
    }

    @FXML
    public void onSignInIsPressed() {
        WindowController.setScreen(Register.getScreen());
    }

    @FXML
    public void onSettingsPressed() {
        Settings.setBackScreen(ViewsPath.LOGIN);
        WindowController.setScreen(Settings.getScreen());
    }

}
