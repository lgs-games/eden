package com.lgs.eden.views.settings;

import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.WindowController;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.config.Language;
import com.lgs.eden.views.login.Login;
import com.lgs.eden.views.register.Register;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * Controller for settings.fxml
 */
public class Settings implements ChangeListener<Language> {

    // ------------------------------ STATIC ----------------------------- \\

    private static ViewsPath backScreen = ViewsPath.LOGIN;

    /**
     * Setter for the Parent of the screen that called settings
     * @param entry the Parent that called the settings screen
     */
    public static void setBackScreen(ViewsPath entry) { backScreen = entry; }

    /**
     * @return settings screen
     */
    public static Parent getScreen() {
        return getScreen(true);
    }

    public static Parent getScreen(boolean inLogin) {
        FXMLLoader loader = Utility.loadView(ViewsPath.SETTINGS.path);
        Parent screen = Utility.loadViewPane(loader);
        Settings controller = loader.getController();
        controller.initScreen(inLogin);

        // in game background is dark by default so we add a white box
        if (!inLogin) {
            screen.getStyleClass().add("white-box");
        }

        return screen;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    // list of available languages
    private final ObservableList<Language> languageList;

    @FXML // select list for language
    private ChoiceBox<Language> selectLanguage;

    @FXML // back button
    private Button back;

    @FXML // select folder button
    private Button folder;

    // true if inLogin, behaviour is different inLogin/... and InGame/...
    private boolean inLogin;

    public Settings() {
        // create list of languages
        this.languageList = FXCollections.observableArrayList();
        // todo: english only
        // this.languageList.addAll(Language.values());
        this.languageList.add(Language.EN);
    }

    /**
     * Init language list
     * todo: init game_folder path
     */
    private void initScreen(boolean inLogin) {
        this.inLogin = inLogin;

        this.selectLanguage.setItems(this.languageList);
        this.selectLanguage.setValue(Config.getLanguage());
        this.selectLanguage.getSelectionModel().selectedItemProperty().addListener(this); // watch

        // show or not back
        this.back.setVisible(inLogin);

        this.folder.setText(Utility.formatPath(Config.getGameFolder()));
        this.folder.setOnAction(event -> {
            // open file chooser
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle(Translate.getTranslation("choose_folder"));
            chooser.setInitialDirectory(new File(Config.getGameFolder()));
            File file = chooser.showDialog(WindowController.getStage());
            // save file chosen
            if (file != null) {
                String path = file.getPath();
                folder.setText(path);
                Config.setGameFolder(path);
            }
        });
    }

    @Override
    public void changed(ObservableValue<? extends Language> observable, Language oldValue, Language newValue) {
        // set selected
        Config.setLocale(newValue);
        // redraw
        if (inLogin) {
            WindowController.setScreen(Settings.getScreen());
        } else {
            AppWindowHandler.loadGameFrame();
            // reload view
            AppWindowHandler.setScreen(Settings.getScreen(false), ViewsPath.PROFILE);
        }
    }


    /**
     * Goes back to the screen that called the settings screen
     */
    @FXML
    public void onBackIsPressed() {
        if (backScreen.equals(ViewsPath.LOGIN)) {
            WindowController.setScreen(Login.getScreen());
        } else {
            WindowController.setScreen(Register.getScreen());
        }
    }

}
