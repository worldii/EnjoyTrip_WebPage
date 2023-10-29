package com.ssafy.enjoytrip.core.hotplace.model.dto.request;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class HotPlaceImageSaveRequest {

    private List<MultipartFile> files;
}
