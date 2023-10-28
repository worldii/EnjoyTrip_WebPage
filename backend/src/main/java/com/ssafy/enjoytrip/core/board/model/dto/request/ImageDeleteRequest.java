package com.ssafy.enjoytrip.core.board.model.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDeleteRequest {

    @NotBlank(message = "파일의 url을 입력해주세요")
    private List<String> fileUrls;
}
