package com.lgs.eden.api;

/**
 * API exceptions
 */
public class APIException extends Throwable {

    public final APIResponseCode code;
    public final Throwable e;

    public APIException(APIResponseCode code) {
        super(code.name());
        this.code = code;
        this.e = new UnknownError();
    }

    public APIException(APIResponseCode code, Exception e) {
        super(e);
        this.code = code;
        this.e = e;
    }
}
