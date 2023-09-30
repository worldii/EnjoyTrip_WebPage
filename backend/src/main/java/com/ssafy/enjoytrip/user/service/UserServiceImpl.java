package com.ssafy.enjoytrip.user.service;

import com.ssafy.enjoytrip.global.auth.model.dto.request.LogoutRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.AccessTokenResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.response.RefreshTokenResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.global.auth.service.TokenService;
import com.ssafy.enjoytrip.global.error.UserException;
import com.ssafy.enjoytrip.global.infra.PasswordEncoder;
import com.ssafy.enjoytrip.user.dao.UserRepository;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserModifyRequest;
import com.ssafy.enjoytrip.user.model.dto.response.UserResponse;
import com.ssafy.enjoytrip.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    @Transactional
    public TokenResponse login(final UserLoginRequest request) {
        final User user = findUserByUserId(request.getUserId());

        if (!passwordEncoder.isMatch(request.getPassword(), user.getPassword(), user.getSalt())) {
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }

        final RefreshTokenResponse refreshToken = tokenService.generateRefreshToken(
            request.getUserId());
        final AccessTokenResponse accessToken = tokenService.generateAccessToken(
            request.getUserId());

        return TokenResponse.of(accessToken.getTokenName(), refreshToken.getTokenName());
    }

    @Override
    @Transactional
    public boolean join(final UserAddRequest request) {
        final String salt = passwordEncoder.generateSalt();
        final String hashedPassword = passwordEncoder.hashPassword(request.getPassword(), salt);

        return userRepository.insertByUser(request.toEntity(hashedPassword, salt)) == 1;
    }

    @Override
    public UserResponse getInformation(final String userId) {
        User user = findUserByUserId(userId);
        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public void modify(final UserModifyRequest request, final String userId) {
        User user = findUserByUserId(userId);

        user.updateEmail(request.getEmail());
        user.updateAddress(request.getAddress());
        user.updateName(request.getName());
        user.updatePassword(request.getPassword());

        userRepository.updateByUser(user);
    }

    @Override
    @Transactional
    public void delete(final String userId, final String loginUserId) {
        validateEqualMember(userId, loginUserId);

        User user = findUserByUserId(userId);

        tokenService.deleteRefreshToken(user.getUserId());
        userRepository.deleteByUserId(user.getUserId());
    }

    @Override
    @Transactional
    public void logout(final String userId, final LogoutRequest request) {
        String requestUserId = tokenService.parseToken(request.getAccessToken());
        validateEqualMember(userId, requestUserId);
        tokenService.deleteRefreshToken(userId);
        tokenService.registerBlackList(request.getAccessToken());
    }

    private User findUserByUserId(final String userId) {
        return userRepository.selectByUserId(userId)
            .orElseThrow(() -> new UserException("해당 유저가 없습니다."));
    }

    private void validateEqualMember(final String userId, final String requestUserId) {
        if (!userId.equals(requestUserId)) {
            throw new UserException("로그인한 회원이 아닙니다.");
        }
    }
}

