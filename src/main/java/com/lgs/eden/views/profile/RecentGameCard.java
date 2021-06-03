package com.lgs.eden.views.profile;

import com.lgs.eden.api.profile.RecentGameData;
import com.lgs.eden.utils.Translate;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Controller for card.fxml
 */
public class RecentGameCard {

    public Label gameName; // game name: Enigma
    public Label lastPlayed; // ex: 5 days ago
    public Label lastPlayedLabel; // "last played" label
    public Label lastPlayedUnit; // "last played" label
    public Label time_played; // ex: 5 min., 10 hours., ...
    public ImageView game_icon; // game icon

    public void init(RecentGameData data) {
        this.gameName.setText(data.gameName);
        this.game_icon.setImage(data.gameIcon);
        if (data.isPlaying()) {
            // hide last played
            this.lastPlayedLabel.setText(Translate.getTranslation("in_game"));
            this.lastPlayedLabel.getStyleClass().add("profile-game-playing");
            this.lastPlayed.setVisible(false);
            this.lastPlayedUnit.setVisible(false);
        } else { // show last played xxx days
            this.lastPlayedLabel.getStyleClass().remove("profile-game-playing");
            this.lastPlayed.setVisible(true);
            this.lastPlayedUnit.setVisible(true);
            this.lastPlayed.setText(data.lastPlayed+"");
        }
        this.time_played.setText(data.timePlayed+"");
    }
}
