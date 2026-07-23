# 코드 컨벤션 제정 및 위키 문서 작성 계획

## Context

waver_spring(Spring Boot 3.5.4 / Java 21 / Maven / JPA / Lombok) 프로젝트에 코드 컨벤션 문서가 없다. 기존 코드는 들여쓰기(2/4-space), 레이어간 DTO 혼용, 엔티티 @Setter, @Transactional import(jakarta/spring), 조회 메서드 네이밍(get/find/명사형)이 파일마다 다르다. dev-harness 초기화의 일환으로 베스트 프랙티스 기준 컨벤션을 제정하고 `.harness/docs/` 위키에 문서화한다. 이후 모든 세션이 wiki lookup으로 이 규칙을 자동 소비한다.

**이 작업은 문서 제정만이다. 기존 코드 리팩토링은 범위 밖이다.**

## 사용자 확정 결정 (변경 불가)

1. 문서 위치: `.harness/docs/` 위키
2. 패키지 구조: 현행 레이어형(web/service/repository/domain) 유지
3. 도구(Spotless/.editorconfig/Checkstyle) 도입 없음 — 문서만
4. 테스트 컨벤션 제외
5. 방향: 베스트 프랙티스 우선. 기존 코드와 달라도 무방, 신규 코드부터 적용. 사용자 명시 예: "서비스와 컨트롤러·레포 레이어간 DTO 혼용 금지"
6. 들여쓰기 4-space
7. 현재 브랜치(argon1025/FEAT-BUCKET-LIST-FRIEND_INFO)에서 진행

## Required reading (실행 전 필독)

1. `~/.claude/plugins/cache/my-claude-plugin-market/dev-harness/1.0.0/references/wiki-rules.md` — 위키 문서 계약(frontmatter 4필드, 본문 스타일 음/함/됨 명사형, 1불릿 1사실, ADR 골격). 문서 작성 형식의 SSOT.
2. 위키 인덱스 검증 스크립트: `python3 ~/.claude/plugins/cache/my-claude-plugin-market/dev-harness/1.0.0/scripts/wiki_index.py` (프로젝트 루트에서 실행) — frontmatter 위반 시 stderr에 `warn:` 출력하고 해당 파일을 인덱스에서 제외. 커밋 검증 게이트.

참고: `.harness/docs/` 직접 수정은 평소 금지(writing-docs 스킬 경유)지만, 이번엔 하네스 초기화로 사용자가 직접 작성을 지시했다. 이 예외를 feedback.md에 기록한다.

## 산출물 — 문서 6개

| # | 경로 | 내용 |
|---|------|------|
| 1 | `.harness/docs/convention-adoption.md` | 컨벤션 적용 시점·범위 원칙 (횡단) |
| 2 | `.harness/docs/convention-style.md` | 포맷·네이밍·Lombok |
| 3 | `.harness/docs/convention-layers.md` | 레이어 책임·DTO 경계 |
| 4 | `.harness/docs/convention-jpa.md` | 엔티티·연관관계·트랜잭션 |
| 5 | `.harness/docs/convention-api.md` | 응답·예외·검증·문서화 |
| 6 | `.harness/docs/adr/0001-error-response-http200.md` | 오류 HTTP 200 계약 동결 ADR |

### frontmatter (전 문서 공통 형식 — wiki-rules 계약)

```yaml
description: <80~150자, 명사구 종결, 한 물리적 줄>
scope: ["src/main/java/..."]
created: <커밋일 YYYY-MM-DD>
updated: <커밋일 YYYY-MM-DD>
```

`harvested` 필드는 절대 쓰지 않는다.

### description 초안 (실행 시 80~150자 검증 후 미세 조정 가능)

