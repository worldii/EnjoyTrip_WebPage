package com.ssafy.enjoytrip.core.board.model.dto.request;


import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class BoardImageSaveRequest {

    @NotBlank(message = "파일을 첨부해주세요")
    private List<MultipartFile> files;
}
