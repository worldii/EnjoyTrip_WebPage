package com.ssafy.enjoytrip.error;

public class JwtInvalidException extends RuntimeException {

    public JwtInvalidException(String msg) {
        super(msg);
    }
}
