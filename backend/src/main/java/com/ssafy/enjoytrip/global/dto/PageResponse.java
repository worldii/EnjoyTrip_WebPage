package com.ssafy.enjoytrip.global.dto;

import com.ssafy.enjoytrip.global.util.PaginationHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

    private PaginationHelper page;

    public static PageResponse from(final PaginationHelper page) {
        return new PageResponse(page);
    }
}
