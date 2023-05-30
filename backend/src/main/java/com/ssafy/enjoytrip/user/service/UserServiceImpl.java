package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.user.model.dto.User;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import com.ssafy.enjoytrip.util.UserEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public User login(String userId,String password) {
        User user = userMapper.selectByUserId(userId);

        if(user != null && UserEncoder.isMatch(password,user.getPassword())){
            return user;
        }

        return null;
    }

    @Override
    @Transactional
    public int join(User user) {
        return userMapper.insertByUser(user);
    }

    @Override
    @Transactional
    public int modify(User user) {
        return userMapper.updateByUser(user);
    }

    @Override
    @Transactional
    public int leave(String userId) {
        return userMapper.deleteByUserId(userId);
    }

    @Override
    public User getInformation(String userId) {
        return userMapper.selectByUserId(userId);
    }
}

