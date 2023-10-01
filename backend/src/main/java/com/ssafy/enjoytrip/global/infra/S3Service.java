package com.ssafy.enjoytrip.global.infra;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.enjoytrip.global.error.MediaException;
import com.ssafy.enjoytrip.media.model.FileUrlResponse;
import com.ssafy.enjoytrip.media.model.ImageFiles;
import com.ssafy.enjoytrip.media.service.UploadService;
import com.ssafy.enjoytrip.media.util.FileUtil;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("!test")
public class S3Service implements UploadService {

    private final AmazonS3 amazonS3;
    private final String bucket;

    public S3Service(
        @Value("${cloud.aws.s3.bucket}") final String bucket,
        final AmazonS3 amazonS3
    ) {
        this.bucket = bucket;
        this.amazonS3 = amazonS3;
    }

    @Override
    public List<FileUrlResponse> uploadMedias(
        final List<MultipartFile> files,
        final String folderName
    ) {
        final ImageFiles images = new ImageFiles(files);

        final List<String> fileUrls = images.getImages().stream()
            .map(imageFile -> uploadMediaToS3(imageFile, folderName))
            .collect(Collectors.toList());

        return fileUrls.stream()
            .map(FileUrlResponse::new)
            .collect(Collectors.toList());
    }

    private String uploadMediaToS3(final MultipartFile file, final String folderName) {
        final String fullFileUrl = FileUtil.getFullFileUrl(folderName, file.getOriginalFilename());

        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        try {
            amazonS3.putObject(
                new PutObjectRequest(
                    bucket, fullFileUrl,
                    file.getInputStream(), metadata
                ).withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new MediaException("파일 업로드에 실패했습니다.");
        }

        return amazonS3.getUrl(bucket, fullFileUrl).toString();
    }


    public void deleteMedias(final List<String> fileUrls) {
        fileUrls.forEach(this::deleteMediaFromS3);
    }

    // TODO : S3 delete 시키는 로직 추가하여야 함
    private void deleteMediaFromS3(final String fileUrl) {
        final String fileName = fileUrl.split("/")[3];
        amazonS3.deleteObject(bucket, fileName);
    }
}
