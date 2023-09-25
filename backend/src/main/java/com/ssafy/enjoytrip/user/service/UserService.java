package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserModifyRequest;
import com.ssafy.enjoytrip.user.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.user.model.dto.response.UserResponse;

public interface UserService {

    TokenResponse login(final UserLoginRequest request);

    UserResponse getInformation(final String userId);

    boolean join(final UserAddRequest user);

    void modify(final UserModifyRequest request, final String userId);

    void delete(final String userId, final String loginUserId);
}
