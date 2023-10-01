package com.ssafy.enjoytrip.board.model.dto.request;

import com.ssafy.enjoytrip.board.model.entity.BoardType;
import com.ssafy.enjoytrip.media.model.entity.FileInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BoardSaveRequest {

    private String subject;
    private String content;
    private BoardType boardType;
    private List<FileInfo> imageFiles;
}
