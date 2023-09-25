package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.global.error.UserException;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserModifyRequest;
import com.ssafy.enjoytrip.user.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.user.model.dto.response.UserResponse;
import com.ssafy.enjoytrip.user.model.entity.User;
import com.ssafy.enjoytrip.user.model.interceptor.PasswordEncoder;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import com.ssafy.enjoytrip.user.model.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public TokenResponse login(final UserLoginRequest request) {
        final User user = userMapper.selectByUserId(request.getUserId())
            .orElseThrow(() -> new UserException("해당 유저가 없습니다."));

        if (!passwordEncoder.isMatch(request.getPassword(), user.getPassword(), user.getSalt())) {
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }

        final String accessToken = jwtService.generateAccessToken(request.getUserId());
        final String refreshToken = jwtService.generateRefreshToken(request.getUserId());
        return TokenResponse.of(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public boolean join(final UserAddRequest request) {
        final String salt = passwordEncoder.generateSalt();
        final String hashedPassword = passwordEncoder.hashPassword(request.getPassword(), salt);

        return userMapper.insertByUser(request.toEntity(hashedPassword, salt)) == 1;
    }

    @Override
    public UserResponse getInformation(final String userId) {
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> new UserException("해당 유저가 없습니다."));
        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public void modify(final UserModifyRequest request, final String userId) {
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> new UserException("해당 유저가 없습니다."));

        user.updateEmail(request.getEmail());
        user.updateAddress(request.getAddress());
        user.updateName(request.getName());
        user.updatePassword(request.getPassword());

        userMapper.updateByUser(user);
    }

    @Override
    @Transactional
    public void delete(final String userId, final String loginUserId) {
        if (!userId.equals(loginUserId)) {
            throw new UserException("로그인한 회원이 아닙니다.");
        }

        userMapper.selectByUserId(userId)
            .orElseThrow(() -> new UserException("해당 유저가 없습니다."));

        jwtService.deleteRefreshToken(userId);
        userMapper.deleteByUserId(userId);
    }

}

