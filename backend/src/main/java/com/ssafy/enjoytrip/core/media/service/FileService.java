package com.ssafy.enjoytrip.core.media.service;

import com.ssafy.enjoytrip.core.media.model.dto.FileInfoResponse;
import java.util.List;


public interface FileService {

    void insertFile(final Long boardId, final String userId, final List<String> fileUrls);

    List<FileInfoResponse> selectFile(final Long boardId);

    void deleteFile(final Long boardId);

    void modifyFile(Long boardId, String userId, List<String> fileUrls);
}


