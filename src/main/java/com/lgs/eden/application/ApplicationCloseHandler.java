package com.lgs.eden.application;

import javafx.event.EventHandler;
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
    public static void startUpdateThread(Timer timer, Runnable r) {
        gameDateUpdateTimer = timer;
        gameDateUpdateThread = new Thread(r);
        gameDateUpdateThread.start();
    }
    public static void closeUpdateThread(){
        if (gameDateUpdateTimer != null) gameDateUpdateTimer.cancel();
        if (gameDateUpdateThread != null) gameDateUpdateThread.interrupt();
    }


    // ------------------------------ HANDLER ----------------------------- \\

    @Override
    public void handle(WindowEvent event) {
        // ends
        closeUpdateThread();
    }
}
