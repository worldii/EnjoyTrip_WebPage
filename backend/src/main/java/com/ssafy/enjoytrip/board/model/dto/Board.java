package com.ssafy.enjoytrip.board.model.dto;

import com.ssafy.enjoytrip.media.FileInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    private Long boardId;

    private String userId;
    private String subject;
    private String content;

    private Long hit;
    private String currentUpdate;

    private BoardType boardType;
    private List<FileInfo> imageFiles = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    public void setFileInfos(List<FileInfo> fileInfos) {
        this.imageFiles = fileInfos;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
