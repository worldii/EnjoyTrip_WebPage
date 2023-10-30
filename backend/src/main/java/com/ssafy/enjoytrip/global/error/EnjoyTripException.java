package com.ssafy.enjoytrip.global.error;

public class EnjoyTripException extends RuntimeException {

    public EnjoyTripException() {
        super();
    }

    public EnjoyTripException(final String message) {
        super(message);
    }

    public EnjoyTripException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EnjoyTripException(final Throwable cause) {
        super(cause);
    }
}
