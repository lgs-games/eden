package com.lgs.eden.views.achievements;

import com.lgs.eden.api.API;
import com.lgs.eden.api.games.AchievementData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Achievement view
 */
public class Achievements {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(String gameID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.ACHIEVEMENTS.path);
        Parent parent = Utility.loadViewPane(loader);
        Achievements controller = loader.getController();
        controller.init(gameID);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private VBox achievements;

    private void init(String gameID) {
        ArrayList<AchievementData> achievements = API.imp.getUserAchievements(gameID, AppWindowHandler.currentUserID());

        int size = achievements.size();
        for (int i = 0; i < size; i+=2) {
            AchievementData a1 = achievements.get(i), a2 = null;
            if (i + 1 < size) a2 = achievements.get(i+1);

            // add in box
            HBox box = new HBox();
            box.setSpacing(25);
            box.setAlignment(Pos.TOP_CENTER);

            box.getChildren().addAll(AchievementsCard.getScreen(a1), AchievementsCard.getScreen(a2));

            // add to view
            this.achievements.getChildren().add(box);
        }

    }

}
