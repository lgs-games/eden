package com.lgs.eden.views.register;

import com.lgs.eden.api.Constants;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.views.login.Login;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Register extends Login {

    // ------------------------------ STATIC ----------------------------- \\

    private static Parent screen = null;
    private static FXMLLoader loader;

    public static Parent getScreen() {
        if (screen == null) {
            // todo: clean
            try {
                loader = Utility.loadView("/fxml/register.fxml");
                screen = loader.load();
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
            }
        }

        Register controller = loader.getController();
        controller.login.setText("");
        controller.password.setText("");
        controller.email.setText("");

        return screen;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    protected TextField email;

    /**
     * Action to submit the login data to the API
     *
     * @param ignore never used
     */
    @FXML
    public void onSubmitWithButton(Event ignore) {
        boolean test = true;
        int username = login.getText().length();
        int wordpass = password.getText().length();


        // TODO: add popups related to logins errors
        // testing username compatibility with the API
        if (username < Constants.LOGIN_MIN_LENGTH || username > Constants.LOGIN_MAX_LENGTH) {
            System.out.println("wrong username");
            test = false;
        }
        // testing password compatibility with the API
        if (wordpass < Constants.PASSWORD_MIN_LENGTH || wordpass > Constants.PASSWORD_MAX_LENGTH) {
            System.out.println("wrong password");
            test = false;
        }
        // testing email compatibility with the API
        if (!email.getText().contains("@") || !email.getText().contains(".")) {
            System.out.println("wrong email");
            test = false;
        }

        // TODO: Add a real submit method & real stock data methods
        if (test)
            System.out.println("submitted");


    }

}
