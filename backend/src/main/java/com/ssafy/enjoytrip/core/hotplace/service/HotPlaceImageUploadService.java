package com.ssafy.enjoytrip.core.hotplace.service;

import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceArticleRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceRepository;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.media.service.UploadService;
import com.ssafy.enjoytrip.core.user.dao.UserRepository;
import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.error.BoardException;
import com.ssafy.enjoytrip.global.error.HotPlaceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class HotPlaceImageUploadService {

    private final UploadService uploadService;
    private final HotPlaceRepository hotPlaceRepository;
    private final UserRepository userRepository;
    private final HotPlaceArticleRepository hotPlaceArticleRepository;

    public List<String> uploadHotPlaceImage(
        final List<MultipartFile> files,
        final String hotPlaceId
    ) {
        final HotPlace hotPlace = findHotPlaceByHotPlaceId(hotPlaceId);

        return uploadService.uploadMedias(files, "/hotplace/" + hotPlace.getHotPlaceId());
    }

    public List<String> uploadHotPlaceArticleImage(
        final List<MultipartFile> files,
        final Long hotPlaceArticleId,
        final String userId
    ) {
        final HotPlaceArticle hotPlaceArticle = findHotPlaceArticleById(hotPlaceArticleId);
        final User user = findUserByUserId(userId);

        validateUser(hotPlaceArticle.getUserId(), user.getUserId());

        return uploadService.uploadMedias(files,
            "/hotplace/article/" + hotPlaceArticle.getHotPlaceId());
    }

    private HotPlaceArticle findHotPlaceArticleById(Long hotPlaceArticleId) {
        return hotPlaceArticleRepository
            .selectHotPlaceArticleByArticleId(hotPlaceArticleId)
            .orElseThrow(() -> new HotPlaceException("존재하지 않는 게시글입니다."));
    }

    private HotPlace findHotPlaceByHotPlaceId(final String hotPlaceId) {
        return hotPlaceRepository
            .selectHotPlaceByHotPlaceId(hotPlaceId)
            .orElseThrow(() -> new HotPlaceException("존재하지 않는 게시글입니다."));
    }

    private User findUserByUserId(final String userId) {
        return userRepository.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));
    }

    private void validateUser(final String hotPlaceArticleUserId, final String userId) {
        if (!hotPlaceArticleUserId.equals(userId)) {
            throw new BoardException("해당 게시글에 대한 권한이 없습니다.");
        }
    }
}
