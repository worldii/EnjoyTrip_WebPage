package com.ssafy.enjoytrip.media.service;

import com.ssafy.enjoytrip.media.FileInfo;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    void insertFile(final Long boardId, final List<MultipartFile> imageFiles, final String folder);

    List<FileInfo> selectFile(final Long boardId);
}


