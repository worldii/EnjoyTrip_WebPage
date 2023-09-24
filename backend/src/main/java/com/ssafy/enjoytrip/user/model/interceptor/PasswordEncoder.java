package com.ssafy.enjoytrip.user.model.interceptor;

public interface PasswordEncoder {

    String hashPassword(final String raw);

    boolean isMatch(final String raw, final String hashed);
}
