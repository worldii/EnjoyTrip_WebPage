package com.ssafy.enjoytrip.fake;

import com.ssafy.enjoytrip.core.media.service.ImageUploader;
import com.ssafy.enjoytrip.core.media.util.FileUtil;
import com.ssafy.enjoytrip.infra.model.ImageFiles;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("test")
public class MockImageUploader implements ImageUploader {

    @Override
    public List<String> uploadMedias(
        final List<MultipartFile> multipartFiles,
        final String folderName
    ) {
        final ImageFiles images = new ImageFiles(multipartFiles);

        return getFileUrls(folderName, images);
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