1. adoption: `코드 컨벤션의 적용 시점과 범위 원칙 — 신규 파일 전면 적용, 기존 파일은 수정 지점 한정 적용, 컨벤션 목적 리팩토링의 별도 커밋 분리 규칙을 정한 횡단 정책`
2. style: `Java 소스 공통 스타일 규칙 — 4-space 들여쓰기와 120자 줄 길이, import 규칙, Lombok 허용 목록, 클래스·메서드 네이밍(get/find 의미 구분 포함) 표준`
3. layers: `web-service-repository 레이어 책임과 의존 방향 규칙 — web/message DTO의 컨트롤러 한정 사용, 서비스·레포지토리 입출력 타입 제한, of() 매핑 관례를 정한 경계 계약`
4. jpa: `JPA 엔티티와 트랜잭션 규칙 — @Setter 금지와 의도 드러내는 변경 메서드, 읽기전용 @ManyToOne+원시 FK 필드 패턴, Spring @Transactional(readOnly) 부착 표준`
5. api: `HTTP API 표면 계약 — /waver 경로 BaseResponse 자동 래핑, WaverException+ResultCode 오류 체계, 컨트롤러 @Valid 검증 위치, springdoc 문서화 필수 항목 규칙`
6. adr: `오류 응답을 HTTP 200 + BaseResponse(success:false, ResultCode 문자열 코드)로 반환하는 기존 클라이언트 계약의 동결 결정과 트레이드오프 기록`

### scope 글롭

1. adoption: `["src/main/java/**/*.java"]`
2. style: `["src/main/java/**/*.java"]`
3. layers: `["src/main/java/com/mybury/waver/web/**", "src/main/java/com/mybury/waver/service/**", "src/main/java/com/mybury/waver/repository/**", "src/main/java/com/mybury/waver/event/**"]`
4. jpa: `["src/main/java/com/mybury/waver/domain/**", "src/main/java/com/mybury/waver/repository/**", "src/main/java/com/mybury/waver/service/**"]`
5. api: `["src/main/java/com/mybury/waver/web/**", "src/main/java/com/mybury/waver/advice/**", "src/main/java/com/mybury/waver/exception/**", "src/main/java/com/mybury/waver/common/**"]`
6. adr: `["src/main/java/com/mybury/waver/advice/**", "src/main/java/com/mybury/waver/common/code/ResultCode.java", "src/main/java/com/mybury/waver/common/dto/BaseResponse.java"]`

## 규칙 전문 (문서 본문의 사실 집합 — 실행 시 wiki-rules 스타일로 변환하되 사실은 가감 없이)

표기: **[충돌]** = 기존 코드 일부/다수와 다름(신규·수정 코드부터 적용). 무표기 = 현행 관행 공식화 또는 신규 규칙.

### 문서 1: convention-adoption.md

- 컨벤션은 신규 파일에 전면 적용 — 규칙 무결성의 최소 단위가 파일
- 기존 파일 수정 시 수정하는 메서드·블록에만 적용 — diff 오염 방지
- 기존 파일의 파일 전체 재포맷 금지 — git blame 추적성 보존
- 컨벤션 목적 리팩토링(보이스카웃)은 기능 커밋과 분리한 독립 커밋으로만 수행 — 회귀 원인 격리
- 기존 코드의 컨벤션 위반은 발견 시 수정 의무 없음 — 제정 범위가 규칙 수립에 한정
- 규칙과 기존 코드가 다를 때 규칙 우선, 기존 코드 모방 금지 — LLM의 주변 코드 모방 경향 차단
- 클라이언트 노출 계약(URL, 응답 스키마, ResultCode 값) 변경은 컨벤션 적용 대상에서 제외 — 외부 계약 동결

### 문서 2: convention-style.md

- **들여쓰기 4-space, 탭 금지** — Java 생태계 기본값(IntelliJ·Oracle 컨벤션). **[충돌]** 서비스·엔티티·레포 다수 파일이 2-space
- 연속 줄(continuation) 들여쓰기 8-space — IntelliJ 기본값과 정합
- 줄 길이 최대 120자
- 와일드카드 import 금지, 미사용 import 금지
- import 정렬은 IDE 기본 알파벳 정렬 허용, static import는 최하단 — 도구 없이 유지 가능한 최소 규칙
- 클래스 네이밍 XxxController / XxxService / XxxRepository / XxxRepositoryCustom / XxxRepositoryImpl
- **조회 메서드 의미 구분: getXxx = 결과 보장(부재 시 WaverException(NOT_FOUND) throw), findXxx = 부재 허용(Optional·빈 컬렉션 반환)** — Spring Data(findBy=Optional)·JPA(getReference=보장) 관례 정합. **[충돌]** 현행 get/find/명사형 혼재(예: FollowService.getMutual은 빈 리스트 허용인데 get)
- Repository 파생 쿼리는 Spring Data 접두어(findBy/existsBy/countBy/deleteBy) 원형 준수
- 명령 메서드는 동사 원형(follow, unfollow, report) — 도메인 행위 표현
- Lombok 허용 목록: @Getter @RequiredArgsConstructor @Slf4j @Builder @NoArgsConstructor @AllArgsConstructor(엔티티 한정, 접근제한 필수). 이외(@Setter @Data @Value @SneakyThrows) 금지 — **[충돌]** 엔티티 5개(Bucket, User, Comment, Badge, Category)가 @Setter 사용
- 의존성 주입은 @RequiredArgsConstructor + private final 필드, 필드 주입(@Autowired) 금지
- 주석은 "왜"만 작성, 코드 재진술 금지, 한국어 허용
- 상수는 private static final + UPPER_SNAKE_CASE, 매직 넘버 금지 — 근거: BucketRepositoryImpl.FEED_PAGE_SIZE 관행

