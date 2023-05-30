package com.ssafy.enjoytrip.user;

import com.ssafy.enjoytrip.user.model.dto.User;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void testLoadUserMapper(){

        //given - when - then
        assertNotNull(userMapper);
    }

    @Test
    void testSelectUser(){
        //given
        String userId = "ssafy";

        //when
        User user = userMapper.selectByUserId(userId);

        //then
        System.out.println(user);
        assertEquals(userId,user.getUserId());
    }

    @Test
    @Transactional
    void testInsertUser(){
        //given
        String userId = "test";
        String password = "test";
        String name = "test";
        String email = "test@test";
        String address = "test";
        User insertedUser = User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .address(address).build();

        //when

        int result = userMapper.insertByUser(insertedUser);
        User selectedUser = userMapper.selectByUserId(userId);

        //then

        System.out.println(selectedUser);
        assertEquals(result,1);
        assertEquals(insertedUser.getUserId(),selectedUser.getUserId());
    }

    @Test
    @Transactional
    void testUpdateUser(){
        //given
        String userId = "test";

        String beforePassword = "before";
        String beforeName = "before";
        String beforeEmail = "before@before";
        String beforeAddress = "before";
        User beforeUser = User.builder()
                .userId(userId)
                .password(beforePassword)
                .name(beforeName)
                .email(beforeEmail)
                .address(beforeAddress).build();

        String updatePassword = "after";
        String updateName = "after";
        String updateEmail = "after@after";
        String updateAddress = "after";
        User updateUser = User.builder()
                .userId(userId)
                .password(updatePassword)
                .name(updateName)
                .email(updateEmail)
                .address(updateAddress).build();

        userMapper.insertByUser(beforeUser);

        //when
        int result = userMapper.updateByUser(updateUser);
        User afterUser = userMapper.selectByUserId(userId);

        //then
        assertEquals(result,1);
        assertEquals(userId,afterUser.getUserId());
        assertEquals(updateUser.getName(),afterUser.getName());
    }

}
