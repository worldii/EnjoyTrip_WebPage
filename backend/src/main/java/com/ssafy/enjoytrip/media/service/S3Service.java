package com.ssafy.enjoytrip.media.service;


import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    // private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String uploadMediaToS3(MultipartFile file, String folder) throws IOException {
        String fileName = file.getOriginalFilename();
        log.info("File name: " + fileName);

        String contentType = file.getContentType();
        log.info("Content Type: " + contentType);

//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(contentType);
//        amazonS3.putObject(
//                new PutObjectRequest(bucket, folder+ fileName, file.getInputStream(),
//                        metadata).withCannedAcl(
//                        CannedAccessControlList.PublicRead));
//        return amazonS3.getUrl(bucket, folder+fileName).toString();
        return "";
    }
}