### 문서 3: convention-layers.md

- 의존 방향 web → service → repository → domain 단방향. 역방향·건너뛰기(컨트롤러→레포 직접 호출) 금지
- **web/message/** DTO는 web 레이어(컨트롤러·advice) 전용, service·repository에서 import 금지** — HTTP 표현과 비즈니스 로직 결합 차단. **[충돌]** 서비스 전반 + BucketRepositoryCustom.java:4
- 서비스 입력 타입: 원시값·ID, 엔티티, domain/vo, 또는 service 패키지의 커맨드 record(XxxCommand)로 한정 — **[충돌]** 현행 서비스가 web DTO 직접 수신
- 서비스 출력 타입: 엔티티, domain/vo, 원시값, 또는 service 패키지 결과 record(XxxResult)로 한정. web DTO 반환 금지 — **[충돌]** FollowService.getFollowList()가 GetFollowersResponse 반환
- 파라미터 3개 초과 또는 선택 필드 포함 시 커맨드 record 의무화 — 시그니처 폭발 방지
- Repository 입출력: 엔티티, ID·원시값, enum(common/code), domain/vo 프로젝션으로 한정. web DTO 금지 — **[충돌]** BucketRepositoryImpl.findBucket(Long, BucketRequest)
- **변환 책임: web DTO ↔ 서비스 입출력 변환은 컨트롤러(또는 web DTO 자신의 팩토리)에서 수행** — 각 레이어가 자기 타입만 알게 하는 단일 지점 변환. **[충돌]** 현행은 서비스 내 변환(FollowService:54)과 컨트롤러 내 변환(FollowController:55) 혼재
- **매핑 관례: 응답 DTO에 `public static Xxx of(도메인타입...)` 정적 팩토리로 통일. 엔티티 받는 public 생성자·도메인 동사 팩토리(follow() 등) 금지** — 현행 최다 관행(of 8곳+)의 표준화. **[충돌]** FollowElement::new, FollowElement.follow()/follower()
- 요청 DTO의 toEntity() 금지 — 엔티티 생성은 서비스에서 빌더·정적 팩토리로. 생성 규칙(기본값·검증)의 서비스 집중
- DTO 접미사: 리스트 항목 XxxElement, 요청 XxxRequest, 응답 XxxResponse
- **응답 DTO의 Get 접두어 금지**(GetFollowersResponse형 → FollowersResponse형) — DTO는 데이터 명사, HTTP 메서드는 URL·매핑이 표현. **[충돌]** GetFollowersResponse, GetPopularBucketResponse (기존 클래스명은 유지, 신규만 적용)
- DTO는 record로 작성 — 현행 48개 전부 record
- 이벤트 리스너(event/)는 서비스와 동급 레이어, repository 직접 접근 허용하되 web DTO 금지

### 문서 4: convention-jpa.md

- **엔티티 @Setter 금지. 상태 변경은 의도를 드러내는 public 변경 메서드(complete(), changeExposure(...), increaseLikeCount())로만** — 변경 지점 추적, 더티체킹 대상 명시화. **[충돌]** 엔티티 5개
- 엔티티 표준 애노테이션: @Getter @Entity @NoArgsConstructor(access = PROTECTED) @AllArgsConstructor(access = PRIVATE) @Builder — JPA 프록시 요구 + 생성 경로 빌더 단일화
- 필드 기본값은 @Builder.Default로 선언 — 빌더 생성 시 기본값 유실 방지 (근거: Bucket.java:54)
- 생성 로직이 3필드 초과 조립이면 엔티티 정적 팩토리(create(...)) 제공 — 생성 불변식의 엔티티 집중
- 연관관계 표준: 값은 `Long xxxId` 원시 FK 필드에 저장, 탐색용 @ManyToOne(fetch = LAZY) @JoinColumn(insertable = false, updatable = false, foreignKey = @ForeignKey(NO_CONSTRAINT)) 읽기전용 병행 — 현행 패턴 공식화 (근거: Bucket.java:113-119, FK 제약 없는 DB 스키마와 정합)
- 연관 객체 필드 쓰기 금지, 항상 xxxId 필드로만 쓰기 — 읽기전용 매핑과의 이중 쓰기 사고 방지
- 신규 컬렉션 연관(@OneToMany)은 조회 전용, cascade·orphanRemoval 금지 — 암묵 전파 삭제 사고 방지
- **@Transactional은 org.springframework.transaction.annotation.Transactional로 통일, jakarta.transaction 금지** — readOnly·propagation 속성은 Spring 것에만 존재. **[충돌]** 레포 5개·서비스 6개·리스너가 jakarta 사용
- **부착 위치는 서비스 public 메서드 한정, Repository·Controller 부착 금지** — 트랜잭션 경계 = 유스케이스 경계. **[충돌]** FollowRepository.java:14,29
- **쓰기 메서드 @Transactional, 조회 메서드 @Transactional(readOnly = true) 의무** — 더티체킹 스냅샷 생략 + 실수 쓰기 차단. **[충돌]** readOnly 현행 0건
- @Modifying 쿼리는 @Modifying(clearAutomatically = true) + 호출 서비스의 @Transactional 참여로 실행, Repository 자체 @Transactional 금지 — 벌크 연산 후 영속성 컨텍스트 불일치 방지. **[충돌]** FollowRepository.insertFollow/deleteFollow
- em.flush()/em.clear() 수동 호출 금지, 트랜잭션 커밋에 위임 — **[충돌]** BucketRepositoryImpl.commit():207-209
- Criteria API 동적 쿼리는 XxxRepositoryCustom 인터페이스 + XxxRepositoryImpl 패턴
- 조회 전용 결과는 domain/vo 인터페이스 프로젝션 또는 record — 엔티티 전체 로딩 회피 (근거: FollowCount)
- @PostLoad 파생 필드(@Transient)는 계산 비용 O(1)일 때만 허용 — N건 로딩 시 비용 증폭 방지
- 엔티티는 BaseEntity 상속으로 생성·수정 시각 Auditing 적용

### 문서 5: convention-api.md

- **오류 포함 전 응답은 HTTP 200 + BaseResponse(success/code/message/data) 계약 유지, 변경 금지(동결)** — 상세는 adr/0001 참조
- 컨트롤러는 DTO 직접 반환, BaseResponse 수동 래핑·ResponseEntity 사용 금지 — BaseResponseAdvice가 /waver 경로 응답 자동 래핑
- 비즈니스 오류는 WaverException(ResultCode) 단일 예외로 throw, 신규 오류는 ResultCode enum 코드 추가로 대응
- ResultCode 신규 코드는 기존 대역(4xxx 요청 오류, 5xxx 시스템·토큰, 6xxx~9xxx 도메인) 내 채번, 기존 값 변경·재사용 금지 — 클라이언트 하드코딩 분기 보호
- 예외 처리는 BaseControllerAdvice @ExceptionHandler에 집중, 컨트롤러·서비스의 try-catch 응답 조립 금지
- **요청 검증은 컨트롤러 @Valid 전용. 서비스 파라미터의 Bean Validation 애노테이션(@NotNull/@Valid) 금지, @Validated 도입하지 않음** — @Validated 부재 시 미동작 장식이고, 도입 시 ConstraintViolationException이 BaseControllerAdvice 검증 핸들러 밖으로 새는 이중 처리 발생. **[충돌]** FollowService.java:76 등 장식 애노테이션 존재
- 서비스의 비즈니스 규칙 검증은 코드 분기 + WaverException으로 표현
- URL은 waver/<도메인> + 하위 경로 명사형, 버전 표현은 DTO 패키지(web/message/v1)로만 관리
- 컨트롤러 메서드 @Operation(summary, description) + 클래스 @Tag 의무, @UserId 파라미터는 @Parameter(hidden = true) 의무 — springdoc 문서 완결성
- 인증 필요가 기본, 공개 API만 @Public 명시 — AuthGuardAspect 계약, secure by default
- 응답 null 필드는 Jackson non_null 전역 설정으로 생략됨을 전제로 DTO 설계 — application.yml jackson 설정과 정합

### 문서 6: adr/0001-error-response-http200.md

wiki-rules ADR 골격 필수: `# 제목` / `## 컨텍스트` / `## 결정` / `## Why` / `## Apply when`

- 컨텍스트: BaseControllerAdvice가 모든 예외를 ResponseEntity.ok(...)(HTTP 200)로 반환. 배포된 모바일 클라이언트가 success + ResultCode 문자열("2000"/"4000"…)로 분기
- 결정: 오류 응답의 HTTP 200 + BaseResponse 계약 동결. 서버 단독 변경 불가
- Why: HTTP 상태 전환은 클라이언트 전면 수정 요구. 포기한 것 — HTTP 상태 기반 관측·모니터링, 중간 장비 캐싱 시맨틱, 표준 클라이언트 에러 핸들링
- Apply when: /waver 경로 신규 API 전부. 재검토 트리거 — v2 API 신설 시 HTTP 상태 병행 검토

## 실행 절차

### 커밋 0 (플랜 승인 직후, 계획 세션에서 수행)
- 승인된 플랜을 `.harness/workspace/progress/argon1025-FEAT-BUCKET-LIST-FRIEND_INFO/plan.md`에 스냅샷, feedback.md와 함께 커밋
- 이후 구현은 새 세션에서 `/dev-harness:executing-plan`으로

### 커밋 1: `docs(convention): 컨벤션 적용 원칙과 코드 스타일 규칙 제정`
- 파일: convention-adoption.md, convention-style.md
- 검증: 프로젝트 루트에서 `python3 ~/.claude/plugins/cache/my-claude-plugin-market/dev-harness/1.0.0/scripts/wiki_index.py` 실행 → stderr `warn:` 0건 + 두 파일이 인덱스 행으로 출력. description 물리적 한 줄·80~150자 확인

### 커밋 2: `docs(convention): 레이어 경계와 API 계약 규칙 제정 + 오류 응답 ADR`
- 파일: convention-layers.md, convention-api.md, adr/0001-error-response-http200.md
- 함께 커밋하는 이유: api 문서가 ADR을 참조하므로 참조 무결성을 한 커밋에서 보장
- 검증: 커밋 1과 동일 + `--scope "src/main/java/com/mybury/waver/web/**"` 필터로 layers·api 문서 매치 확인 + 본문에 인용된 코드 경로(BucketRepositoryImpl.java 등) 실존 확인

### 커밋 3: `docs(convention): JPA 엔티티·트랜잭션 규칙 제정`
- 파일: convention-jpa.md
- 단독 커밋 이유: 규칙 수·충돌 표시가 가장 많은 문서라 독립 리뷰 단위로 분리
- 검증: 인덱스 전체 실행 → 문서 6개 전부 행 출력·warn 0건. `--scope "src/main/java/com/mybury/waver/domain/**"` 매치 확인

### feedback.md 기록 사항 (실행 중 해당 커밋에 포함)
- 하네스 초기화로 사용자가 `.harness/docs/` 직접 작성을 지시함(평소 writing-docs 스킬 경유 규칙의 명시적 예외) — context
- 들여쓰기 4-space는 사용자 결정. 현행 다수 파일(서비스·엔티티·레포)이 2-space라 Google Java Style(2-space) 대안을 기각함 — why
- 오류 HTTP 200 + ResultCode 계약은 배포된 모바일 클라이언트와의 동결 계약 — constraint
- 컨벤션은 신규·수정 코드부터 적용, 기존 코드 일괄 리팩토링은 범위 외(사용자 확정) — context

## 검증 (전체 완료 기준)

1. `python3 ~/.claude/plugins/cache/my-claude-plugin-market/dev-harness/1.0.0/scripts/wiki_index.py` → 6개 문서 전부 인덱스에 출력, `warn:` 0건
2. 각 문서 frontmatter: description 80~150자 한 줄, scope 실경로 글롭, created/updated 커밋일, harvested 없음
3. 본문 스타일: 음/함/됨 명사형 종결, 1불릿 1사실, "규칙 — 근거" 순서, 모호어(TBD/적절히/필요시) 0건
4. ADR이 골격(컨텍스트/결정/Why/Apply when) 준수
5. 커밋 3개가 Conventional Commits 형식, 각 커밋 시점에 인덱스 파싱 통과
