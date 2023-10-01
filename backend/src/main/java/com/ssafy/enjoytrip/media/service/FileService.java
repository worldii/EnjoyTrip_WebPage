package com.ssafy.enjoytrip.media.service;

import com.ssafy.enjoytrip.media.model.dto.FileInfoResponse;
import java.util.List;


public interface FileService {

    void insertFile(final Long boardId, final List<String> fileUrls);

    List<FileInfoResponse> selectFile(final Long boardId);

    void deleteFile(final Long boardId);
}


