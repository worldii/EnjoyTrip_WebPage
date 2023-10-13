package com.ssafy.enjoytrip.core.hotplace.service;

import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceArticleImageRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceArticleRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceTagRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceArticleSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSearchRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceVoteRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceDetailResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticleImage;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTag;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceTags;
import com.ssafy.enjoytrip.core.user.dao.UserRepository;
import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.error.HotPlaceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class HotPlaceServiceImpl implements HotPlaceService {

    private final HotPlaceRepository hotPlaceRepository;
    private final HotPlaceArticleRepository hotPlaceArticleRepository;
    private final HotPlaceTagRepository hotPlaceTagRepository;
    private final HotPlaceArticleImageRepository hotPlaceArticleImageRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public String insertHotPlace(final HotPlaceSaveRequest request) {
        final HotPlace hotPlace = HotPlace.builder()
            .hotPlaceId(request.getHotPlaceId())
            .hotPlaceName(request.getHotPlaceName())
            .imageUrl(request.getImageUrl())
            .x(request.getX())
            .y(request.getY())
            .addressName(request.getAddressName())
            .roadAddressName(request.getRoadAddressName())
            .build();

        hotPlaceRepository.insertHotPlace(hotPlace);

        return hotPlace.getHotPlaceId();
    }

    @Transactional
    @Override
    public Long insertHotPlaceArticle(
        final String hotPlaceId,
        final HotPlaceArticleSaveRequest request, final String userId
    ) {
        final HotPlace hotPlace = findHotPlaceByHotPlaceId(hotPlaceId);
        final User user = findUserByUserId(userId);
        final HotPlaceArticle hotPlaceArticle = HotPlaceArticle.builder()
            .hotPlaceId(hotPlace.getHotPlaceId())
            .hotPlaceName(request.getHotPlaceName())
            .content(request.getContent())
            .imageUrl(request.getImageUrl())
            .userId(user.getUserId())
            .build();

        hotPlaceArticleRepository.insertHotPlaceArticle(hotPlaceArticle);

        if (hotPlaceArticle.isImageUrlsNotEmpty()) {
            insertHotPlaceArticleImages(hotPlaceArticle);
        }
        insertHotPlaceTags(hotPlace.getHotPlaceId(), request.getTagName());

        return hotPlaceArticle.getHotPlaceArticleId();
    }

    private void insertHotPlaceArticleImages(final HotPlaceArticle hotPlaceArticle) {
        final List<HotPlaceArticleImage> imageInfos = hotPlaceArticle.getImageUrl().stream()
            .map(imageUrl -> HotPlaceArticleImage.builder()
                .hotPlaceArticleId(hotPlaceArticle.getHotPlaceArticleId())
                .imageUrl(imageUrl)
                .build())
            .collect(Collectors.toList());

        hotPlaceArticleImageRepository.insertFile(imageInfos);
    }

    private HotPlace findHotPlaceByHotPlaceId(final String hotPlaceId) {
        return hotPlaceRepository.selectHotPlaceByHotPlaceId(hotPlaceId)
            .orElseThrow(() -> new HotPlaceException("존재하지 않는 핫플레이스입니다."));
    }

    @Transactional(readOnly = true)
    @Override
    public List<HotPlaceResponse> selectAllHotPlace(
        final HotPlaceSearchRequest request
    ) {
        final List<HotPlace> hotPlaces = hotPlaceRepository.selectAllHotPlace(request.getKeyword());

        return hotPlaces.stream()
            .map(HotPlaceResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public HotPlaceDetailResponse selectAllByHotPlaceId(final String hotPlaceId) {
        HotPlace hotPlace = hotPlaceRepository.selectAllByHotPlaceId(hotPlaceId)
            .orElseThrow(() -> new HotPlaceException("존재하지 않는 핫플레이스입니다."));

        return HotPlaceDetailResponse.from(hotPlace);
    }

    @Override
    public HotPlaceArticleResponse selectHotPlaceArticleByArticleId(
        final String hotPlaceId,
        final Long articleId
    ) {
        final HotPlace hotPlace = findHotPlaceByHotPlaceId(hotPlaceId);
        final HotPlaceArticle hotPlaceArticle = findHotPlaceArticleById(articleId);

        validateHotPlaceArticle(hotPlace.getHotPlaceId(), hotPlaceArticle.getHotPlaceId());

        return HotPlaceArticleResponse.from(hotPlaceArticle);
    }

    @Override
    public void updateVoteCount(final String hotPlaceId, final HotPlaceVoteRequest voteRequest) {
        final HotPlace hotPlace = findHotPlaceByHotPlaceId(hotPlaceId);

        hotPlace.updateVoteCount(voteRequest.getVoteCount());

        hotPlaceRepository.updateHotPlace(hotPlace);
    }

    private void insertHotPlaceTags(final String hotPlaceId, final List<String> tagName) {
        if (tagName == null || tagName.isEmpty()) {
            return;
        }

        final List<HotPlaceTag> tagList = tagName.stream()
            .map(name -> hotPlaceTagRepository.selectByHotPlaceIdAndTagName(hotPlaceId, name))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        final HotPlaceTags hotPlaceTags = new HotPlaceTags(tagList, hotPlaceId, tagName);

        hotPlaceTagRepository.insertTags(hotPlaceTags.getTagList());
    }

    private User findUserByUserId(final String userId) {
        return userRepository.selectByUserId(userId)
            .orElseThrow(() -> new HotPlaceException("존재하지 않는 유저입니다."));
    }

    private void validateHotPlaceArticle(
        final String hotPlaceId,
        final String hotPlaceArticlePlaceId
    ) {
        if (!hotPlaceId.equals(hotPlaceArticlePlaceId)) {
            throw new HotPlaceException("핫플레이스 게시글이 아닙니다.");
        }
    }

    private HotPlaceArticle findHotPlaceArticleById(final Long articleId) {
        return hotPlaceArticleRepository.selectHotPlaceArticleByArticleId(articleId)
            .orElseThrow(() -> new HotPlaceException("존재하지 않는 핫플레이스 게시글입니다."));
    }
}
