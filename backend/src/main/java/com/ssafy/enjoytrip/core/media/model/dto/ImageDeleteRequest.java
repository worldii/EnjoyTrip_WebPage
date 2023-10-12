package com.ssafy.enjoytrip.core.media.model.dto;

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
