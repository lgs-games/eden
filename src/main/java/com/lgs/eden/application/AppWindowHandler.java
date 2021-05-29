package com.lgs.eden.application;

import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.login.Login;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * App Window handler. Called when logged
 * or to switch screen in the logged side.
 */
public class AppWindowHandler {

    private static BorderPane window;

    /** reset window to login/basic format **/
    public static void changeToAppWindow(){
        WindowController.setSize(Config.SCREEN_WIDTH_APP, Config.SCREEN_HEIGHT_APP);
        // load main frame and save as window
        FXMLLoader loader = Utility.loadView(ViewsPath.FRAME_MAIN.path);
        window = (BorderPane) Utility.loadViewPane(loader);
        WindowController.setScreen(window);
    }

    /** set main frame screen **/
    public static void setScreen(Parent content){ window.setCenter(content); }

    /** reset window to login/basic format **/
    public static void goBackToMainApp(){
        WindowController.setSize(Config.SCREEN_WIDTH_APP, Config.SCREEN_HEIGHT_APP);
        WindowController.setScreen(Login.getScreen());
    }

}
