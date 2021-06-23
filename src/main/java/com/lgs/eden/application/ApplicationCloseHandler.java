package com.lgs.eden.application;

import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.web.WebEngine;
import javafx.stage.WindowEvent;

import java.util.Timer;

/**
 * Ends main/login app tasks
 * when closing requested.
 */
public class ApplicationCloseHandler implements EventHandler<WindowEvent> {

    // ------------------------------ UPDATE REQUEST THREAD ----------------------------- \\
    // see GameList#onUpdateRequest
    private static Timer gameDateUpdateTimer = null;
    private static Thread gameDateUpdateThread = null;
    private static WebEngine engine = null;
    private static Thread downloadManager = null;
    private static Thread gameThread = null;
    private static Thread notificationsThread = null;
    private static boolean logged = false;

    public static void setLogged(boolean logged) {
        ApplicationCloseHandler.logged = logged;
    }

    public static void startUpdateThread(Timer timer, Runnable r) {
        closeUpdateThread();
        gameDateUpdateTimer = timer;
        gameDateUpdateThread = new Thread(r);
        gameDateUpdateThread.start();
    }

    public static void closeUpdateThread() {
        if (gameDateUpdateTimer != null) gameDateUpdateTimer.cancel();
        if (gameDateUpdateThread != null) gameDateUpdateThread.interrupt();
        gameDateUpdateTimer = null;
        gameDateUpdateThread = null;
    }

    public static void registerLastEngine(WebEngine engine) {
        closeWebEngine();
        ApplicationCloseHandler.engine = engine;
    }

    public static void closeWebEngine() {
        if (engine != null) {
            engine.load(null);
            engine = null;
        }
    }

    public static void startDownloadThread(Thread downloadManager) {
        closeDownloadThread();
        ApplicationCloseHandler.downloadManager = downloadManager;
        downloadManager.start();
    }

    public static void closeDownloadThread() {
        if (downloadManager != null) {
            downloadManager.interrupt();
            downloadManager = null;
        }
    }

    public static void close(boolean simpleExit) {
        // ends
        closeUpdateThread();
        closeWebEngine();
        closeDownloadThread();
        closeNotificationsThread();

        if (logged) {
            try {
                API.imp.logout(AppWindowHandler.currentUserID());
            } catch (APIException e) {
                System.out.println("Logout failed");
            }
        }

        // close handler
        API.imp.close();

        // radical ends
        Platform.exit();

        if (simpleExit) {
            System.exit(0);
        }
    }

    public static void startGameThread(Runnable runnable) {
        closeGameThread();
        gameThread = new Thread(runnable);
        gameThread.start();
    }

    private static void closeGameThread() {
        if (gameThread != null) {
            gameThread.interrupt();
            gameThread = null;
        }
    }

    public static void starNotificationsThread(Runnable notifications) {
        closeNotificationsThread();
        notificationsThread = new Thread(notifications);
        notificationsThread.start();
    }

    private static void closeNotificationsThread() {
        if (notificationsThread != null) {
            notificationsThread.interrupt();
            notificationsThread = null;
        }
    }

    // ------------------------------ HANDLER ----------------------------- \\

    @Override
    public void handle(WindowEvent event) {
        close(true);
    }
}
