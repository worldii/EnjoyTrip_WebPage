package com.ssafy.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardType {
    COMMUNITY("community"), NOTICE("notice"),
    ;
    
    private String type;
}
