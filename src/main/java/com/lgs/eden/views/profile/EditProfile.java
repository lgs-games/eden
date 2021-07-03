package com.lgs.eden.views.profile;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.application.WindowController;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Edit profile view
 */
public class EditProfile {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(ProfileData user) {
        FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE_EDIT.path);
        Parent parent = Utility.loadViewPane(loader);
        EditProfile controller = loader.getController();
        controller.init(user);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private ImageView avatarImage;
    @FXML
    private TextField usernameField;
    @FXML
    private Button avatarButton;
    @FXML
    private TextArea descriptionField;

    private String newAvatarPath;
    private ProfileData user;

    private void init(ProfileData user) {
        this.user = user;
        avatarImage.setImage(user.avatar);
        usernameField.setText(user.username);
        descriptionField.setText(user.biography);

        this.avatarButton.setOnAction(event -> {
            // open file chooser
            FileChooser chooser = new FileChooser();
            chooser.setTitle(Translate.getTranslation("select_avatar_title"));
            chooser.setInitialDirectory(new File(System.getProperty("user.home")));
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
            File file = chooser.showOpenDialog(WindowController.getStage());
            // save file chosen
            if (file != null) {
                newAvatarPath = file.getPath();
                try {
                    File f = new File(newAvatarPath);
                    boolean valid = f.exists() && f.isFile();
                    if (valid) {
                        Path path = Paths.get(newAvatarPath);
                        valid = Files.size(path) <= API.MAX_AVATAR_SIZE;
                    }
                    if (valid) {
                        avatarImage.setImage(new Image(new FileInputStream(f)));
                    } else {
                        PopupUtils.showPopup(Translate.getTranslation("select_avatar_error"));
                    }
                } catch (IOException e) { // FileNotFoundException too
                    avatarImage.setImage(user.avatar);
                }
            } else{
                avatarImage.setImage(user.avatar);
            }
        });
    }

    @FXML
    private void onSubmitChanges(){
        // did we got changes ?
        String newAvatar = this.user.avatarPath.equals(this.newAvatarPath) ? null : this.newAvatarPath;
        String newUsername = this.user.username.equals(this.usernameField.getText()) ? null : this.usernameField.getText();
        String newDesc = this.user.biography.equals(this.descriptionField.getText()) ? null : this.descriptionField.getText();

        if (newAvatar == null && newUsername == null && newDesc == null){
            AppWindowHandler.setScreen(Profile.getScreen(), ViewsPath.PROFILE);
        } else {
            try {
                LoginResponseData d = API.imp.editProfile(newUsername, newAvatar, newDesc);
                AppWindowHandler.updateLoginResponse(d);
            } catch (APIException e){
                PopupUtils.showPopup(e);
            }
        }
    }
}
