package com.ssafy.enjoytrip.global.error;

public class JwtInvalidException extends RuntimeException {

    public JwtInvalidException(String msg) {
        super(msg);
    }
}
