package com.lgs.eden.api.nexus.helpers;

import com.lgs.eden.api.APIHandler;
import io.socket.client.Socket;

public abstract class ImpSocket {

    protected final Socket socket;
    protected APIHandler parent;

    public ImpSocket(Socket socket) {
        this.socket = socket;
    }

    public void setParent(APIHandler parent) { this.parent = parent; }

    public boolean notConnected() {
        return !socket.connected();
    }
}
