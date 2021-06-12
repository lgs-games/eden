package com.lgs.eden.utils.config;

import com.lgs.eden.api.games.GameViewData;
import com.lgs.eden.application.ApplicationCloseHandler;
import com.lgs.eden.utils.Utility;

import java.io.File;
import java.io.IOException;

public class InstallUtils {

    /** install eden, close program **/
    public static void installEden(String installer) {
        OperatingSystem os = Utility.getUserOS();
        if (os.equals(OperatingSystem.WINDOWS)){
            try {
                // and close
                ApplicationCloseHandler.close(false);

                // start exe
                ProcessBuilder process = new ProcessBuilder(installer, "/VERYSILENT", "/MERGETASKS=\"desktopicon,postinstall\"");
                process.directory(new File(new File(installer).getParent()));
                process.start();

                // kill program
                System.exit(0);
            } catch (IOException ex) {
                System.out.println("Unable to install new version.");
                // PopupUtils.showPopup(Translate.getTranslation("Unable to install new version."));
            }
        } else {
            throw new UnsupportedOperationException("not yet");
        }
    }

    /** install game **/
    public static boolean installGame(String installer, int gameID) {
        OperatingSystem os = Utility.getUserOS();

        String location = Config.getGameFolder() + gameID + "/";

        if (os.equals(OperatingSystem.WINDOWS)){
            try {
                // start exe
                ProcessBuilder process = new ProcessBuilder(installer, "/SILENT", "/MERGETASKS=\"desktopicon\"",
                        "/DIR="+location);
                process.directory(new File(new File(installer).getParent()));
                Process start = process.start();
                int r = start.waitFor();
                return r == 0;
            } catch (IOException|InterruptedException ex) {
                return false;
            }
        } else {
            throw new UnsupportedOperationException("not yet");
        }
    }

    public static void runGame(GameViewData gameData, Runnable onProcessEnd) {
        if (!Utility.getUserOS().equals(OperatingSystem.WINDOWS))
            throw new UnsupportedOperationException("not yet");

        String location = Config.getGameFolder() + gameData.id + "/" + gameData.update.getRunnable(Utility.getUserOS());

        File file = new File(location);
        if (!file.exists()) {
            throw new IllegalStateException("file not found "+location);
        }

        ApplicationCloseHandler.startGameThread(() -> {
            try {
                // start exe
                ProcessBuilder process = new ProcessBuilder(location);
                process.directory(new File(file.getParent()));
                Process start = process.start();
                start.waitFor();
                onProcessEnd.run();
            } catch (IOException|InterruptedException ignore) {}
        });
    }

    public static boolean uninstallGame(GameViewData gameData) {
        if (!Utility.getUserOS().equals(OperatingSystem.WINDOWS))
            throw new UnsupportedOperationException("not yet");

        try {
            String location = Config.getGameFolder() + gameData.id + "/" + gameData.update.getUninstall(Utility.getUserOS());

            File file = new File(location);
            if (!file.exists()) { throw new IOException(); }

            ProcessBuilder process = new ProcessBuilder(location);
            process.directory(new File(file.getParent()));
            Process start = process.start();
            return start.waitFor() == 0;
        } catch (IOException|InterruptedException e){
            return false;
        }
    }


}
