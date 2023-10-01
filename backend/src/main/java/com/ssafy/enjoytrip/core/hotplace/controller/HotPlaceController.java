package com.ssafy.enjoytrip.core.hotplace.controller;

import com.ssafy.enjoytrip.core.hotplace.model.dto.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.TagType;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import com.ssafy.enjoytrip.core.hotplace.service.HotPlaceService;
import com.ssafy.enjoytrip.core.media.model.FileUrlResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import com.ssafy.enjoytrip.infra.S3Service;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
@RequestMapping("/hotplace")
public class HotPlaceController {

    private final HotPlaceService hotPlaceService;
    private final S3Service s3Service;

    @NoAuth
    @GetMapping
    public ResponseEntity<List<HotPlaceResponse>> getHotPlaceList() {
        return ResponseEntity.ok(hotPlaceService.selectAllHotPlace());
    }

    @NoAuth
    @GetMapping("/search")
    public ResponseEntity<List<HotPlace>> getHotPlaceList(@RequestParam final String keyword) {
        return ResponseEntity.ok(hotPlaceService.selectHotPlaceByKeyword(keyword));
    }

    @NoAuth
    @GetMapping("/articleAll/{hotPlaceId}")
    public ResponseEntity<List<HotPlaceArticleResponse>> getHotPlaceArticleList(
        @PathVariable final String hotPlaceId) {
        return ResponseEntity.ok(hotPlaceService.selectAllHotPlaceArticle(hotPlaceId));
    }

    @NoAuth
    @GetMapping("/{hotPlaceId}")
    public ResponseEntity<HotPlace> getHotPlaceDetail(@PathVariable final String hotPlaceId) {
        return ResponseEntity.ok(hotPlaceService.selectHotPlaceByHotPlaceId(hotPlaceId));
    }

    @NoAuth
    @GetMapping("/article/{hotPlaceArticleId}")
    public ResponseEntity<HotPlaceArticle> getHotPlaceArticleList(
        @PathVariable final int hotPlaceArticleId
    ) {
        return ResponseEntity.ok(
            hotPlaceService.selectHotPlaceArticleByArticleId(hotPlaceArticleId));
    }

    @PostMapping("/article/{articleId}/flleUpload")
    public ResponseEntity<Boolean> uploadImagetoArticle(
        @PathVariable final int articleId,
        @ModelAttribute final List<MultipartFile> files
    ) {
        final List<String> strings = s3Service.uploadMedias(files, "hotplace/")
            .stream()
            .map(FileUrlResponse::getUrl)
            .collect(Collectors.toList());

        hotPlaceService.updateHotPlaceArticleImage(articleId, strings.get(0));
        return ResponseEntity.ok(true);
    }

    @PostMapping("/{hotPlaceId}/vote")
    public ResponseEntity<Boolean> voteHotPlace(@PathVariable final String hotPlaceId) {
        hotPlaceService.increaseHitHotPlaceCount(hotPlaceId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/{hotPlaceId}/unvote")
    public ResponseEntity<Boolean> unvoteHotPlace(@PathVariable final String hotPlaceId) {
        hotPlaceService.decreaseHitHotPlaceCount(hotPlaceId);
        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<Integer> addHotPlace(@RequestBody final HotPlace hotPlace) {
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
        return ResponseEntity.ok(hotPlaceService.insertHotPlaceArticle(hotPlaceArticle));
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
