package com.ssafy.enjoytrip.global.error;

public class MediaException extends EnjoyTripException {

    public MediaException() {
    }

    public MediaException(String message) {
        super(message);
    }

    public MediaException(String message, Throwable cause) {
        super(message, cause);
    }
}
