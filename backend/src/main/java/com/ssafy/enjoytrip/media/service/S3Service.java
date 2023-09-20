package com.ssafy.enjoytrip.media.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    // private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String uploadMediaToS3(final MultipartFile file, final String folder) {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

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
