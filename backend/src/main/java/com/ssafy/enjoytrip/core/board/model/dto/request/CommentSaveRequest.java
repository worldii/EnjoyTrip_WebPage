package com.ssafy.enjoytrip.core.board.model.dto.request;


import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentSaveRequest {

    @NotBlank(message = "게시판 번호를 입력해주세요")
    private String content;
}
