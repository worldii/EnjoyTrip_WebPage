package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.user.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.user.model.entity.User;

public interface UserService {

    TokenResponse login(final UserLoginRequest request);

    User getInformation(String userId);

    boolean join(UserAddRequest user);

    int modify(User user);

    int leave(String userId);
}
