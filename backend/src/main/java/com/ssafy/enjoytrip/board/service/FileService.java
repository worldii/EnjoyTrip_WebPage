package com.ssafy.enjoytrip.board.service;

import com.ssafy.enjoytrip.board.model.dto.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileService {
    int insertFile (int boardId, List<MultipartFile> imageFiles,String folder);
    List<FileInfo> selectFile(int boardId);
}


