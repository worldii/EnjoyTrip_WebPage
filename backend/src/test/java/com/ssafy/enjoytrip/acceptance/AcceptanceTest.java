package com.ssafy.enjoytrip.acceptance;


import com.ssafy.enjoytrip.config.RedisTestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(RedisTestConfig.class)
public abstract class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        redisConnectionFactory.getConnection().close();
    }
}
