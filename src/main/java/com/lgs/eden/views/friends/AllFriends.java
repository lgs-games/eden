package com.lgs.eden.views.friends;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class AllFriends {

    public static Parent getScreen(int userID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.FRIENDS_LIST.path);
        return Utility.loadViewPane(loader);
    }

}
