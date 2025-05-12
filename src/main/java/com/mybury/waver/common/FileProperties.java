package com.mybury.waver.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("waver.file")
public class FileProperties {
  private Path upload;
  private Path images;
  private Path statics;

  @Setter
  @Getter
  public static class Path {
    private String path;
  }
}
