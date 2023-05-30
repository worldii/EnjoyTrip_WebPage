package com.ssafy.enjoytrip.board.service;

import com.ssafy.enjoytrip.board.model.dto.FileInfo;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final BoardMapper boardMapper;
    private final S3Service s3Service;

    @Override
    public int insertFile(int boardId, List<MultipartFile> imageFiles,String folder) {
        // imageFile을 s3Service dml uploadtoS3 함수를 호출하여 url 을 가져온 다음 set 함 그리고 그것을
        // List<fileinfo> 로 변환
        List<FileInfo> list = new ArrayList<>();
        imageFiles.forEach(imageFile -> {
            try {
                String fileUrl = s3Service.uploadMediaToS3(imageFile, folder);
                list.add(new FileInfo(0, boardId, fileUrl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return boardMapper.insertFile(boardId, list);
    }

    @Override
    public List<FileInfo> selectFile(int boardId) {
        return boardMapper.selectFile(boardId);
    }

}
