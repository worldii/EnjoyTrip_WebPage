package com.ssafy.enjoytrip.core.hotplace.controller;

import com.ssafy.enjoytrip.core.hotplace.service.HotPlaceImageUploadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotplace/image")
public class HotPlaceImageController {

    private final HotPlaceImageUploadService hotPlaceImageService;

    @PostMapping("{hotPlaceId}")
    public ResponseEntity<List<String>> uploadHotPlaceImage(
        @PathVariable final String hotPlaceId,
        @RequestParam final List<MultipartFile> files
    ) {
        return ResponseEntity.ok(hotPlaceImageService.uploadHotPlaceImage(files, hotPlaceId));
    }

    @PostMapping("/article/{articleId}")
    public ResponseEntity<List<String>> uploadHotPlaceArticleImage(
        @PathVariable final Long articleId,
        @RequestParam final List<MultipartFile> files,
        @RequestParam final String userId
    ) {
        final List<String> imageUrls = hotPlaceImageService
            .uploadHotPlaceArticleImage(files, articleId, userId);

        return ResponseEntity.ok(imageUrls);
    }
}
