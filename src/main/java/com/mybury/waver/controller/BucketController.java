package com.mybury.waver.controller;

import com.mybury.waver.dto.BucketCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "버킷리스트")
@RestController
@RequestMapping("waver/bucket")
@RequiredArgsConstructor
public class BucketController {

    @Operation(summary = "버킷리스트 등록")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void bucketCreate(@Valid @ModelAttribute BucketCreateRequest request) {

    }

}
