package com.ssafy.enjoytrip.media.service;

import com.ssafy.enjoytrip.media.model.entity.FileInfo;
import java.util.List;


public interface FileService {

    void insertFile(final Long boardId, final List<String> fileUrls);

    List<FileInfo> selectFile(final Long boardId);

    void deleteFile(final Long boardId);
}


