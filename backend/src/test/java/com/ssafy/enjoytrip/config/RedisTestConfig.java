package com.ssafy.enjoytrip.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class RedisTestConfig {

    private GenericContainer<?> REDIS_CONTAINER;

    @BeforeEach
    void beforeAll() {
        REDIS_CONTAINER =
            new GenericContainer<>(DockerImageName.parse("redis:latest"))
                .withExposedPorts(6379);
        REDIS_CONTAINER.start();
        System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port",
            String.valueOf(REDIS_CONTAINER.getFirstMappedPort()));
    }

    @AfterEach
    void afterAll() {
        REDIS_CONTAINER.stop();
    }
}
