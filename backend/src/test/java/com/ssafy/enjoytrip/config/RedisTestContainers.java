package com.ssafy.enjoytrip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@Configuration
@Testcontainers
public class RedisTestContainers {

    private static final String REDIS_DOCKER_IMAGE = "redis:latest";

    static {
        GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>(DockerImageName.parse(REDIS_DOCKER_IMAGE))
                .withExposedPorts(6379)
                .withReuse(true); // 이전에 사용한 컨테이너 재사용 (컨테이너 재사용을 위해 withReuse(true) 추가
        REDIS_CONTAINER.start();
        System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
    }
}
