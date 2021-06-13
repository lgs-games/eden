package com.lgs.eden.utils.download;

@FunctionalInterface
public interface DownloadListener {

    /** Called by DownloadManager, check the on... methods. **/
    void downloadCallBack(DownloadEvent event);

}
