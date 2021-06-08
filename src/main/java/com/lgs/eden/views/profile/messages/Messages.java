package com.lgs.eden.views.profile.messages;

import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Controller for message.fxml view
 */
public class Messages {

    // ------------------------------ STATIC ----------------------------- \\

    // no friend in particular
    public static Parent getScreen() { return getScreen(-1); }
    // open "friendID" conv
    public static Parent getScreen(int friendID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.MESSAGES.path);
        Parent view = Utility.loadViewPane(loader);
        Messages controller = loader.getController();
        controller.init(friendID);
        return view;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private void init(int friendID) {

    }
}
