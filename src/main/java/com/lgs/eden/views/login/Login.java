package com.lgs.eden.views.login;

import com.lgs.eden.api.Api;
import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.helper.LoginRegisterForm;

import com.lgs.eden.api.Constants;
import com.lgs.eden.application.WindowController;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.views.register.Register;
import com.lgs.eden.views.settings.SettingsController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Controller for Login.
 */
public class Login extends LoginRegisterForm {

    // ------------------------------ STATIC ----------------------------- \\

    /**
     * @return Login screen
     **/
    public static Parent getScreen() {
        return LoginRegisterForm.getScreen("/fxml/login.fxml");
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private CheckBox rememberMe;

    public Login() {
    }

    @Override
    public void resetForm() {
        // get stored if we do have one
        String stored_username = Config.getStored_username();
        this.login.setText(stored_username);
        this.rememberMe.setSelected(!stored_username.isEmpty());
        this.password.setText("");
    }

    // ------------------------------ METHODS ----------------------------- \\

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

    /**
     * Action to submit the login data to the API
     */
    @FXML
    public void onSubmitWithButton() { // TODO: translate
        String username = this.login.getText();
        String pwd = this.password.getText();
        StringBuilder error = new StringBuilder(); // for error message

        // testing username, password compatibility with the API
        if (checkUsername(username)) error.append("wrong username\n");
        if (checkPassword(pwd)) error.append("wrong password\n");

        if (error.toString().isEmpty()){ // no error
            // add user
            int response = Api.login(username, pwd);
            if (response == 0){
                // todo: move to app
                System.out.println("submitted, go to app");
            } else {
                error.append("Invalid credentials\n");
            }

            // store or not username
            Config.lastUsername(username, rememberMe.isSelected());
        }

        // todo: show popup related to logins errors
        System.out.println(error);
    }

    /**
     * Action to redirect the user to the link to recover his password
     */
    @FXML
    public void onForgotPassword() { // rename @ignore if you use it
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(Api.passwordForgotPage(Config.getLanguage().code)));
            } catch (URISyntaxException | IOException ignoreMeTooBlink) {
                // TODO: add popup related to these exceptions
                System.out.println("exception has occurred");
            }
        }
    }

    @FXML
    public void onSigninIsPressed(ActionEvent ignore) {
        WindowController.setScreen(Register.getScreen());
    }

    @FXML
    public void onSettingsPressed(ActionEvent ignore) {
        SettingsController.setBackScreen(Login.getScreen());
        WindowController.setScreen(SettingsController.getScreen());
    }

}
