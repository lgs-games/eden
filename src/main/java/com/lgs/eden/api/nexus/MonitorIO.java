package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;
import io.socket.client.Socket;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Used to simply the handling of IO Requests.
 * Create a monitor with #createMonitor then
 * call for #response to wait for a response.
 *
 * In another thread, you should call set in order to
 * stop the response wait.
 *
 * @param <T> returned by response, also the type of
 *           set so that's basically the data that you are
 *           waiting for.
 */
public class MonitorIO<T> {
    public final CountDownLatch latch;
    public final AtomicReference<T> ref;

    private MonitorIO(CountDownLatch latch, AtomicReference<T> ref) {
        this.latch = latch;
        this.ref = ref;
    }

    /**
     * Wait and return the reference value of raise exception if we
     * got an error
     */
    public T response() throws APIException {
        try {
            this.latch.await();
        } catch (InterruptedException e) {
            throw new APIException(APIResponseCode.SERVER_UNREACHABLE);
        }

        T rep = this.ref.get();
        if (rep == null){
            throw new APIException(APIResponseCode.SERVER_UNREACHABLE);
        }
        return rep;
    }

    /**
     * Set the value we were waiting and resume execution
     */
    public void set(T value){
        this.ref.set(value);
        this.latch.countDown();
    }

    public static <T> MonitorIO<T> createMonitor(ImpSocket s) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<T> ref = new AtomicReference<>();

        s.socket.once(Socket.EVENT_CONNECT_ERROR, args -> {
            ref.set(null);
            latch.countDown();
        });
        s.socket.once(Socket.EVENT_DISCONNECT, args -> {
            ref.set(null);
            latch.countDown();
        });

        return new MonitorIO<>(latch, ref);
    }
}
