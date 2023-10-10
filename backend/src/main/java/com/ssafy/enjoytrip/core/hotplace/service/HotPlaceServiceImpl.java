package com.ssafy.enjoytrip.core.hotplace.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceArticleRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dao.HotPlaceTagRepository;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceArticleSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceVoteRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlace;
import com.ssafy.enjoytrip.core.hotplace.model.entity.HotPlaceArticle;
import com.ssafy.enjoytrip.core.user.dao.UserRepository;
import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.error.HotPlaceException;
import com.ssafy.enjoytrip.global.error.PageInfoRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class HotPlaceServiceImpl implements HotPlaceService {

    private final HotPlaceRepository hotPlaceRepository;
    private final HotPlaceArticleRepository hotPlaceArticleRepository;
    private final HotPlaceTagRepository hotPlaceTagRepository;
    private final UserRepository userRepository;

    @Override
    public String insertHotPlace(final HotPlaceSaveRequest request) {
        final HotPlace hotPlace = HotPlace.builder()
            .hotPlaceId(request.getHotPlaceId())
            .hotPlaceName(request.getHotPlaceName())
            .placeUrl(request.getPlaceUrl())
            .x(request.getX())
            .y(request.getY())
            .addressName(request.getAddressName())
            .roadAddressName(request.getRoadAddressName())
            .build();

        hotPlaceRepository.insertHotPlace(hotPlace);

        return hotPlace.getHotPlaceId();
    }

    @Override
    public Long insertHotPlaceArticle(
        final String hotPlaceId,
        final HotPlaceArticleSaveRequest request,
        final String userId
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

        final List<Long> tagIds = getTagIdsByTagName(hotPlaceId, request.getTagName());
        hotPlaceArticleRepository.insertHotPlaceArticle(hotPlaceArticle);
        hotPlaceTagRepository.insertHotPlaceTagList(hotPlaceId, tagIds);
        return hotPlaceArticle.getHotPlaceArticleId();
    }

    private List<Long> getTagIdsByTagName(String hotPlaceId, List<String> tagName) {
        return tagName.stream()
            .map(name -> hotPlaceTagRepository.selectHotPlaceTagIdByTagNameAndHotPlaceId(name,
                hotPlaceId))
            .collect(Collectors.toList());
    }

    @Override
    public List<HotPlaceResponse> selectAllHotPlace(
        final PageInfoRequest pageInfoRequest, final String keyword) {
        PageHelper.startPage(pageInfoRequest.getPage(), pageInfoRequest.getPageSize());
        final Page<HotPlace> hotPlaces = hotPlaceRepository.selectAllHotPlace(keyword);

        return hotPlaces.stream()
            .map(HotPlaceResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public HotPlaceResponse selectHotPlaceByHotPlaceId(final String hotPlaceId) {
        return HotPlaceResponse.from(findHotPlaceByHotPlaceId(hotPlaceId));
    }

    @Override
    public HotPlaceArticleResponse selectHotPlaceArticleByArticleId(
        final String hotPlaceId, final Long articleId
    ) {
        final HotPlace hotPlace = findHotPlaceByHotPlaceId(hotPlaceId);
        final HotPlaceArticle hotPlaceArticle = findHotPlaceArticleById(articleId);

        validateHotPlaceArticle(hotPlace.getHotPlaceId(), hotPlaceArticle.getHotPlaceId());

        return HotPlaceArticleResponse.from(hotPlaceArticle);
    }

    @Override
    public void updateVoteCount(final String hotPlaceId, final HotPlaceVoteRequest voteRequest
    ) {
        final HotPlace hotPlace = findHotPlaceByHotPlaceId(hotPlaceId);

        hotPlace.updateVoteCount(voteRequest.getVoteCount());

        hotPlaceRepository.updateHotPlace(hotPlace);
    }


    @Override
    public int updateHotPlaceArticleImage(final int hotPlaceArticleId, final String imageUrl) {
        return hotPlaceArticleRepository.updateHotPlaceArticleImage(hotPlaceArticleId, imageUrl);
    }

    @Override
    public int updateHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceTagRepository.updateHotPlaceTag(hotPlaceId, tagName);
    }

    @Override
    public void updateHotPlaceTagList(final String hotPlaceId, final List<String> tagIdList) {
        tagIdList.forEach(tagId -> updateHotPlaceTag(hotPlaceId, tagId));
    }

    @Override
    public int insertHotPlaceTag(final String hotPlaceId, final String tagName) {
        return hotPlaceTagRepository.insertHotPlaceTag(hotPlaceId, tagName);
    }

    private HotPlace findHotPlaceByHotPlaceId(String hotPlaceId) {
        return hotPlaceRepository.selectAllByHotPlaceId(hotPlaceId)
            .orElseThrow(() -> new HotPlaceException("존재하지 않는 핫플레이스입니다."));
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
