package com.lgs.eden.views.gameslist;

import com.lgs.eden.api.games.GameViewData;
import com.lgs.eden.application.ApplicationCloseHandler;
import com.lgs.eden.utils.Utility;
import com.lgs.eden.utils.ViewsPath;
import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.download.DownloadListener;
import com.lgs.eden.utils.download.DownloadManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * Show download box view
 */
public class DownloadBox {

    // ------------------------------ STATIC ----------------------------- \\

    public static Parent getView(GameViewData data, Runnable onCancel, Runnable onInstalled) {
        FXMLLoader loader = Utility.loadView(ViewsPath.GAMES_LIST_DDL_BOX.path);
        Parent parent = Utility.loadViewPane(loader);
        DownloadBox controller = loader.getController();
        controller.init(data, onCancel, onInstalled);
        return parent;
    }

    // ------------------------------ INSTANCE ----------------------------- \\

    @FXML
    private Label speed;
    @FXML
    private Label downloaded;
    @FXML
    private Label size;

    private Runnable onCancel;

    public void init(GameViewData data, Runnable onCancel, Runnable onInstalled){
        this.onCancel = onCancel;

        // get the update information
        String url = data.update.getURL(Utility.getOS());

        DownloadManager d = new DownloadManager(url, Config.getDownloadRepository());

        DownloadListener l = (e) -> Platform.runLater(() -> {
            // init and show show
            this.size.setText(String.format("%.2f", (e.expectedSize / 1000000f)));
            this.speed.setText(String.format("%.2f", (e.speed / 1000000f)));
            this.downloaded.setText(String.format("%.2f", (e.downloaded / 1000000f)));
        });

        d.onInitCalled(l);
        d.onUpdateProgress(l);
        // move to install
        d.onDownloadEnd((e) -> {
            if (Utility.installGame(e.fileName, data.id)){
                onInstalled.run();
            }
        });

        // start download thread
        ApplicationCloseHandler.startDownloadThread(d);
    }

    public void onCancelRequest() {
        onCancel.run();
    }
}
