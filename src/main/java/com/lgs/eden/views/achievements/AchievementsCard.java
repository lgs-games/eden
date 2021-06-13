package com.lgs.eden.views.achievements;

import com.lgs.eden.api.games.AchievementData;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Achievement card
 */
public class AchievementsCard {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(AchievementData data) {
        FXMLLoader loader = Utility.loadView(ViewsPath.ACHIEVEMENTS_CARD.path);
        Parent parent = Utility.loadViewPane(loader);
        AchievementsCard controller = loader.getController();
        controller.init(data);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private BorderPane achievementPane;
    @FXML
    private ImageView achievementImage;
    @FXML
    private Label achievementTitle;
    @FXML
    private Label achievementDesc;

    private void init(AchievementData data) {
        if (data != null){
            achievementImage.setImage(data.icon);
            achievementTitle.setText(data.name);
            achievementDesc.setText(data.description);
            if (!data.unlocked){
                achievementPane.setOpacity(0.5);
            }
        } else {
            achievementTitle.setText("");
            achievementDesc.setText("");
            achievementPane.setVisible(false);
        }
    }

}
