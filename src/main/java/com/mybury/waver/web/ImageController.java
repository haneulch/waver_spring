package com.mybury.waver.web;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.util.FileImageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이미지", description = "업로드된 이미지 파일 서빙")
@RestController
@RequestMapping("waver/image")
@RequiredArgsConstructor
public class ImageController {

    private final FileImageUtils fileImageUtils;

    @Public
    @Operation(summary = "이미지 조회",
        description = "업로드된 이미지 파일을 반환합니다. 버킷/프로필 응답의 이미지 URL이 이 경로를 가리킵니다. 인증이 필요 없는 공개 API입니다.")
    @GetMapping("{*path}")
    public ResponseEntity<Resource> image(@PathVariable String path) {
        Resource resource = fileImageUtils.getImage(path);
        return ResponseEntity.ok()
            .contentType(fileImageUtils.contentType(Objects.requireNonNull(resource.getFilename())))
            .body(resource);
    }
}
