package com.lgs.eden.views.login;


import com.lgs.eden.api.Constants;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Login {

    @FXML
    protected TextField login;
    @FXML
    protected TextField password;
    @FXML
    protected CheckBox rememberMe;
    @FXML
    protected Label forgotPwd;

    /**
     * Action to submit a form by typing ENTER in a TextField
     * Calls the method {@link #onSubmitWithButton(Event)} if the right key is pressed
     * @param event event associated with this action
     */
    @FXML
    public void onSubmitWithEnter(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER))
            onSubmitWithButton(event);
    }


    /**
     * Action to submit the login data to the API
     * @param ignore never used
     */
    @FXML
    public void onSubmitWithButton(Event ignore) {
        boolean test = true;
        int username = login.getText().length();
        int wordpass = password.getText().length();


        // TODO: add popups related to logins errors
        // testing username compatibility with the API
        if(username < Constants.LOGIN_MIN_LENGTH || username > Constants.TEXT_MAX_LENGTH) {
            System.out.println("wrong username");
            test = false;
        }
        // testing password compatibility with the API
        if(wordpass < Constants.PASSWORD_MIN_LENGTH || wordpass > Constants.TEXT_MAX_LENGTH) {
            System.out.println("wrong password");
            test = false;
        }

        // TODO: Add a real submit method & real stock data methods
        if(test) {
            if(rememberMe.isSelected())
                System.out.println("Stocking username: " + login.getText());
            else
                System.out.println("Removing username :" + login.getText());

            System.out.println("submitted");
        }
    }


    /**
     * Action to redirect the user to the link to recover his password
     * @param ignore nothing to do with it
     */
    @FXML
    public void onForgotPassword(MouseEvent ignore) { // rename @ignore if you use it

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                // TODO: check language
                Desktop.getDesktop().browse(new URI("https://lgs-games.com/en/password_forgot"));
            } catch (URISyntaxException | IOException ignoreMeTooBlink) {
                // TODO: add popup related to these exceptions
                System.out.println("exception has occured");
            }
        }
    }


}
