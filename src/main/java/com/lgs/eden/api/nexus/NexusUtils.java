package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.APIResponseCode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Methods for Nexus API classes
 */
public class NexusUtils {

    /**
     * Wait and return the reference value of raise exception if we
     * got an error
     */
    public static <T> T response(CountDownLatch latch, AtomicReference<T> ref) throws APIException {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new APIException(APIResponseCode.SERVER_UNREACHABLE);
        }

        T rep = ref.get();
        if (rep == null){
            throw new APIException(APIResponseCode.SERVER_UNREACHABLE);
        }
        return rep;
    }

}
