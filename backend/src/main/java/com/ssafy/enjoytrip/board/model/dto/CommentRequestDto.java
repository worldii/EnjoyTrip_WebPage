package com.ssafy.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private int commentId;
    @NotBlank(message = "게시글 입력이 비어있습니다.")
    private int boardId;
    @NotBlank(message = "유저 입력이 비어있습니다.")
    private String userId;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Comment toEntity() {
        return Comment.builder()
                .commentId(commentId)
                .boardId(boardId)
                .userId(userId)
                .content(content)
                .build();
    }

}
