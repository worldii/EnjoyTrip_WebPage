package com.ssafy.enjoytrip.user;

import com.ssafy.enjoytrip.user.model.dto.User;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import com.ssafy.enjoytrip.user.service.UserServiceImpl;
import com.ssafy.enjoytrip.util.UserEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserMapper userMapper;

    @Test
    public void testLoadUserService(){

        // given - when - then
        System.out.println(userService);
        System.out.println(userMapper);

        assertNotNull(userService);
        assertNotNull(userMapper);
    }

    @Test
    @Transactional
    public void testJoin(){
        // given
        String userId = "test";
        User user = createUserRequest(userId);

        // when
        int result = userService.join(user);
        User selectedUser = userMapper.selectByUserId(userId);

        // then
        assertEquals(result,1);
        assertEquals(userId,selectedUser.getUserId());
    }

    @Test
    @Transactional
    public void testLoginSuccess(){
        // given
        String userId = "test";
        User user = createUserRequest(userId);
        String correctPassword = new String(user.getPassword());

        userService.join(user);

        // when
        User loginUser = userService.login(userId,correctPassword);

        //
        System.out.println("loginUser = " + loginUser);
        assertNotNull(loginUser);
    }

    @Test
    @Transactional
    public void testLoginFail(){
        // given
        String joinUserId = "test";
        String trashUserId = "trash";

        User user = createUserRequest(joinUserId);
        String trashPassword = new String(user.getPassword()+"trash");

        userService.join(user);

        // when
        User trashPasswordUser = userService.login(joinUserId,trashPassword);
        User noUser = userService.login(trashUserId,trashPassword);

        // then
        assertNull(trashPasswordUser);
        assertNull(noUser);
    }

    private User createUserRequest(String key) {
        String userId = key;
        String password = key;
        String name = key;
        String email = key + "@" + key;
        String address = key;

        User user = User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .address(address).build();

        return user;
    }
}
