package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.api.auth.AuthAPI;
import com.lgs.eden.api.auth.LoginResponseData;
import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Nexus imp of Auth
 */
public class AuthImp extends ImpSocket implements AuthAPI {

    // constructor
    public AuthImp(Socket socket) { super(socket); }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    public synchronized LoginResponseData login(String username, String pwd) throws APIException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<LoginResponseData> ref = new AtomicReference<>();

        socket.once(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });

        socket.once(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });

        socket.emit("login", username, pwd, (Ack) args -> {
            System.out.println(Arrays.toString(args));
//            Object json = args[0];
//            ref.set(new LoginResponseData(
//                    10, 23, username, "/avatars/23.png"
//            ));
//            latch.countDown();
        });

        return NexusUtils.response(latch, ref);

    }

    @Override
    public void logout(int currentUserID) {

    }

    @Override
    public APIResponseCode register(String username, String pwd, String email) throws APIException {
        return null;
    }

    @Override
    public String getPasswordForgotLink(String languageCode) {
        return null;
    }
}
