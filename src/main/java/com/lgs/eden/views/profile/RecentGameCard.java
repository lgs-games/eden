package com.lgs.eden.views.profile;

import com.lgs.eden.api.profile.RecentGameData;
import com.lgs.eden.utils.Translate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Controller for card.fxml
 */
public class RecentGameCard {

    @FXML
    private VBox root;
    @FXML
    private Label gameName; // game name: Enigma
    @FXML
    private Label lastPlayed; // ex: 5 days ago
    @FXML
    private Label lastPlayedLabel; // "last played" label
    @FXML
    private Label lastPlayedUnit; // days
    @FXML
    private Label lastPlayedAgo; // ago
    @FXML
    private Label time_played; // ex: 5 min., 10 hours., ...
    @FXML
    private ImageView game_icon; // game icon

    /**
     * Init the recent games view
     */
    public void init(RecentGameData data) {
        this.gameName.setText(data.gameName);
        this.game_icon.setImage(data.gameIcon);
        if (data.isPlaying()) {
            // hide last played
            this.lastPlayedLabel.setText(Translate.getTranslation("in_game"));
            this.lastPlayedLabel.getStyleClass().add("profile-game-playing-label");
            this.root.getStyleClass().set(0, "profile-game-playing");
            this.lastPlayed.setVisible(false);
            this.lastPlayedUnit.setVisible(false);
            this.lastPlayedAgo.setVisible(false);
        } else { // show last played xxx days
            this.lastPlayedLabel.getStyleClass().remove("profile-game-playing-label");
            this.root.getStyleClass().set(0, "profile-game");
            this.lastPlayed.setVisible(true);
            this.lastPlayedUnit.setVisible(true);
            this.lastPlayedAgo.setVisible(true);

            // last played in days
            GregorianCalendar c = new GregorianCalendar();
            c.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
            Date now = c.getTime();
            int res = (int) ((now.getTime() - data.lastPlayed) / (1000 * 60 * 60));
            if (res >= 24) this.lastPlayed.setText((res / 24)+ "");
            else {
                this.lastPlayed.setText(res+ "");
                this.lastPlayedUnit.setText("hours");
            }
        }
        this.time_played.setText(data.timePlayed + "");
    }
}
