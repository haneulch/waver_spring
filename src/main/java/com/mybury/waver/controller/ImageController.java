package com.mybury.waver.controller;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.util.FileImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("waver/image")
@RequiredArgsConstructor
public class ImageController {

  private final FileImageUtils fileImageUtils;

  @Public
  @GetMapping("{*path}")
  public ResponseEntity<Resource> image(@PathVariable String path) {
    Resource resource = fileImageUtils.getImage(path);
    return ResponseEntity.ok()
        .contentType(fileImageUtils.contentType(Objects.requireNonNull(resource.getFilename())))
        .body(resource);
  }
}
