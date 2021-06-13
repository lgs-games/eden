package com.lgs.eden.api;

/**
 * API exceptions
 */
public class APIException extends Throwable {

    public final APIResponseCode code;
    public final Exception e;

    public APIException(APIResponseCode code, Exception e) {
        super(e);
        this.code = code;
        this.e = e;
    }
}
