package com.ssafy.enjoytrip.core.board.model.dto.response;


import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String userId;
    private String content;
    private String currentUpdate;

    public static CommentResponse from(final Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getCurrentUpdate());
    }
}
