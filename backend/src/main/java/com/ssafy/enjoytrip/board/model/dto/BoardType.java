package com.ssafy.enjoytrip.board.model.dto;

public enum BoardType {
    COMMUNITY("community"), NOTICE("notice");
    String type ;

    BoardType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
