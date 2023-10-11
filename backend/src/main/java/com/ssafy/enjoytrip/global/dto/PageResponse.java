package com.ssafy.enjoytrip.global.dto;

import com.ssafy.enjoytrip.infra.PageNavigationForPageHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

    private PageNavigationForPageHelper page;

    public static PageResponse from(final PageNavigationForPageHelper page) {
        return new PageResponse(page);
    }
}
