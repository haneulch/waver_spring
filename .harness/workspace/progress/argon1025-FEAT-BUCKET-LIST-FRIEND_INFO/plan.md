# 버킷리스트 상세 friendUsers에 imgUrl·goalCount·userCount 추가

## Context

버킷리스트 상세 API(`GET /waver/bucket/{id}`)의 `friendUsers`가 현재 `{id, name}`만 내려줘요. 클라이언트가 함께하는 친구를 화면에 그리려면 프로필 이미지와 진행도(goalCount/userCount)가 더 필요하다고 요청했어요.

데이터 모델을 확인한 결과, 함께하기(TOGETHER) 버킷은 친구와 버킷 row 하나를 공유해요. `userCount`(달성 진행)/`goalCount`(목표)는 버킷 전역 값이고, 친구가 achieve해도 같은 카운트가 올라가요(`BucketService.achieve`, `BucketRepository.java:43`). 그래서 친구별 개별 진행도는 존재하지 않아요.

## 사용자 확정 결정 (verbatim)

1. **카운트 의미**: "버킷 공유 진행도 그대로" — friendUsers 각 항목에 버킷의 goalCount/userCount를 그대로 복사해서 내려줘요. 클라이언트 파싱 편의용이고, 서버는 DTO만 수정해요. (친구별 개별 진행도 스키마 신설, 친구 프로필 통계 방안은 기각)
2. **필드명**: "imgUrl" — 클라이언트가 요청 문구에 쓴 `profileImage` 대신 기존 유저 DTO(UserElement, ProfileResponse) 네이밍 관례인 `imgUrl`을 써요. 값은 `FileImageUtils.imagePath()`로 풀 URL 변환해서 내려줘요(ProfileResponse 관례).

## Required Reading (코딩 전 필독)

- `.harness/docs/convention-layers.md` — 응답 DTO는 `of()` 정적 팩토리로 매핑, 엔티티 받는 public 생성자 금지 규칙이 이번 수정 지점에 직접 적용됨
- `.harness/docs/convention-adoption.md` — 기존 파일은 수정하는 블록에만 컨벤션 적용, 파일 전체 재포맷 금지
- `.harness/docs/convention-api.md` — 응답 null 필드는 Jackson non_null 전역 설정으로 생략됨(imgUrl이 없는 유저는 필드 자체가 빠짐을 전제로 설계)

## 변경 내용

수정 파일은 1개예요: `src/main/java/com/mybury/waver/web/message/v1/bucket/BucketDetailResponse.java`

같은 파일 상단에 있는 `FriendElement` record를 확장해요.

```java
record FriendElement(
        long id,
        String name,
        String imgUrl,
        int goalCount,
        int userCount) {

    public static FriendElement of(User friend, Bucket bucket) {
        return new FriendElement(
                friend.getId(),
                friend.getName(),
                FileImageUtils.imagePath(friend.getImgUrl()),
                bucket.getGoalCount(),
                bucket.getUserCount());
    }
}
```

- 기존 `public FriendElement(User friend)` 생성자는 삭제해요. 수정 지점이므로 컨벤션(엔티티 받는 public 생성자 금지, `of()` 정적 팩토리 통일)을 적용해요.
- `BucketDetailResponse.of()` 내부 매핑을 `friendUserList.stream().map(FriendElement::new)` 에서 `friendUserList.stream().map(friend -> FriendElement.of(friend, bucket))` 로 바꿔요.
- `User.imgUrl`은 상대 경로로 저장되므로(`UserService.java:69`) `FileImageUtils.imagePath()`로 풀 URL을 만들어요. 프로필 이미지가 없으면 `imagePath()`가 null을 반환하고, Jackson non_null 설정으로 응답에서 필드가 생략돼요.

컨트롤러·서비스·레포지토리는 바꾸지 않아요. `BucketDetailResponse.of()`가 이미 `bucket`과 `friendUserList`를 둘 다 받고 있어서 DTO 레이어 안에서 끝나요. 응답 스키마는 필드 추가만이라 기존 클라이언트 계약(동결)과 호환돼요.

## 커밋 계획

커밋 1개예요.

- `feat(bucket): 버킷 상세 friendUsers에 imgUrl·goalCount·userCount 추가`
- 포함: `BucketDetailResponse.java` 수정 + `.harness/workspace/progress/{slug}/feedback.md` 신규 항목

feedback.md에 append할 항목 (why/constraint):

```
- 함께하기(TOGETHER) 버킷은 친구와 버킷 row 하나를 공유하며 userCount/goalCount는 버킷 전역 값이다 — 친구별 개별 진행도는 스키마에 없고, 버킷 상세 friendUsers의 goalCount/userCount는 버킷 값을 그대로 복사한 것이다(사용자 확정).
  - evidence: src/main/java/com/mybury/waver/service/BucketService.java:264-298
- 버킷 상세 friendUsers의 프로필 이미지 필드명은 클라이언트 요청 문구(profileImage) 대신 기존 유저 DTO 관례인 imgUrl로 확정했다(사용자 확정). 값은 FileImageUtils.imagePath()로 풀 URL 변환한다.
  - evidence: src/main/java/com/mybury/waver/web/message/v1/user/UserElement.java
```

## 검증

빌드 도구는 Maven이에요(`pom.xml`, `mvnw`).

1. `./mvnw -q compile` — 컴파일 성공(exit 0)이면 통과예요.
2. `./mvnw -q test` — 기존 테스트(JwtTokenParserTest 등) 전부 통과하면 돼요. 버킷 상세 전용 테스트는 현재 없어요.
3. 수동 확인(선택): 앱 기동 후 `GET /waver/bucket/{id}`를 함께하기 버킷으로 호출해서 `friendUsers[].imgUrl`(풀 URL), `friendUsers[].goalCount`, `friendUsers[].userCount`가 최상위 goalCount/userCount와 같은 값으로 내려오는지 확인해요.
