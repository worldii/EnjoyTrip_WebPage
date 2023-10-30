package com.ssafy.enjoytrip.core.hotplace.model.dto.request;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotPlaceSearchRequest {

    private int page = 1;
    private int pageSize = 10;
    private String keyword;
}
