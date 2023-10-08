package com.ssafy.enjoytrip.global.error;

public class HotPlaceException extends RuntimeException {

    public HotPlaceException() {
        super();
    }

    public HotPlaceException(String message) {
        super(message);
    }

    public HotPlaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public HotPlaceException(Throwable cause) {
        super(cause);
    }

    protected HotPlaceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
