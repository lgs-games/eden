package com.lgs.eden.utils.download;

/**
 * Generated each second during a download.
 * Contains download info. Times are in ms.
 *
 * @see DownloadEvent#downloaded size such as as 92415878 for 92 mb 415Ko 878o
 * @see DownloadEvent#expectedSize size such as 92415878 for 92 mb 415Ko 878o
 * @see DownloadEvent#speed speed such as 1605632 for 1MB 605Ko 632o per second
 * @see DownloadEvent#timeRemaining estimated remaining time based on speed and remaining size, in s
 */
public class DownloadEvent {

    public final long downloaded;
    public final long expectedSize;
    public final long speed;
    public final long timeRemaining;

    public DownloadEvent(long downloaded, long expectedSize, long speed, long timeRemaining) {
        this.downloaded = downloaded;
        this.expectedSize = expectedSize;
        this.speed = speed;
        this.timeRemaining = timeRemaining;
    }

}
