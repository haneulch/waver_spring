---
description: JPA 엔티티와 트랜잭션 규칙 — @Setter 금지와 의도 드러내는 변경 메서드, 읽기전용 @ManyToOne+원시 FK 필드 패턴, Spring @Transactional(readOnly) 부착 표준
scope: ["src/main/java/com/mybury/waver/domain/**", "src/main/java/com/mybury/waver/repository/**", "src/main/java/com/mybury/waver/service/**"]
created: 2026-07-23
updated: 2026-07-23
---

# JPA 컨벤션

## 엔티티 상태 변경

- 엔티티 `@Setter` 금지, 상태 변경은 의도를 드러내는 public 변경 메서드(`complete()`, `changeExposure(...)`, `increaseLikeCount()`)로만 — 변경 지점 추적, 더티체킹 대상 명시화. [충돌] 엔티티 5개
- 연관 객체 필드 쓰기 금지, 항상 `xxxId` 필드로만 쓰기 — 읽기전용 매핑과의 이중 쓰기 사고 방지

## 엔티티 선언

- 엔티티 표준 애노테이션 `@Getter` `@Entity` `@NoArgsConstructor(access = PROTECTED)` `@AllArgsConstructor(access = PRIVATE)` `@Builder` — JPA 프록시 요구 + 생성 경로 빌더 단일화
- 필드 기본값은 `@Builder.Default`로 선언 — 빌더 생성 시 기본값 유실 방지. 근거: `Bucket.java:54`
- 생성 로직이 3필드 초과 조립이면 엔티티 정적 팩토리(`create(...)`) 제공 — 생성 불변식의 엔티티 집중
- 엔티티는 `BaseEntity` 상속으로 생성·수정 시각 Auditing 적용

## 연관관계

- 연관관계 표준은 값을 `Long xxxId` 원시 FK 필드에 저장, 탐색용 `@ManyToOne(fetch = LAZY)` `@JoinColumn(insertable = false, updatable = false, foreignKey = @ForeignKey(NO_CONSTRAINT))` 읽기전용 병행 — 현행 패턴 공식화. 근거: `Bucket.java:113-119`, FK 제약 없는 DB 스키마와 정합
- 신규 컬렉션 연관(`@OneToMany`)은 조회 전용, cascade·orphanRemoval 금지 — 암묵 전파 삭제 사고 방지

## 트랜잭션

- `@Transactional`은 `org.springframework.transaction.annotation.Transactional`로 통일, `jakarta.transaction` 금지 — readOnly·propagation 속성은 Spring 것에만 존재. [충돌] 레포 5개·서비스 6개·리스너가 jakarta 사용
- 부착 위치는 서비스 public 메서드 한정, Repository·Controller 부착 금지 — 트랜잭션 경계가 유스케이스 경계. [충돌] `FollowRepository.java:14,29`
- 쓰기 메서드 `@Transactional`, 조회 메서드 `@Transactional(readOnly = true)` 의무 — 더티체킹 스냅샷 생략 + 실수 쓰기 차단. [충돌] readOnly 현행 0건
- `@Modifying` 쿼리는 `@Modifying(clearAutomatically = true)` + 호출 서비스의 `@Transactional` 참여로 실행, Repository 자체 `@Transactional` 금지 — 벌크 연산 후 영속성 컨텍스트 불일치 방지. [충돌] `FollowRepository.insertFollow`/`deleteFollow`
- `em.flush()`/`em.clear()` 수동 호출 금지, 트랜잭션 커밋에 위임 — [충돌] `BucketRepositoryImpl.commit():207-209`

## 조회·프로젝션

- Criteria API 동적 쿼리는 `XxxRepositoryCustom` 인터페이스 + `XxxRepositoryImpl` 패턴
- 조회 전용 결과는 domain/vo 인터페이스 프로젝션 또는 record — 엔티티 전체 로딩 회피. 근거: `FollowCount`
- `@PostLoad` 파생 필드(`@Transient`)는 계산 비용 O(1)일 때만 허용 — N건 로딩 시 비용 증폭 방지
