package com.ssafy.enjoytrip.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class RedisTestConfig {

    static {
        final GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>(DockerImageName.parse("redis:latest"))
                .withExposedPorts(6379);
        REDIS_CONTAINER.start();

        System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port",
            String.valueOf(REDIS_CONTAINER.getFirstMappedPort()));
    }

    private final String host;
    private final String port;

    public RedisTestConfig(
        @Value("${spring.redis.host}") final String host,
        @Value("${spring.redis.port}") final String port
    ) {
        this.host = host;
        this.port = port;
    }
}
