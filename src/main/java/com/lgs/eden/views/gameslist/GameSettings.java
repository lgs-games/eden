package com.lgs.eden.views.gameslist;

import com.lgs.eden.api.API;
import com.lgs.eden.api.games.BasicGameData;
import com.lgs.eden.application.AppWindowHandler;
import com.lgs.eden.application.PopupUtils;
import com.lgs.eden.utils.Translate;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Game Settings controller
 */
public class GameSettings {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getScreen(BasicGameData gameID) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_SETTINGS.path);
        Parent parent = Utility.loadViewPane(loader);
        GameSettings controller = loader.getController();
        controller.init(gameID);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    private BasicGameData game;

    private void init(BasicGameData game) { this.game = game; }

    @FXML
    public void onUninstallGame() { processUninstallAndRemoveGame(true, false); }

    @FXML
    public void onLibraryRemoveGame() { processUninstallAndRemoveGame(false, true); }

    @FXML
    public void onUninstallAndRemoveGame() { processUninstallAndRemoveGame(true, true); }

    private void processUninstallAndRemoveGame(boolean uninstall, boolean remove){
        if (uninstall){ // todo: code uninstall
            System.out.println("uninstall");
        }
        if (remove){
            // remove from library
            if (!API.imp.removeFromLibrary(AppWindowHandler.currentUserID(), game)){
                PopupUtils.showPopup(Translate.getTranslation("remove_from_library_failed"));
                return;
            }

            AppWindowHandler.setScreen(GameList.getScreen(), ViewsPath.GAMES);
        }
    }
}
