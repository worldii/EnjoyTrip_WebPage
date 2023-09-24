package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.entity.User;

public interface UserService {

    User login(String userId, String password);

    User getInformation(String userId);

    int join(UserAddRequest user);

    int modify(User user);

    int leave(String userId);
}
