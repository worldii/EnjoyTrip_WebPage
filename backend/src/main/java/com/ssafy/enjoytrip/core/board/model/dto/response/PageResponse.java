package com.ssafy.enjoytrip.core.board.model.dto.response;

import com.ssafy.enjoytrip.core.board.util.PageNavigationForPageHelper;
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
