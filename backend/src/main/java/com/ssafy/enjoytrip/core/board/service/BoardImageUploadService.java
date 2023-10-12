package com.ssafy.enjoytrip.core.board.service;

import com.ssafy.enjoytrip.core.board.dao.BoardImageRepository;
import com.ssafy.enjoytrip.core.board.dao.BoardRepository;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardImageUrlResponse;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import com.ssafy.enjoytrip.core.media.service.UploadService;
import com.ssafy.enjoytrip.core.user.dao.UserRepository;
import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.error.BoardException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardImageUploadService {

    private final UploadService uploadService;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardImageRepository boardImageRepository;

    public List<BoardImageUrlResponse> uploadMedias(
        final List<MultipartFile> files,
        final Long boardId,
        final String userId
    ) {
        final Board board = findBoardByBoardId(boardId);
        final User user = findUserByUserId(userId);

        validateUser(board.getUserId(), user.getUserId());
        final List<String> imageUrls = uploadService.uploadMedias(
            files, "/board/" + board.getBoardId());

        return imageUrls.stream()
            .map(BoardImageUrlResponse::new)
            .collect(Collectors.toList());
    }

    public List<BoardImageUrlResponse> modifyMedias(
        final List<MultipartFile> files,
        final Long boardId,
        final String userId
    ) {
        deleteMedias(boardId, userId);
        return uploadMedias(files, boardId, userId);
    }

    public void deleteMedias(final Long boardId, final String userId) {
        final User user = findUserByUserId(userId);
        final Board board = findBoardByBoardId(boardId);

        validateUser(board.getUserId(), user.getUserId());

        final List<String> imageUrls = boardImageRepository
            .selectFileByBoardId(boardId)
            .stream()
            .map(BoardImageInfo::getImageUrl)
            .collect(Collectors.toList());
        uploadService.deleteMedias(imageUrls);
    }

    private void validateUser(final String boardUserId, final String userId) {
        if (!boardUserId.equals(userId)) {
            throw new BoardException("해당 게시글의 작성자가 아닙니다.");
        }
    }

    private Board findBoardByBoardId(final Long boardId) {
        return boardRepository.selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 Board가 없습니다."));
    }

    private User findUserByUserId(final String userId) {
        return userRepository.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));
    }
}
