package com.ssafy.enjoytrip.media.model;

import com.ssafy.enjoytrip.global.error.MediaException;
import java.util.List;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageFiles {

    private final List<MultipartFile> images;

    public ImageFiles(final List<MultipartFile> images) {
        validate(images);
        this.images = images;
    }

    private void validate(final List<MultipartFile> imageFiles) {
        if (imageFiles == null) {
            throw new MediaException("imageFiles은 null이 될 수 없습니다.");
        }

        for (final MultipartFile imageFile : imageFiles) {
            if (imageFile == null) {
                throw new MediaException("imageFile은 null이 될 수 없습니다.");
            }
        }
    }
}
