---
description: 오류 응답을 HTTP 200 + BaseResponse(success:false, ResultCode 문자열 코드)로 반환하는 기존 클라이언트 계약의 동결 결정과 트레이드오프 기록
scope: ["src/main/java/com/mybury/waver/advice/**", "src/main/java/com/mybury/waver/common/code/ResultCode.java", "src/main/java/com/mybury/waver/common/dto/BaseResponse.java"]
created: 2026-07-23
updated: 2026-07-23
---

# 오류 응답 HTTP 200 계약 동결

## 컨텍스트

- `BaseControllerAdvice`가 모든 예외를 `ResponseEntity.ok(...)`(HTTP 200)로 반환
- 배포된 모바일 클라이언트가 success + `ResultCode` 문자열("2000"/"4000"…)로 분기

## 결정

- 오류 응답의 HTTP 200 + `BaseResponse` 계약 동결, 서버 단독 변경 불가

## Why

- HTTP 상태 전환은 클라이언트 전면 수정 요구
- 포기한 것 — HTTP 상태 기반 관측·모니터링, 중간 장비 캐싱 시맨틱, 표준 클라이언트 에러 핸들링

## Apply when

- /waver 경로 신규 API 전부
- 재검토 트리거 — v2 API 신설 시 HTTP 상태 병행 검토
