package com.ssafy.enjoytrip.util;

import com.ssafy.enjoytrip.board.model.dto.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileUtil {
    // 확장자 추출
    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String findContentType(String contentType) {
        String[] mediaContentType = contentType.split("/");
        if (mediaContentType.length <= 0)
            throw new RuntimeException("파일 형식이 잘못되었습니다.");
        if (!(mediaContentType[0].toUpperCase().equals("IMAGE") || mediaContentType[0].toUpperCase().equals("VIDEO")))
            throw new RuntimeException("파일 형식이 잘못되었습니다.");
        return mediaContentType[0].toUpperCase();
    }

    public static String findFolder(String filename, String userName, String contentType) {
        String folder = "";
        folder += contentType + "/" + userName + "/" + contentType + "/" + filename;
        log.info("folder Name : " + folder);
        return folder;
    }

    public static String findExt(String fileName) {
        return fileName.split("\\.")[1];
    }
}
