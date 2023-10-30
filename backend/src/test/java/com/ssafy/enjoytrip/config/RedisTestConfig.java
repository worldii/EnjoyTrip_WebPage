package com.ssafy.enjoytrip.config;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class RedisTestConfig implements BeforeEachCallback, AfterEachCallback {

    private GenericContainer<?> REDIS_CONTAINER;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        REDIS_CONTAINER = new GenericContainer<>(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379);
        REDIS_CONTAINER.start();
        System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port",
            String.valueOf(REDIS_CONTAINER.getFirstMappedPort()));
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if (REDIS_CONTAINER != null) {
            REDIS_CONTAINER.stop();
        }
    }
}
