package com.mybury.waver.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GooglePlayConfig {

  @Value("${google.play.credentials-path}")
  private String credentialsPath;

  @Bean
  public AndroidPublisher androidPublisher() throws IOException, GeneralSecurityException {
    // 1. JSON 키 파일 로드 및 권한(Scope) 설정
    FileInputStream stream = new FileInputStream(credentialsPath);
    GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
        .createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER));

    // 2. AndroidPublisher 객체 생성 및 반환
    return new AndroidPublisher.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        GsonFactory.getDefaultInstance(),
        new HttpCredentialsAdapter(credentials))
        .setApplicationName("Waver-Spring-Backend")
        .build();
  }
}
