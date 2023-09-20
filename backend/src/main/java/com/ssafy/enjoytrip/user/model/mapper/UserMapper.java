package com.ssafy.enjoytrip.user.model.mapper;

import com.ssafy.enjoytrip.user.model.dto.User;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    Optional<User> selectByUserId(String userId);

    int updateByUser(User user);

    int insertByUser(User user);

    int deleteByUserId(String userId);
}
