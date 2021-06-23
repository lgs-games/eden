package com.lgs.eden.api.nexus;

import io.socket.client.Socket;

public abstract class ImpSocket {

    protected final Socket socket;

    public ImpSocket(Socket socket) {
        this.socket = socket;
    }
}
