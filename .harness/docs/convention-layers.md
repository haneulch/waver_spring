---
description: web-service-repository 레이어 책임과 의존 방향 규칙 — web/message DTO의 컨트롤러 한정 사용, 서비스·레포지토리 입출력 타입 제한, of() 매핑 관례를 정한 경계 계약
scope: ["src/main/java/com/mybury/waver/web/**", "src/main/java/com/mybury/waver/service/**", "src/main/java/com/mybury/waver/repository/**", "src/main/java/com/mybury/waver/event/**"]
created: 2026-07-23
updated: 2026-07-23
---

# 레이어 경계

## 의존 방향

- 의존 방향 web → service → repository → domain 단방향, 역방향·건너뛰기(컨트롤러→레포 직접 호출) 금지
- 이벤트 리스너(`event/`)는 서비스와 동급 레이어, repository 직접 접근 허용하되 web DTO 금지

## DTO 사용 경계

- `web/message/` DTO는 web 레이어(컨트롤러·advice) 전용, service·repository에서 import 금지 — HTTP 표현과 비즈니스 로직 결합 차단. [충돌] 서비스 전반 + `BucketRepositoryCustom.java:4`
- 서비스 입력 타입은 원시값·ID, 엔티티, domain/vo, service 패키지의 커맨드 record(`XxxCommand`)로 한정 — [충돌] 현행 서비스가 web DTO 직접 수신
- 서비스 출력 타입은 엔티티, domain/vo, 원시값, service 패키지 결과 record(`XxxResult`)로 한정, web DTO 반환 금지 — [충돌] `FollowService.getFollowList()`가 `GetFollowersResponse` 반환
- Repository 입출력은 엔티티, ID·원시값, enum(`common/code`), domain/vo 프로젝션으로 한정, web DTO 금지 — [충돌] `BucketRepositoryImpl.findBucket(Long, BucketRequest)`
- 파라미터 3개 초과 또는 선택 필드 포함 시 커맨드 record 의무화 — 시그니처 폭발 방지

## 변환 책임

- web DTO ↔ 서비스 입출력 변환은 컨트롤러 또는 web DTO 자신의 팩토리에서 수행 — 각 레이어가 자기 타입만 알게 하는 단일 지점 변환. [충돌] 현행은 서비스 내 변환(`FollowService:54`)과 컨트롤러 내 변환(`FollowController:55`) 혼재
- 요청 DTO의 `toEntity()` 금지, 엔티티 생성은 서비스에서 빌더·정적 팩토리로 — 생성 규칙(기본값·검증)의 서비스 집중

## 매핑·네이밍 관례

- 응답 DTO 매핑은 `public static Xxx of(도메인타입...)` 정적 팩토리로 통일, 엔티티 받는 public 생성자·도메인 동사 팩토리(`follow()` 등) 금지 — 현행 최다 관행(of 8곳+)의 표준화. [충돌] `FollowElement::new`, `FollowElement.follow()`/`follower()`
- DTO 접미사는 리스트 항목 `XxxElement`, 요청 `XxxRequest`, 응답 `XxxResponse`
- 응답 DTO의 `Get` 접두어 금지(`GetFollowersResponse`형 → `FollowersResponse`형) — DTO는 데이터 명사, HTTP 메서드는 URL·매핑이 표현. [충돌] `GetFollowersResponse`, `GetPopularBucketResponse`(기존 클래스명은 유지, 신규만 적용)
- DTO는 record로 작성 — 현행 48개 전부 record
