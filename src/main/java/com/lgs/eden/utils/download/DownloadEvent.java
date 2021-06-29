package com.lgs.eden.utils.download;

/**
 * Generated each second during a download.
 * Contains download info. Times are in ms.
 *
 * @see DownloadEvent#downloaded size such as as 92415878 for 92 mb 415Ko 878o
 * @see DownloadEvent#expectedSize size such as 92415878 for 92 mb 415Ko 878o
 * @see DownloadEvent#speed speed such as 1605632 for 1MB 605Ko 632o per second
 * @see DownloadEvent#timeRemaining estimated remaining time based on speed and remaining size, in s
 * @see DownloadEvent#fileName file name
 */
public record DownloadEvent(long downloaded, long expectedSize, long speed, long timeRemaining,
                            String fileName) {

}
