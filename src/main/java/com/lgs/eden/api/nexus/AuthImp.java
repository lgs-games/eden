package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.auth.AuthAPI;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.nexus.helpers.ImpSocket;
import com.lgs.eden.api.nexus.helpers.MonitorIO;
import com.lgs.eden.api.nexus.helpers.RequestObject;
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
        return RequestObject.requestObject(this, new RequestObject<>() {
            @Override
            public LoginResponseData parse(JSONObject o) throws JSONException {
                // get the code
                int code = o.getInt("code");
                // since ok, get the new rest of the response
                if (code == APIResponseCode.LOGIN_OK.code) {
                    return new LoginResponseData(
                            code,
                            o.getString("user_id"),
                            o.getString("username"),
                            o.getString("avatar")
                    );
                }
                return new LoginResponseData(code);
            }

            @Override
            public boolean recall(MonitorIO<LoginResponseData> monitor) {
                if (monitor.isEmpty()) {
                    new Thread(() -> {
                        try {
                            logout("-1");
                        } catch (APIException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    return true;
                }
                return false;
            }
        }, "login", username, pwd);
    }

    @Override
    public void logout(String currentUserID) throws APIException {
        RequestObject.requestObject(this, o -> new Object(), "logout");
    }

    @Override
    public APIResponseCode register(String username, String pwd, String email) throws APIException {
        return RequestObject.requestObject(this, o -> {
            try {
                int code = o.getInt("code");
                return APIResponseCode.fromCode(code);
            } catch (JSONException e) {
                return APIResponseCode.REGISTER_FAILED;
            }
        }, "register", username, pwd, email);
    }

}
