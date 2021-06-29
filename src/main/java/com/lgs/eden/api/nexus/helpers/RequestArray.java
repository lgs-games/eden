package com.lgs.eden.api.nexus.helpers;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.nexus.NexusHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handle creating objects from the JSON
 * result of a request.
 */
public interface RequestArray {

    /**
     * Call RequestObject for each object or an
     * array.
     */
    static <T> ArrayList<T> requestArray(ImpSocket imp, RequestObject<T> r, String event, Object... params)
            throws APIException {

        // no connection
        NexusHandler.checkNetwork(imp);

        // register
        MonitorIO<ArrayList<T>> monitor = MonitorIO.createMonitor(imp);
        imp.socket.emit(event, params, args -> {
            ArrayList<T> rep = null;
            if (args.length > 0 && args[0] instanceof JSONArray) {
                try {
                    JSONArray array = (JSONArray) args[0];
                    rep = new ArrayList<>();
                    // valid
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = (JSONObject) array.get(i);
                        rep.add(r.parse(o));
                    }
                } catch (JSONException | ParseException e) {
                    rep = null;
                }
            }
            monitor.set(rep);
        });

        // ask for response, can raise Exception
        return monitor.response();
    }

}