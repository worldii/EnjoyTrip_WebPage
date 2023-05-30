package com.ssafy.enjoytrip.hotPlace;

import com.ssafy.enjoytrip.hotPlace.model.mapper.HotPlaceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class hotPlaceInserTest {
    @Autowired
    private HotPlaceMapper hotPlaceMapper;


    @Test
    public void loadTest(){
        assertNotNull(hotPlaceMapper);
    }
}
