package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.board.model.dto.request.CommentModifyRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.CommentSaveRequest;
import com.ssafy.enjoytrip.core.board.model.dto.response.CommentResponse;
import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import com.ssafy.enjoytrip.core.board.service.CommentService;
import com.ssafy.enjoytrip.global.error.BoardException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("CommentService 통합 테스트")
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("Comment를 정상적으로 생성하는 테스트")
    void createComment() {
        // given
        Long boardId = 1L;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();

        // when
        Long commentId = commentService.save(commentSaveRequest, userId, boardId);

        // then
        assertThat(commentId).isNotNull();
    }


    @Test
    @DisplayName("boardId가 없을 경우, 정상적으로 생성되지 않는다.")
    void createCommentWithoutBoardId() {
        // given
        Long wrongBoardId = null;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();

        // when & then
        assertThatCode(() -> commentService.save(commentSaveRequest, userId, wrongBoardId))
            .isInstanceOf(BoardException.class)
            .hasMessageContaining("해당 boardId에 해당하는 board가 없습니다.");
    }


    @Test
    @DisplayName("userId가 없을 경우, 정상적으로 생성되지 않는다.")
    void createCommentWithoutUserId() {
        // given
        Long boardId = 1L;
        String wrongUserId = null;
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();

        // when & then
        assertThatCode(() -> commentService.save(commentSaveRequest, wrongUserId, boardId))
            .isInstanceOf(BoardException.class)
            .hasMessageContaining("해당 userId에 해당하는 user가 없습니다.");
    }

    @Test
    @DisplayName("commentId를 통해 Comment를 정상적으로 조회하는 테스트")
    @Sql(scripts = {
        "/truncate.sql",
        "/board.sql",
        "/user.sql"
    })
    void getCommentDetail() {
        // given
        Long boardId = 1L;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        Long commentId = commentService.save(commentSaveRequest, userId, boardId);

        // when
        Comment detail = commentService.detail(commentId);

        // then
        assertThat(detail).isNotNull();
    }

    @Test
    @DisplayName("commentId가 없을 경우, 정상적으로 조회되지 않는다.")
    void getCommentDetailWithoutCommentId() {
        // given
        Long wrongCommentId = null;

        // when & then
        assertThatCode(() -> commentService.detail(wrongCommentId))
            .isInstanceOf(BoardException.class)
            .hasMessageContaining("해당 commentId에 해당하는 comment가 없습니다.");
    }

    @Test
    @DisplayName("Comment를 정상적으로 수정하는 테스트")
    @Sql(scripts = {
        "/truncate.sql",
        "/board.sql",
        "/user.sql"
    })
    void modifyComment() {
        // given
        Long boardId = 1L;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        Long commentId = commentService.save(commentSaveRequest, userId, boardId);

        // when
        CommentModifyRequest commentModifyRequest = CommentModifyRequest.builder()
            .content("test2")
            .boardId(boardId)
            .build();
        commentService.modify(commentId, userId, commentModifyRequest);

        // then
        Comment detail = commentService.detail(commentId);
        assertThat(detail.getContent()).isEqualTo("test2");
    }


    @Test
    @DisplayName("Comment의 유저와 수정하려는 유저가 다를 경우, 정상적으로 수정되지 않는다.")
    @Sql(scripts = {
        "/truncate.sql",
        "/board.sql",
        "/user.sql"
    })
    void modifyCommentWithDifferentUser() {
        // given
        Long boardId = 1L;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        Long commentId = commentService.save(commentSaveRequest, userId, boardId);

        // when
        CommentModifyRequest commentModifyRequest = CommentModifyRequest.builder()
            .content("test2")
            .boardId(boardId)
            .build();
        String wrongUserId = "wrong";
        // then
        assertThatCode(() -> commentService.modify(commentId, wrongUserId, commentModifyRequest))
            .isInstanceOf(BoardException.class)
            .hasMessageContaining("해당 userId에 해당하는 user가 없습니다.");
    }

    @Test
    @DisplayName("수정하려는 BoardId가 없을 경우, 정상적으로 수정되지 않는다.")
    @Sql(scripts = {
        "/truncate.sql",
        "/board.sql",
        "/user.sql"
    })
    void modifyCommentWithoutBoardId() {
        // given
        Long boardId = 1L;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        Long commentId = commentService.save(commentSaveRequest, userId, boardId);

        // when
        CommentModifyRequest commentModifyRequest = CommentModifyRequest.builder()
            .content("test2")
            .boardId(null)
            .build();
        // then
        assertThatCode(() -> commentService.modify(commentId, userId, commentModifyRequest))
            .isInstanceOf(BoardException.class)
            .hasMessageContaining("해당 boardId에 해당하는 board가 없습니다.");
    }

    @Test
    @Sql(scripts = {
        "/truncate.sql",
        "/board.sql",
        "/user.sql"
    })
    @DisplayName("Comment를 정상적으로 삭제하는 테스트")
    void deleteComment() {
        // given
        Long boardId = 1L;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        Long commentId = commentService.save(commentSaveRequest, userId, boardId);

        // when
        commentService.delete(commentId, userId);

        // then
        assertThatCode(() -> commentService.detail(commentId))
            .isInstanceOf(BoardException.class)
            .hasMessageContaining("해당 commentId에 해당하는 comment가 없습니다.");
    }

    @Test
    @Sql(scripts = {
        "/truncate.sql",
        "/board.sql",
        "/user.sql"
    })
    @DisplayName("Comment의 유저와 삭제하려는 유저가 다를 경우, 정상적으로 삭제되지 않는다.")
    void deleteCommentWithDifferentUser() {
        // given
        Long boardId = 1L;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        Long commentId = commentService.save(commentSaveRequest, userId, boardId);

        // when
        String wrongUserId = "wrong";
        // then
        assertThatCode(() -> commentService.delete(commentId, wrongUserId))
            .isInstanceOf(BoardException.class)
            .hasMessageContaining("해당 userId에 해당하는 user가 없습니다.");
    }


    @Test
    @DisplayName("BoardId 에 해당하는 댓글을 모두 삭제한다")
    @Sql(scripts = {
        "/truncate.sql",
        "/board.sql",
        "/user.sql"
    })
    void deleteAllByBoardId() {
        // given
        Long boardId = 1L;
        String userId = "test";
        CommentSaveRequest commentSaveRequest = CommentSaveRequest.builder()
            .content("test")
            .build();
        commentService.save(commentSaveRequest, userId, boardId);

        // when
        commentService.deleteAll(boardId);

        // then
        List<CommentResponse> commentList = commentService.getCommentList(boardId);
        assertThat(commentList.size()).isZero();
    }
}
