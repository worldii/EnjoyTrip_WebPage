package com.ssafy.enjoytrip.core.media.service;


import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    List<String> uploadMedias(final List<MultipartFile> multipartFiles, final String folderName);

    void deleteMedias(final List<String> fileUrls);
}
