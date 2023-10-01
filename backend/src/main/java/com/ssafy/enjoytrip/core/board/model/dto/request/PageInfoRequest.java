package com.ssafy.enjoytrip.core.board.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
