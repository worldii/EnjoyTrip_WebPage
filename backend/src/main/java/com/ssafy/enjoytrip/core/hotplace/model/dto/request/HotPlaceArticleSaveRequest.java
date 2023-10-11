package com.ssafy.enjoytrip.core.hotplace.model.dto.request;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class HotPlaceArticleSaveRequest {

    private String hotPlaceName;
    private String content;
    private String imageUrl;
    private List<String> tagName;
}
