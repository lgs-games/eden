package com.lgs.eden.views.register;

import com.lgs.eden.api.Api;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.utils.helper.LoginRegisterForm;
import com.lgs.eden.application.WindowController;
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
        if (checkUsername(username)) error.append("wrong username\n");
        if (checkPassword(pwd)) error.append("wrong password\n");

        // testing email compatibility with the API
        if (!email.contains("@") || !email.contains(".")) {
            error.append("wrong email");
        }

        // TODO: Add a real submit method & real stock data methods
        if (error.toString().isEmpty()) { // no error
            int response = Api.register(username, pwd, email);
            if (response == 0){ //todo: create class
                // todo: move to app
                System.out.println("submitted, show message");
            } else {
                error.append("Register failed\n");
            }
        }

        if (!error.toString().isBlank()){
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
