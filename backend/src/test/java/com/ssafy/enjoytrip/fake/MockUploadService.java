package com.ssafy.enjoytrip.fake;

import com.ssafy.enjoytrip.core.board.model.ImageFiles;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardImageUrlResponse;
import com.ssafy.enjoytrip.core.media.service.UploadService;
import com.ssafy.enjoytrip.core.media.util.FileUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("test")
public class MockUploadService implements UploadService {

    @Override
    public List<BoardImageUrlResponse> uploadMedias(
        final List<MultipartFile> multipartFiles,
        final String folderName
    ) {
        final ImageFiles images = new ImageFiles(multipartFiles);
        final List<String> fileUrls = getFileUrls(folderName, images);

        return fileUrls.stream()
            .map(BoardImageUrlResponse::new)
            .collect(Collectors.toList());
    }

    private List<String> getFileUrls(final String folderName, final ImageFiles images) {
        return images.getImages().stream()
            .map(imageFile -> FileUtil.getFullFileUrl(folderName, imageFile.getOriginalFilename()))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteMedias(List<String> fileUrls) {

    }
}
