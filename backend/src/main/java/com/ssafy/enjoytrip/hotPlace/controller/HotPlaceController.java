package com.ssafy.enjoytrip.hotPlace.controller;

import com.ssafy.enjoytrip.board.service.FileService;
import com.ssafy.enjoytrip.board.service.S3Service;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlace;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlaceArticle;
import com.ssafy.enjoytrip.hotPlace.model.dto.HotPlaceTag;
import com.ssafy.enjoytrip.hotPlace.model.dto.TagType;
import com.ssafy.enjoytrip.hotPlace.service.HotPlaceService;

import static com.ssafy.enjoytrip.util.ApiUtil.ApiResult;
import static com.ssafy.enjoytrip.util.ApiUtil.success;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@CrossOrigin(origins = {"http://127.0.0.1:8080", "http://192.168.0.1:8080", "http://localhost:8080"})
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/hotplace")
public class HotPlaceController {
    private final HotPlaceService hotPlaceService;
    private final S3Service s3Service;

    @GetMapping
    public ApiResult<List<HotPlace>> getHotPlaceList() {
        List<HotPlace> hotPlaces = hotPlaceService.selectAllHotPlace();
        return success(hotPlaces);
    }

    @GetMapping("/search")
    public ApiResult<List<HotPlace>> getHotPlaceList(@RequestParam String keyword) {
        List<HotPlace> hotPlaces = hotPlaceService.selectHotPlaceByKeyword(keyword);
        return success(hotPlaces);
    }

    @GetMapping("/articleAll/{hotPlaceId}")
    public ApiResult<List<HotPlaceArticle>> getHotPlaceArticleList(@PathVariable String hotPlaceId) {
        List<HotPlaceArticle> hotPlaceArticles = hotPlaceService.selectAllHotPlaceArticle(hotPlaceId);
        return success(hotPlaceArticles);
    }
    @GetMapping("/{hotPlaceId}")
    public ApiResult<HotPlace> getHotPlaceDetail(@PathVariable String hotPlaceId) {
        HotPlace hotPlace = hotPlaceService.selectHotPlaceByHotPlaceId(hotPlaceId);
        return success(hotPlace);
    }
    @GetMapping("/article/{hotPlaceArticleId}")
    public ApiResult<HotPlaceArticle> getHotPlaceArticleList(@PathVariable int hotPlaceArticleId) {
        HotPlaceArticle hotPlaceArticle = hotPlaceService.selectHotPlaceArticleByArticleId(hotPlaceArticleId);
        return success(hotPlaceArticle);
    }

    // 파일 업로드
    @PostMapping("/article/{articleId}/flleUpload")
    public ApiResult<Boolean> uploadImagetoArticle(@PathVariable int articleId, @ModelAttribute List<MultipartFile> files) throws IOException {
        log.info("uploadImagetoArticle Controller", articleId, files);
        String url = s3Service.uploadMediaToS3(files.get(0), "hotplace/");
        hotPlaceService.updateHotPlaceArticleImage(articleId, url);
        return success(true);
    }

    @PostMapping("/{hotPlaceId}/vote")
    public ApiResult<Boolean> voteHotPlace(@PathVariable String hotPlaceId) {
        hotPlaceService.increaseHitHotPlaceCount(hotPlaceId);
        return success(true);
    }
    @PostMapping("/{hotPlaceId}/unvote")
    public ApiResult<Boolean> unvoteHotPlace(@PathVariable String hotPlaceId) {
        hotPlaceService.decreaseHitHotPlaceCount(hotPlaceId);
        return success(true);
    }

    @PostMapping
    public ApiResult<Integer> addHotPlace(@RequestBody HotPlace hotPlace) {
        log.info("addHotPlace Controller");
        int pk = hotPlaceService.insertHotPlace(hotPlace);
        // tagtype 의 모든 종류를 1로 초기화
        // tagType 의 tagName 을 List<String> tagList 로 만듦

        for (TagType tagType : TagType.values()) {
            hotPlaceService.insertHotPlaceTag(hotPlace.getHotPlaceId(), tagType.getTagName());
        }
        return success(pk);
    }
    @PostMapping("/article")
    public ApiResult<Integer> addHotPlaceArticle(@RequestBody HotPlaceArticle hotPlaceArticle) {
        int pk = hotPlaceService.insertHotPlaceArticle(hotPlaceArticle);
        return success(pk);
    }

    @PostMapping("/{hotPlaceId}/tag")
    public ApiResult<Boolean> addHotPlaceTag(@PathVariable String hotPlaceId, @RequestBody List<String> tagList) {
        hotPlaceService.insertHotPlaceTagList(hotPlaceId, tagList);
        return success(true);
    }
    @PutMapping("/{hotPlaceId}/tag")
    public ApiResult<Boolean> updateHotPlaceTag(@PathVariable String hotPlaceId, @RequestBody List<String> tagList) {
        hotPlaceService.updateHotPlaceTagList(hotPlaceId, tagList);
        return success(true);
    }
    @GetMapping("/{hotPlaceId}/tag")
    public ApiResult<List<HotPlaceTag>> getHotPlaceTagList(@PathVariable String hotPlaceId) {
        List<HotPlaceTag> hotPlaceTags = hotPlaceService.selectHotPlaceTagList(hotPlaceId);
        return success(hotPlaceTags);
    }
}
