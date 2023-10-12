package com.ssafy.enjoytrip.core.board.model.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDeleteRequest {

    private List<String> fileUrls;
}
