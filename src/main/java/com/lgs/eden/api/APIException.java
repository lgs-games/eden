package com.lgs.eden.api;

/**
 * API exceptions
 */
public class APIException extends Exception {

    public final APIResponseCode code;
    public final Throwable e;

    public APIException(APIResponseCode code) {
        super(code.name());
        this.code = code;
        this.e = new UnknownError();
    }

    public APIException(APIResponseCode code, Throwable e) {
        super(e);
        this.code = code;
        this.e = e;
    }
}
