package com.mybury.waver.util;

import com.mybury.waver.common.FileProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileImageUtils {

  private final FileProperties properties;

  private static String imagePath;
  private static String staticPath;

  public FileImageUtils(FileProperties properties) {
    this.properties = properties;
    FileImageUtils.imagePath = properties.getImages().getPath();
    FileImageUtils.staticPath = properties.getStatics().getPath();
  }

  public Resource getImage(String path) {
    try {
      Path filePath = Paths.get(properties.getUpload().getPath() + "/" + path).normalize();
      return new UrlResource(filePath.toUri());
    } catch (Exception e) {
      return null;
    }
  }

  public static String imagePath(String relativePath) {
    if (StringUtils.hasText(relativePath)) {
      return imagePath + "/" + relativePath;
    }
    return null;
  }

  public static String staticPath(String relativePath) {
    if (StringUtils.hasText(relativePath)) {
      return staticPath + "/" + relativePath;
    }
    return null;
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
