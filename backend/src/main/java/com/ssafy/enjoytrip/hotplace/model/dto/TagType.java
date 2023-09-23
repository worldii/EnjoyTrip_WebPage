package com.ssafy.enjoytrip.hotplace.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagType {
    ATMOSPHERE("분위기 좋은"),
    DELICIOUS("음식이 맛있는"),
    KINDSERVICE("친절한 서비스"),
    TERRACE("테라스/야외석"),
    NICEDESIGN("디자인이 멋진"),
    CHEAP("저렴한 가격"),
    SAFE("가성비 있는"),
    FAMOUSMENU("유명한 메뉴");
    private String tagName;
}
