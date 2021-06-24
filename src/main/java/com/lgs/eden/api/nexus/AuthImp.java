package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.auth.AuthAPI;
import com.lgs.eden.api.auth.LoginResponseData;
import io.socket.client.Ack;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Nexus imp of Auth
 */
class AuthImp extends ImpSocket implements AuthAPI {

    // constructor
    public AuthImp(Socket socket) {
        super(socket);
    }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    public LoginResponseData login(String username, String pwd) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        MonitorIO<LoginResponseData> monitor = MonitorIO.createMonitor(this);
        // ask for login
        socket.emit("login", username, pwd, (Ack) args -> {
            if (monitor.isEmpty()) {
                new Thread(() -> {
                    try {
                        logout("-1");
                    } catch (APIException e) {
                        e.printStackTrace();
                    }
                }).start();
                return;
            }

            if (args.length > 0 && args[0] instanceof JSONObject){
                try {
                    JSONObject o = (JSONObject) args[0];
                    LoginResponseData rep; // store the response
                    // get the code
                    int code = o.getInt("code");
                    // since ok, get the new rest of the response
                    if (code == APIResponseCode.LOGIN_OK.code){
                        rep = new LoginResponseData(
                                code,
                                o.getString("user_id"),
                                o.getString("username"),
                                o.getString("avatar")
                        );
                    } else {
                        rep = new LoginResponseData(code);
                    }
                    // resume execution
                    monitor.set(rep);
                } catch (JSONException e) {
                    monitor.set(null);
                }
            }
        });

        return monitor.response();
    }

    @Override
    public void logout(String currentUserID) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);
        // logout
        MonitorIO<Object> monitor = MonitorIO.createMonitor(this);
        socket.emit("logout", (Ack) args -> monitor.set(new Object()));
        monitor.response();
    }

    @Override
    public APIResponseCode register(String username, String pwd, String email) throws APIException {
        // no connection
        NexusHandler.checkNetwork(this);

        // register
        MonitorIO<APIResponseCode> monitor = MonitorIO.createMonitor(this);
        socket.emit("register", username, pwd, email, (Ack) args -> {
            APIResponseCode rep = null;
            if (args.length > 0 && args[0] instanceof JSONObject) {
                   try {
                       JSONObject o = (JSONObject) args[0];
                       int code = o.getInt("code");
                       rep = APIResponseCode.fromCode(code);
                   } catch (JSONException e) {
                       rep = APIResponseCode.REGISTER_FAILED;
                   }
            }
            monitor.set(rep);
        });

        // ask for response, can raise Exception
        return monitor.response();
    }

}
