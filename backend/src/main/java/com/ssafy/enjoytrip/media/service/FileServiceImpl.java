package com.ssafy.enjoytrip.media.service;

import com.ssafy.enjoytrip.media.FileInfo;
import com.ssafy.enjoytrip.media.model.mapper.FileMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final S3Service s3Service;

    @Override
    public void insertFile(
        final Long boardId,
        final List<MultipartFile> imageFiles,
        final String folder
    ) {

        if (imageFiles == null) {
            return;
        }

        final List<FileInfo> fileInfos = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            try {
                final String fileUrl = s3Service.uploadMediaToS3(imageFile, folder);
                final FileInfo fileInfo = FileInfo.builder()
                    .boardId(boardId)
                    .fileUrl(fileUrl)
                    .build();
                fileInfos.add(fileInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileMapper.insertFile(boardId, fileInfos);
    }

    @Override
    public List<FileInfo> selectFile(Long boardId) {
        return fileMapper.selectFile(boardId);
    }
}
