package com.ssafy.enjoytrip.core.user.service;


import com.ssafy.enjoytrip.core.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserModifyRequest;
import com.ssafy.enjoytrip.core.user.model.dto.response.UserResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.request.LogoutRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;

public interface UserService {

    TokenResponse login(final UserLoginRequest request);

    UserResponse getInformation(final String userId);

    boolean join(final UserAddRequest user);

    void modify(final UserModifyRequest request, final String userId);

    void delete(final String userId, final String loginUserId);

    void logout(final String userId, final LogoutRequest request);
}
