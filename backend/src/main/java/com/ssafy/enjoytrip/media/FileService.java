package com.ssafy.enjoytrip.media;

import com.ssafy.enjoytrip.board.model.dto.FileInfo;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    int insertFile(Long boardId, List<MultipartFile> imageFiles, String folder);

    List<FileInfo> selectFile(Long boardId);
}


