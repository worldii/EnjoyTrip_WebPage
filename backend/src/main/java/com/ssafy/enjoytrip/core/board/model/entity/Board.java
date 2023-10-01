package com.ssafy.enjoytrip.core.board.model.entity;

import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
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

    public void setFileInfos(final List<FileInfo> fileInfos) {
        this.imageFiles = fileInfos;
    }

    public void setComments(final List<Comment> comments) {
        this.comments = comments;
    }
}
