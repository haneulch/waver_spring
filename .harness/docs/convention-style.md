---
description: Java 소스 공통 스타일 규칙 — 4-space 들여쓰기와 120자 줄 길이, import 규칙, Lombok 허용 목록, 클래스·메서드 네이밍(get/find 의미 구분 포함) 표준
scope: ["src/main/java/**/*.java"]
created: 2026-07-23
updated: 2026-07-23
---

# 코드 스타일

## 포맷

- 들여쓰기 4-space, 탭 금지 — Java 생태계 기본값(IntelliJ·Oracle 컨벤션). [충돌] 서비스·엔티티·레포 다수 파일이 2-space
- 연속 줄(continuation) 들여쓰기 8-space — IntelliJ 기본값과 정합
- 줄 길이 최대 120자

## import

- 와일드카드 import 금지
- 미사용 import 금지
- import 정렬은 IDE 기본 알파벳 정렬 허용, static import는 최하단 — 도구 없이 유지 가능한 최소 규칙

## 클래스 네이밍

- 클래스 네이밍 `XxxController` / `XxxService` / `XxxRepository` / `XxxRepositoryCustom` / `XxxRepositoryImpl`

## 메서드 네이밍

- `getXxx`는 결과 보장, 부재 시 `WaverException(NOT_FOUND)` throw — Spring Data·JPA `getReference`의 보장 관례 정합
- `findXxx`는 부재 허용, Optional·빈 컬렉션 반환 — Spring Data `findBy` Optional 관례 정합. [충돌] 현행 get/find/명사형 혼재(예: `FollowService.getMutual`은 빈 리스트 허용인데 get)
- Repository 파생 쿼리는 Spring Data 접두어(`findBy`/`existsBy`/`countBy`/`deleteBy`) 원형 준수
- 명령 메서드는 동사 원형(`follow`, `unfollow`, `report`) — 도메인 행위 표현

## Lombok

- 허용 목록: `@Getter` `@RequiredArgsConstructor` `@Slf4j` `@Builder` `@NoArgsConstructor` `@AllArgsConstructor`(엔티티 한정, 접근제한 필수)
- `@Setter` `@Data` `@Value` `@SneakyThrows` 금지 — [충돌] 엔티티 5개(Bucket, User, Comment, Badge, Category)가 `@Setter` 사용
- 의존성 주입은 `@RequiredArgsConstructor` + `private final` 필드, 필드 주입(`@Autowired`) 금지

## 주석·상수

- 주석은 "왜"만 작성, 코드 재진술 금지, 한국어 허용
- 상수는 `private static final` + UPPER_SNAKE_CASE, 매직 넘버 금지 — 근거: `BucketRepositoryImpl.FEED_PAGE_SIZE` 관행
