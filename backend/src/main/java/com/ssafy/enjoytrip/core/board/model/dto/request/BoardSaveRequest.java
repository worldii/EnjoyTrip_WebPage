package com.ssafy.enjoytrip.core.board.model.dto.request;


import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BoardSaveRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String subject;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private BoardType boardType;
    private List<String> fileUrls;
}
