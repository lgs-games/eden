package com.lgs.eden.api.nexus.helpers;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.nexus.NexusHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Handle creating objects from the JSON
 * result of a request.
 * @param <T> result type
 */
public interface RequestObject<T> {

    /**
     * Parse and create an object from the
     * JSON
     */
    T parse(JSONObject o) throws JSONException, ParseException;

    /**
     * We wait for an event, and once we have the result, we parse it using
     * RequestObject and return it.
     *
     * Raise exception if not network / parse failed / wait failed.
     *
     * Params are the argument of your function aside the callback.
     */
    static <T> T requestObject(ImpSocket i, RequestObject<T> r, String event, Object... params) throws APIException {
        // no connection
        NexusHandler.checkNetwork(i);

        // register
        MonitorIO<T> monitor = MonitorIO.createMonitor(i);
        i.socket.emit(event, params, args -> {
            T rep = null;

            if (args.length > 0 && args[0] instanceof JSONObject) {
                try {
                    JSONObject o = (JSONObject) args[0];
                    rep = r.parse(o);
                } catch (JSONException | ParseException e){
                    rep = null;
                }
            }

            monitor.set(rep);
        });

        // ask for response, can raise Exception
        return monitor.response();
    }

}
