package com.mybury.waver.util;

import com.mybury.waver.common.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class FileImageUtils {

  private final FileProperties properties;

  public Resource getImage(@PathVariable String path) {
    try {
      Path filePath = Paths.get(properties.getUpload().getPath() + "/" + path).normalize();
      return new UrlResource(filePath.toUri());
    } catch (Exception e) {
      return null;
    }
  }

  public String imagePath(String relativePath) {
    return properties.getImages().getPath() + "/" + relativePath;
  }

  public String staticPath(String relativePath) {
    return properties.getStatics().getPath() + "/" + relativePath;
  }

  public MediaType contentType(String fileName) {
    String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
    return switch (ext) {
      case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
      case "png" -> MediaType.IMAGE_PNG;
      case "gif" -> MediaType.IMAGE_GIF;
      default -> null;
    };
  }
}
