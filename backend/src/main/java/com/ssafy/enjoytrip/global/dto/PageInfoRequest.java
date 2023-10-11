package com.ssafy.enjoytrip.global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageInfoRequest {

    private int page = 1;
    private int pageSize = 10;

    public static PageInfoRequest from(final Integer currentPage) {
        if (currentPage == null) {
            return new PageInfoRequest();
        }

        return PageInfoRequest.builder()
            .page(currentPage)
            .build();
    }
}
