package com.lgs.eden.application;

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
    private static WebEngine engine;

    public static void startUpdateThread(Timer timer, Runnable r) {
        closeUpdateThread();
        gameDateUpdateTimer = timer;
        gameDateUpdateThread = new Thread(r);
        gameDateUpdateThread.start();
    }
    public static void closeUpdateThread(){
        if (gameDateUpdateTimer != null) gameDateUpdateTimer.cancel();
        if (gameDateUpdateThread != null) gameDateUpdateThread.interrupt();
        gameDateUpdateTimer = null;
        gameDateUpdateThread = null;
    }

    public static void registerLastEngine(WebEngine engine) {
        closeWebEngine();
        ApplicationCloseHandler.engine = engine;
    }

    private static void closeWebEngine() {
        if (engine != null){
            engine.load(null);
        }
    }


    // ------------------------------ HANDLER ----------------------------- \\

    @Override
    public void handle(WindowEvent event) {
        // ends
        closeUpdateThread();
        closeWebEngine();
        // radical ends
        Platform.exit();
    }
}
