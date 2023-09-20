package com.ssafy.enjoytrip.board.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoRequest {
    int page;
    int pageSize;

}
