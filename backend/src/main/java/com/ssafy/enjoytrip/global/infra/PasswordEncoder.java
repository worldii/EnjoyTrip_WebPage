package com.ssafy.enjoytrip.global.infra;

public interface PasswordEncoder {

    String hashPassword(final String raw, final String salt);

    boolean isMatch(final String raw, final String hashed, final String salt);

    String generateSalt();
}
