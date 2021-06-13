package com.lgs.eden.api.callback;

import com.lgs.eden.api.APIResponseCode;

import java.util.ArrayList;

/**
 * Called when notification should be updated
 */
public interface NotificationsCallBack {

    void onCall(ArrayList<APIResponseCode> newStatus);

}
