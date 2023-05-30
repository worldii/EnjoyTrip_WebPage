package com.ssafy.enjoytrip.hotPlace.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HotPlaceTag {
    private String tagName;
    private String hotPlaceId;
    private int count;
}
