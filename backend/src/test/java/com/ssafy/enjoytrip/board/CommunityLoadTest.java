package com.ssafy.enjoytrip.board;

import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommunityLoadTest {
    @Autowired
    CommentMapper commentMapper;

    @Test
    void testLoadCommunityMapper(){

    }
}
