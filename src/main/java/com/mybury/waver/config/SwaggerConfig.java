package com.mybury.waver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private static final String API_DESCRIPTION = """
      버킷리스트 SNS **Waver** 백엔드 API 문서입니다.

      ### 인증
      - 로그인/회원가입 등 일부 공개 API를 제외한 모든 `/waver/**` API는 \
      `Authorization: Bearer {accessToken}` 헤더가 필요합니다.
      - accessToken은 `POST /waver/login` 또는 `POST /waver/user/join` 응답으로 발급됩니다.
      - 우측 상단 **Authorize** 버튼에 토큰을 입력하면 모든 요청에 자동으로 적용됩니다.

      ### 공통 응답 포맷
      모든 응답은 아래 형태로 감싸져 내려갑니다.
      ```json
      { "success": true, "code": "2000", "message": "SUCCESS", "data": { } }
      ```
      오류 시 `success=false`이며 `code`로 원인을 구분합니다. \
      (예: 4040 NOT_FOUND, 5001 TOKEN_EXPIRED, 8100 이미지 저장 제한 초과, 8101 함께하기 제한 초과)

      ### 무료 이용 제한
      - 이미지: 버킷당 최대 3장. 무료 사용자는 기본 1장, 2장 이상 저장은 1회 제공
      - 함께하기: 무료 사용자는 3회 제공, 구독(Waver+) 시 무제한
      - 잔여 횟수는 `GET /waver/user/check/limit`로 조회
      """;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Waver API")
            .description(API_DESCRIPTION)
            .version("v1"))
        .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
        .components(new Components()
            .addSecuritySchemes("BearerAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")));
  }
}
