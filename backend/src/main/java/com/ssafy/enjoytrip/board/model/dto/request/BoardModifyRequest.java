package com.ssafy.enjoytrip.board.model.dto.request;

import com.ssafy.enjoytrip.board.model.dto.BoardType;
import com.ssafy.enjoytrip.board.model.dto.FileInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardModifyRequest {

    private Long boardId;
    private String subject;
    private String content;
    private String userId;
    private BoardType boardType;
    private List<FileInfo> imageFiles;
}
