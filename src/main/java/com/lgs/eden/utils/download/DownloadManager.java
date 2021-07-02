package com.lgs.eden.utils.download;

import com.lgs.eden.application.PopupUtils;
import javafx.application.Platform;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Download a file to a location. You may add
 * callback on init, update and download end.
 */
public class DownloadManager extends Thread implements ReadableByteChannel {

    private final String url;
    private final String location;
    private DownloadListener initRunnable;
    private DownloadListener onUpdateRunnable;
    private DownloadListener endRunnable;

    public DownloadManager(String url, String location) {
        this.url = url;
        this.location = location;
        this.initRunnable = null;
        this.onUpdateRunnable = null;
        this.endRunnable = null;
    }

    // ------------------------------ EVENTS ----------------------------- \\

    public void onInitCalled(DownloadListener r) { this.initRunnable = r; }

    public void onUpdateProgress(DownloadListener r) { this.onUpdateRunnable = r; }

    public void onDownloadEnd(DownloadListener r) { this.endRunnable = r; }

    // ------------------------------ CORE ----------------------------- \\

    private DownloadEvent event;
    private DownloadEvent last;
    private FileOutputStream fileOutput;
    private ReadableByteChannel readChannel;
    private String fileName;
    private int size;
    private long timeElapsed = 0L;

    @Override
    public void run() {
        // todo: fix view inside model
        try {
            File out = new File(this.location);

            // try create dir
            if (!out.exists()) {
                try {
                    if (!out.mkdir()) {
                        throw new IOException();
                    }
                } catch (IOException | SecurityException e) {
                    Platform.runLater(() -> PopupUtils.showPopup(
                            "Unable to create a directory to store the new version."
                    ));
                }
            }

            //reading
            try {
                HttpsURLConnection.setFollowRedirects(false);
                HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("HEAD");

                this.size = connection.getContentLength();
                this.fileName = this.location + File.separator + Paths.get(new URI(url).getPath()).getFileName().toString();

                connection = (HttpsURLConnection) new URL(url).openConnection();
                connection.connect();
                this.readChannel = Channels.newChannel(connection.getInputStream());
                // call init callback with the values we have
                if (this.initRunnable != null) this.initRunnable.downloadCallBack(event = new DownloadEvent(
                        0, this.size, -1, -1,
                        fileName));

                // then start the transfert
                this.fileOutput = new FileOutputStream(this.fileName);

                this.fileOutput.getChannel().transferFrom(this, 0, Long.MAX_VALUE);
                this.fileOutput.close();
                this.fileOutput = null;
                //lgtm [java/useless-null-check]
                if (this.fileName != null) {
                    this.endRunnable.downloadCallBack(event);
                } // else callDownloadCancel();
            } catch (IOException e) {
                /*
                 * causes may be
                 * - can't find URL
                 * - invalid location path / can't create
                 */
                throw new IllegalStateException(e);
            }
        } catch (Exception e) {
            Platform.runLater(() -> PopupUtils.showPopup(
                    "Unable to download new version.\n" +
                            e.getMessage()
                            + "\n" +
                            Arrays.toString(e.getStackTrace()))
            );
        }
    }

    /**
     * cancel download
     **/
    public void cancel() {
        try {
            if (this.fileOutput == null) return;
            this.fileOutput.close();
            // delete remnant
            boolean delete = new File(this.fileName).delete();
            if (!delete) {
                throw new IOException();
            }
            this.fileName = null;
        } catch (IOException ignored) {}
    }

    // ------------------------------ CHANNELS ----------------------------- \\

    public int read(ByteBuffer bb) throws IOException {
        long downloaded, speed = this.event.speed(), timeRemaining;
        int n;
        // first call
        if (this.last == null) {
            this.timeElapsed = System.currentTimeMillis();
            this.last = event;
        }

        if (System.currentTimeMillis() - this.timeElapsed > 1000L) {//= 1s
            speed = this.event.downloaded() - this.last.downloaded();
            //reset
            this.last = this.event;
            this.timeElapsed = System.currentTimeMillis();
        }

        if ((n = this.readChannel.read(bb)) > 0) {
            downloaded = n + this.event.downloaded();
            if (speed < 0) speed = 0;
            if (speed != 0) {
                timeRemaining = (this.size - downloaded) / speed;
            } else timeRemaining = Long.MAX_VALUE;
            this.event = new DownloadEvent(downloaded, this.size, speed, timeRemaining, fileName);
            this.onUpdateRunnable.downloadCallBack(this.event);
        }

        return n;
    }

    // ------------------------------ CLOSE ----------------------------- \\

    @Override
    public void close() throws IOException { this.readChannel.close(); }

    @Override
    public boolean isOpen() { return this.readChannel.isOpen(); }

}
