package com.lgs.eden.application;

import com.lgs.eden.api.API;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.views.friends.AllFriends;
import com.lgs.eden.views.gameslist.GameList;
import com.lgs.eden.views.inventory.Inventory;
import com.lgs.eden.views.login.Login;
import com.lgs.eden.views.marketplace.Marketplace;
import com.lgs.eden.views.profile.messages.Messages;
import com.lgs.eden.views.profile.Profile;
import com.lgs.eden.views.settings.Settings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * App Window handler. Called when logged
 * or to switch screen in the logged side.
 */
public class AppWindowHandler {

    // ------------------------------ STATIC ----------------------------- \\

    private static BorderPane window;
    // save logged user data
    private static LoginResponseData loggedUser;
    private static AppWindowHandler controller;

    /** reset window to login/basic format **/
    public static void changeToAppWindow(LoginResponseData response){
        // save
        loggedUser = response;

        // load
        WindowController.setSize(Config.SCREEN_WIDTH_APP, Config.SCREEN_HEIGHT_APP);
        loadGameFrame();
        setScreen(GameList.getScreen(), ViewsPath.GAMES);
    }

    /** load game menu bar with login, ... and menu **/
    public static void loadGameFrame(){
        // load main frame and save as window
        FXMLLoader loader = Utility.loadView(ViewsPath.FRAME_MAIN.path);
        window = (BorderPane) Utility.loadViewPane(loader);
        controller = loader.getController();
        controller.init();
        WindowController.setScreen(window);
    }

    /** set main frame screen **/
    public static void setScreen(Parent content, ViewsPath menu){
        window.setCenter(content);
        controller.setCurrentMenu(menu);
    }

    /** reset window to login/basic format **/
    public static void goBackToMainApp(){
        WindowController.setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        WindowController.setScreen(Login.getScreen());
        loggedUser = null;
    }

    /** convenience method, return userID **/
    public static int currentUserID() { return loggedUser.userID; }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private MenuButton username;
    @FXML
    private ImageView userAvatar;
    @FXML
    private Label games;
    @FXML
    private Label library;

    private void init() {
        this.username.setText("      "+loggedUser.username);
        this.userAvatar.setImage(loggedUser.avatar);
    }

    /** set in red current menu **/
    private void setCurrentMenu(ViewsPath menu) {
        Labeled s = games;
        Labeled o1 = library;
        Labeled o2 = username;

        if (menu.equals(ViewsPath.PROFILE)){
            s = username;
            o2 = games;
        } if (menu.equals(ViewsPath.MARKETPLACE)){
            s = library;
            o1 = games;
        }

        s.setTextFill(Color.valueOf("#dd4411"));
        o1.setTextFill(Color.BLACK);
        o2.setTextFill(Color.BLACK);
    }

    // ------------------------------ LISTENERS ----------------------------- \\

    @FXML
    public void goToInventory() { setScreen(Inventory.getScreen(), ViewsPath.PROFILE); }

    @FXML
    public void openSettings() {
        setScreen(Settings.getScreen(false), ViewsPath.PROFILE);
    }

    @FXML
    public void logout() {
        API.imp.logout();
        Platform.runLater(AppWindowHandler::goBackToMainApp);
    }

    @FXML
    public void goToProfile() { setScreen(Profile.getScreen(), ViewsPath.PROFILE); }

    @FXML
    public void goToAllFriends() { setScreen(AllFriends.getScreen(loggedUser.userID), ViewsPath.PROFILE); }

    @FXML
    public void goToMessages() { setScreen(Messages.getScreen(), ViewsPath.PROFILE); }

    @FXML
    public void goToMarketPlace() {
        setScreen(Marketplace.getScreen(), ViewsPath.MARKETPLACE);
    }

    @FXML
    public void goToGameList() { setScreen(GameList.getScreen(), ViewsPath.GAMES); }
}
