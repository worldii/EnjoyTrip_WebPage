package com.ssafy.enjoytrip.domain.hotplace;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.hotplace.model.mapper.HotPlaceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class hotPlaceInserTest {

    @Autowired
    private HotPlaceMapper hotPlaceMapper;


    @Test
    public void loadTest() {
        assertNotNull(hotPlaceMapper);
    }
}
