package com.lgs.eden.views.settings;

import com.lgs.eden.application.WindowController;
import com.lgs.eden.utils.Config;
import com.lgs.eden.utils.Language;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.views.login.Login;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;

/**
 * Controller for settings.fxml
 */
public class Settings implements ChangeListener<Language> {

    // ------------------------------ STATIC ----------------------------- \\

    private static Parent backScreen = Login.getScreen();
    public static void setBackScreen(Parent entry) {backScreen = entry;}
    public static Parent getBackScreen() {return backScreen;}

    /**
     * @return settings screen
     */
    public static Parent getScreen() {
        FXMLLoader loader = Utility.loadView("/fxml/settings.fxml");
        Parent screen = Utility.loadViewPane(loader);
        Settings controller = loader.getController();
        controller.initScreen();
        return screen;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    // list of available languages
    private final ObservableList<Language> languageList;

    @FXML // select list for language
    private ChoiceBox<Language> selectLanguage;

    public Settings() {
        // create list of languages
        this.languageList = FXCollections.observableArrayList();
        this.languageList.addAll(Language.values());
    }

    /**
     * Init language list
     * todo: init game_folder path
     * todo: add back button
     */
    private void initScreen() {
        this.selectLanguage.setItems(this.languageList);
        this.selectLanguage.setValue(Config.getLanguage());
        this.selectLanguage.getSelectionModel().selectedItemProperty().addListener(this); // watch
    }

    @Override
    public void changed(ObservableValue<? extends Language> observable, Language oldValue, Language newValue) {
        // set selected
        Config.setLocale(newValue);
        // redraw
        // todo: redraw
    }

    @FXML
    public void onBackIsPressed(Event ignore) {
        WindowController.setScreen(backScreen);
    }

}
