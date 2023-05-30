package com.ssafy.enjoytrip.board.mapper;

import com.ssafy.enjoytrip.board.model.dto.*;
import com.ssafy.enjoytrip.board.model.mapper.CommentMapper;
import com.ssafy.enjoytrip.board.service.BoardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class CommentMapperInsertTest {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    BoardService boardService;


    @Test
    @DisplayName("Comment Mapper 오류 : commentRequest에 참조하는 boardId가 없을 때 foreign key 오류가 난다.")
    @Transactional
    public void insertCommentTest() {
        // given
        Comment comment = Comment.builder().boardId(2).userId(null).content("hello").build();
        // when, then
        Assertions.assertThrows(Exception.class, () -> {
            commentMapper.insertComment(comment);
        });
    }

    @Test
    @DisplayName("Comment Mapper 오류 : commentRequest의 userId가 없을 때")
    @Transactional
    public void insertCommentTest2() {
        // given
        Comment comment = Comment.builder().boardId(1).content("hello").build();
        // when then
        Assertions.assertThrows(Exception.class, () -> {
            commentMapper.insertComment(comment);
        });
    }

    @Test
    @DisplayName("Comment Mapper 정상 작동")
    @Transactional
    public void insertCommentTest4() {
        // given
        Comment comment = Comment.builder().boardId(1).userId("ssafy").content("hello").build();
        // when
        int res = commentMapper.insertComment(comment);
        // then
        Assertions.assertEquals(1, res);
    }

}
