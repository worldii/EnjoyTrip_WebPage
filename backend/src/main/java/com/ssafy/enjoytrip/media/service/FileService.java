package com.ssafy.enjoytrip.media.service;

import com.ssafy.enjoytrip.media.FileInfo;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    void insertFile(Long boardId, List<MultipartFile> imageFiles, String folder);

    List<FileInfo> selectFile(Long boardId);
}


