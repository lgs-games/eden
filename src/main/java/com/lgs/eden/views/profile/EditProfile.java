package com.lgs.eden.views.profile;

import com.lgs.eden.api.profile.ProfileData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Edit profile view
 */
public class EditProfile {

    public static Parent getScreen(ProfileData user) {
        FXMLLoader loader = Utility.loadView(ViewsPath.PROFILE_EDIT.path);
        Parent parent = Utility.loadViewPane(loader);
        EditProfile controller = loader.getController();
        controller.init(user);
        return parent;
    }

    private void init(ProfileData user) {
        
    }
}
