---
description: HTTP API 표면 계약 — /waver 경로 BaseResponse 자동 래핑, WaverException+ResultCode 오류 체계, 컨트롤러 @Valid 검증 위치, springdoc 문서화 필수 항목 규칙
scope: ["src/main/java/com/mybury/waver/web/**", "src/main/java/com/mybury/waver/advice/**", "src/main/java/com/mybury/waver/exception/**", "src/main/java/com/mybury/waver/common/**"]
created: 2026-07-23
updated: 2026-07-23
---

# API 계약

## 응답 래핑

- 오류 포함 전 응답은 HTTP 200 + `BaseResponse`(success/code/message/data) 계약 유지, 변경 금지(동결) — 상세는 `adr/0001-error-response-http200.md`
- 컨트롤러는 DTO 직접 반환, `BaseResponse` 수동 래핑·`ResponseEntity` 사용 금지 — `BaseResponseAdvice`가 /waver 경로 응답 자동 래핑
- 응답 null 필드는 Jackson non_null 전역 설정으로 생략됨을 전제로 DTO 설계 — `application.yml` jackson 설정과 정합

## 오류 체계

- 비즈니스 오류는 `WaverException(ResultCode)` 단일 예외로 throw, 신규 오류는 `ResultCode` enum 코드 추가로 대응
- `ResultCode` 신규 코드는 기존 대역(4xxx 요청 오류, 5xxx 시스템·토큰, 6xxx~9xxx 도메인) 내 채번, 기존 값 변경·재사용 금지 — 클라이언트 하드코딩 분기 보호
- 예외 처리는 `BaseControllerAdvice` `@ExceptionHandler`에 집중, 컨트롤러·서비스의 try-catch 응답 조립 금지

## 검증

- 요청 검증은 컨트롤러 `@Valid` 전용, 서비스 파라미터의 Bean Validation 애노테이션(`@NotNull`/`@Valid`) 금지, `@Validated` 도입하지 않음 — `@Validated` 부재 시 미동작 장식이고, 도입 시 `ConstraintViolationException`이 `BaseControllerAdvice` 검증 핸들러 밖으로 새는 이중 처리 발생. [충돌] `FollowService.java:76` 등 장식 애노테이션 존재
- 서비스의 비즈니스 규칙 검증은 코드 분기 + `WaverException`으로 표현

## URL·문서화

- URL은 `waver/<도메인>` + 하위 경로 명사형, 버전 표현은 DTO 패키지(`web/message/v1`)로만 관리
- 컨트롤러 메서드 `@Operation(summary, description)` + 클래스 `@Tag` 의무, `@UserId` 파라미터는 `@Parameter(hidden = true)` 의무 — springdoc 문서 완결성
- 인증 필요가 기본, 공개 API만 `@Public` 명시 — `AuthGuardAspect` 계약, secure by default
