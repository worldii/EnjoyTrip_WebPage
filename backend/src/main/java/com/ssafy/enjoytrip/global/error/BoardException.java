package com.ssafy.enjoytrip.global.error;

public class BoardException extends EnjoyTripException {

    public BoardException(String message) {
        super(message);
    }

    public BoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
