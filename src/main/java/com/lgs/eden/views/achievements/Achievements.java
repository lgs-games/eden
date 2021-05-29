package com.lgs.eden.views.achievements;

import com.lgs.eden.api.Api;
import com.lgs.eden.api.wrapper.AchievementData;
import com.lgs.eden.application.WindowController;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

// TODO: make some max width values for the achievementCards

public class Achievements {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView(ViewsPath.ACHIEVEMENTS.path);
        Parent parent = Utility.loadViewPane(loader);
        Achievements controller = loader.getController();
        controller.init();
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private final AchievementData[] achievementData;

    private final int achievementNumber;

    @FXML
    private Label achievementTitle;

    @FXML
    private GridPane achievements;

    public Achievements() {
        this(1);
    }

    public Achievements(int game) {
        this.achievementData = Api.getGameAchievements(game);
        this.achievementNumber = Api.getGameAchievementsNumber(game);
    }

    private void init() {
        this.achievementTitle.setText(Translate.getTranslation("achievement_title") + " (count/total)");

        int column;
        for (int cardCount = 0; cardCount < achievementNumber; cardCount++) {
            column = cardCount % 2;

            // create
            FXMLLoader loader = Utility.loadView("/fxml/achievements/achievementcard.fxml");
            Parent card = Utility.loadViewPane(loader);
            // fill
            ((AchievementCard)loader.getController()).init(achievementData[cardCount]);
            // add
            this.achievements.add(card, column,cardCount / 2);
        }

        System.out.println(WindowController.getStage().getWidth());
    }


}
