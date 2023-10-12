package com.ssafy.enjoytrip.core.board.model.dto.request;

import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
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

    private String subject;
    private String content;
    private BoardType boardType;
    private List<String> imageUrls;
}
