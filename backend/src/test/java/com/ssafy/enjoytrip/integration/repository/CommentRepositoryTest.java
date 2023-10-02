package com.ssafy.enjoytrip.integration.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ssafy.enjoytrip.core.board.dao.CommentRepository;
import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("CommentRepository 통합 테스트")
@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Comment를 정상적으로 생성하는 테스트")
    void createComment() {
        // given
        Comment comment = Comment.builder()
            .boardId(1L)
            .userId("test")
            .content("test")
            .build();

        // when
        Long commentId = commentRepository.insertComment(comment);

        // then
        Comment createdComment = commentRepository
            .selectComment(commentId)
            .orElseThrow(RuntimeException::new);
        assertThat(createdComment.getCommentId()).isEqualTo(commentId);
    }

    @Test
    @Sql(scripts = {
        "/truncate.sql",
        "/comment.sql"
    })
    @DisplayName("BoardId를 통해 Comment를 정상적으로 조회하는 테스트")
    void selectComment() {
        // given
        Long boardId = 1L;

        // when
        List<Comment> comments = commentRepository.selectAll(boardId);

        // then
        assertThat(comments.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("CommentId에 해당하는 Comment를 정상적으로 조회한다.")
    void selectCommentById() {
        // given
        Long commentId = 1L;

        // when
        Comment comment = commentRepository
            .selectComment(commentId)
            .orElseThrow(RuntimeException::new);

        // then
        assertThat(comment.getCommentId()).isEqualTo(commentId);
    }

    @Test
    @DisplayName("comment를 삭제한다.")
    void deleteComment() {
        // given
        Long commentId = 1L;

        // when
        commentRepository.deleteComment(commentId);

        // then
        assertThat(commentRepository.selectComment(commentId)).isEmpty();
    }

    @Test
    @DisplayName("comment를 수정한다.")
    void updateComment() {
        // given
        Long commentId = 1L;
        String content = "update";
        Comment updateComment = Comment.builder()
            .commentId(commentId)
            .boardId(1L)
            .userId("test")
            .content(content)
            .build();

        // when
        commentRepository.updateComment(updateComment);

        // then
        Comment newComment = commentRepository
            .selectComment(commentId)
            .orElseThrow(RuntimeException::new);
        assertThat(newComment).extracting("content").isEqualTo(content);
    }

    @Test
    @DisplayName("comment를 모두 삭제한다.")
    void deleteAllComment() {
        // given
        Long boardId = 1L;

        // when
        commentRepository.deleteAll(boardId);

        // then
        assertThat(commentRepository.selectAll(boardId).size()).isZero();
    }
}
