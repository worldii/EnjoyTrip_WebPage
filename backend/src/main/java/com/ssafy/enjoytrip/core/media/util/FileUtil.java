package com.ssafy.enjoytrip.core.media.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    public static String getFullFileUrl(
        final String folderName,
        final String filename
    ) {
        return folderName + "/" + filename;
    }

    public String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String findExt(String fileName) {
        return fileName.split("\\.")[1];
    }
}
