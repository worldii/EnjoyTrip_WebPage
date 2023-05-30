package com.ssafy.enjoytrip.board.mapper;

import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentMapperTest {

    @Autowired
    CommentMapper commentMapper;

    @Test
    @DisplayName("빈 등록 되었는지 확인")
    public void loadTest() {
        Assertions.assertNotNull(commentMapper);
    }
}
