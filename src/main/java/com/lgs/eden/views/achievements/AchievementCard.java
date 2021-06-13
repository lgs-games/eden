package com.lgs.eden.views.achievements;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class AchievementCard {

    @FXML
    private Label achievementName;
    @FXML
    private Label description;
    @FXML
    private ImageView achievementIcon;

    public void init() {
//        if(data.hidden && !data.unlocked) {
//            this.achievementName.setText(Translate.getTranslation("tmp_achievement_hidden_title"));
//            this.description.setText(Translate.getTranslation("tmp_achievement_hidden_description"));
//            this.achievementIcon.setImage(Utility.loadImage("/icons/patch-question64x64.png"));
//        } else {
//            this.achievementName.setText(data.achievementName);
//            this.description.setText(data.description);
//            this.achievementIcon.setImage(data.achievementIcon);
//        }
    }

}