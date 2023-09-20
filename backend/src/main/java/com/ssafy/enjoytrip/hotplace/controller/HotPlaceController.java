package com.ssafy.enjoytrip.hotplace.controller;

import com.ssafy.enjoytrip.hotplace.model.dto.HotPlace;
import com.ssafy.enjoytrip.hotplace.model.dto.HotPlaceArticle;
import com.ssafy.enjoytrip.hotplace.model.dto.HotPlaceTag;
import com.ssafy.enjoytrip.hotplace.model.dto.TagType;
import com.ssafy.enjoytrip.hotplace.service.HotPlaceService;
import com.ssafy.enjoytrip.media.S3Service;
import com.ssafy.enjoytrip.user.model.dto.NoAuth;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = {"http://127.0.0.1:8080", "http://192.168.0.1:8080",
    "http://localhost:8080"})
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/hotplace")
public class HotPlaceController {

    private final HotPlaceService hotPlaceService;
    private final S3Service s3Service;

    @NoAuth
    @GetMapping
    public ResponseEntity<List<HotPlace>> getHotPlaceList() {
        List<HotPlace> hotPlaces = hotPlaceService.selectAllHotPlace();
        return ResponseEntity.ok(hotPlaces);
    }

    @NoAuth
    @GetMapping("/search")
    public ResponseEntity<List<HotPlace>> getHotPlaceList(@RequestParam String keyword) {
        List<HotPlace> hotPlaces = hotPlaceService.selectHotPlaceByKeyword(keyword);
        return ResponseEntity.ok(hotPlaces);
    }

    @NoAuth
    @GetMapping("/articleAll/{hotPlaceId}")
    public ResponseEntity<List<HotPlaceArticle>> getHotPlaceArticleList(
        @PathVariable String hotPlaceId) {
        List<HotPlaceArticle> hotPlaceArticles = hotPlaceService.selectAllHotPlaceArticle(
            hotPlaceId);
        return ResponseEntity.ok(hotPlaceArticles);
    }

    @NoAuth
    @GetMapping("/{hotPlaceId}")
    public ResponseEntity<HotPlace> getHotPlaceDetail(@PathVariable String hotPlaceId) {
        HotPlace hotPlace = hotPlaceService.selectHotPlaceByHotPlaceId(hotPlaceId);
        return ResponseEntity.ok(hotPlace);
    }

    @NoAuth
    @GetMapping("/article/{hotPlaceArticleId}")
    public ResponseEntity<HotPlaceArticle> getHotPlaceArticleList(
        @PathVariable int hotPlaceArticleId) {
        HotPlaceArticle hotPlaceArticle = hotPlaceService.selectHotPlaceArticleByArticleId(
            hotPlaceArticleId);
        return ResponseEntity.ok(hotPlaceArticle);
    }

    // 파일 업로드
    @PostMapping("/article/{articleId}/flleUpload")
    public ResponseEntity<Boolean> uploadImagetoArticle(@PathVariable int articleId,
        @ModelAttribute List<MultipartFile> files) throws IOException {
        log.info("uploadImagetoArticle Controller", articleId, files);
        String url = s3Service.uploadMediaToS3(files.get(0), "hotplace/");
        hotPlaceService.updateHotPlaceArticleImage(articleId, url);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/{hotPlaceId}/vote")
    public ResponseEntity<Boolean> voteHotPlace(@PathVariable String hotPlaceId) {
        hotPlaceService.increaseHitHotPlaceCount(hotPlaceId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/{hotPlaceId}/unvote")
    public ResponseEntity<Boolean> unvoteHotPlace(@PathVariable String hotPlaceId) {
        hotPlaceService.decreaseHitHotPlaceCount(hotPlaceId);
        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<Integer> addHotPlace(@RequestBody HotPlace hotPlace) {
        log.info("addHotPlace Controller");
        int pk = hotPlaceService.insertHotPlace(hotPlace);
        // tagtype 의 모든 종류를 1로 초기화
        // tagType 의 tagName 을 List<String> tagList 로 만듦

        for (TagType tagType : TagType.values()) {
            hotPlaceService.insertHotPlaceTag(hotPlace.getHotPlaceId(), tagType.getTagName());
        }
        return ResponseEntity.ok(pk);
    }

    @PostMapping("/article")
    public ResponseEntity<Integer> addHotPlaceArticle(
        @RequestBody HotPlaceArticle hotPlaceArticle) {
        int pk = hotPlaceService.insertHotPlaceArticle(hotPlaceArticle);
        return ResponseEntity.ok(pk);
    }

    @PostMapping("/{hotPlaceId}/tag")
    public ResponseEntity<Boolean> addHotPlaceTag(@PathVariable String hotPlaceId,
        @RequestBody List<String> tagList) {
        hotPlaceService.insertHotPlaceTagList(hotPlaceId, tagList);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{hotPlaceId}/tag")
    public ResponseEntity<Boolean> updateHotPlaceTag(@PathVariable String hotPlaceId,
        @RequestBody List<String> tagList) {
        hotPlaceService.updateHotPlaceTagList(hotPlaceId, tagList);
        return ResponseEntity.ok(true);
    }

    @NoAuth
    @GetMapping("/{hotPlaceId}/tag")
    public ResponseEntity<List<HotPlaceTag>> getHotPlaceTagList(@PathVariable String hotPlaceId) {
        List<HotPlaceTag> hotPlaceTags = hotPlaceService.selectHotPlaceTagList(hotPlaceId);
        return ResponseEntity.ok(hotPlaceTags);
    }
}
