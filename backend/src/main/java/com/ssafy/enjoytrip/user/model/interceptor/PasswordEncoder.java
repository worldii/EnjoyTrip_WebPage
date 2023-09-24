package com.ssafy.enjoytrip.user.model.interceptor;

public interface PasswordEncoder {

    String hashPassword(final String raw, final String salt);

    boolean isMatch(final String raw, final String hashed, final String salt);

    String generateSalt();
}
