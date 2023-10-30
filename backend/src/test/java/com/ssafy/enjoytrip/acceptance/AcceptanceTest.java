package com.ssafy.enjoytrip.acceptance;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/truncate.sql"})
public abstract class AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
    }
}
