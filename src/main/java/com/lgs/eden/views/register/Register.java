package com.lgs.eden.views.register;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIConstants;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.application.WindowController;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.helper.LoginRegisterForm;
import com.lgs.eden.views.login.Login;
import com.lgs.eden.views.settings.Settings;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

/**
 * Controller for register.fxml
 */
public class Register extends LoginRegisterForm {

    // ------------------------------ STATIC ----------------------------- \\

    // register screen
    public static Parent getScreen() {
        return LoginRegisterForm.getScreen(ViewsPath.REGISTER.path);
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    protected TextField email;

    public Register() {
    }

    @Override
    public void resetForm() {
        this.login.setText("");
        this.password.setText("");
        this.email.setText("");
    }

    // ------------------------------ METHODS ----------------------------- \\

    /**
     * Action to submit the login data to the API
     */
    @FXML
    @Override
    public void onSubmitWithButton() {
        String username = this.login.getText();
        String pwd = this.password.getText();
        String email = this.email.getText();
        StringBuilder error = new StringBuilder(); // for error message

        // testing username, password compatibility with the API
        if (checkUsername(username)) error.append("Username length must be in ["+ APIConstants.LOGIN_MIN_LENGTH+","+APIConstants.LOGIN_MAX_LENGTH+"]\n");
        if (checkUsername(pwd)) error.append("Username length must be in ["+APIConstants.PASSWORD_MIN_LENGTH+","+APIConstants.PASSWORD_MAX_LENGTH+"]\n");

        // testing email compatibility with the API
        if (!email.contains("@") || !email.contains(".")) {
            error.append("Please enter a valid email, we will ask for a confirmation.\n");
        }

        if (error.toString().isEmpty()) { // no error
            try {
                APIResponseCode response = API.imp.register(username, pwd, email);
                if (response.equals(APIResponseCode.REGISTER_OK)) {
                    // back to login
                    PopupUtils.showPopup(Translate.getTranslation(response), this::onPressBack);
                } else {
                    PopupUtils.showPopup(Translate.getTranslation(response));
                }
            } catch (APIException e) {
                PopupUtils.showPopup(e);
            }
        }

        if (!error.toString().isBlank()) {
            PopupUtils.showPopup(error.toString());
        }
    }

    /**
     * Goes from register screen to log in screen
     */
    @FXML
    public void onPressBack() {
        WindowController.setScreen(Login.getScreen());
    }


    /**
     * Action called when the user want to go to setting screen
     */
    @FXML
    public void onSettingsPressed() {
        Settings.setBackScreen(ViewsPath.REGISTER);
        WindowController.setScreen(Settings.getScreen());
    }

}
