package com.ssafy.enjoytrip.media;

import com.ssafy.enjoytrip.board.model.dto.FileInfo;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final BoardMapper boardMapper;
    private final S3Service s3Service;

    @Override
    public int insertFile(final Long boardId, final List<MultipartFile> imageFiles,
        final String folder) {
        
        List<FileInfo> list = new ArrayList<>();
        imageFiles.forEach(imageFile -> {
            try {
                String fileUrl = s3Service.uploadMediaToS3(imageFile, folder);
                list.add(new FileInfo(0L, boardId, fileUrl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return boardMapper.insertFile(boardId, list);
    }

    @Override
    public List<FileInfo> selectFile(Long boardId) {
        return boardMapper.selectFile(boardId);
    }
}
