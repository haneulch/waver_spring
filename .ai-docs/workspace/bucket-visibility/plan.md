# bucket-visibility 버킷 공개 범위 적용

## 의도

버킷 작성자가 설정한 공개 범위와 실제 조회 가능 대상이 다른 문제를 해결한다. 일반 버킷은 `PRIVATE`일 때 소유자만, `PUBLIC`일 때 전체 사용자, `FOLLOWER`일 때 소유자와 맞팔로우 사용자가 열람한다. 함께하기 버킷은 항상 `PUBLIC`으로 저장한다. 공개 범위에 맞는 사용자에게만 버킷 내용과 목록 개수를 반환하면 완료한다. 사용자는 네 이슈를 공개 범위, 내 목록 정렬, 알림 정렬, 마이버리 이관 버킷 비공개 순서로 확인하고 수정하도록 요청했다.

## 확인한 사실

- `MyService.getOther`는 `OtherMyResponse` 생성자에 대상 사용자를 전달하고, 생성자는 `user.getBucketlist()` 전체를 응답으로 변환한다. 공개 범위와 삭제 여부를 검사하지 않아 타인 프로필 경로에서 비공개 버킷이 반환될 수 있다.
- `BucketAccessPolicy.canView`는 소유자와 참여자를 공개 상태와 무관하게 허용하고, `FOLLOWER`는 맞팔로우 사용자에게도 허용한다. 사용자는 `FOLLOWER`의 맞팔로우 공개가 맞다고 정정했고, 함께하기 버킷은 항상 `PUBLIC`으로 고정한다고 확인했다.
- `BucketService.create`와 `update`는 요청의 공개 값을 저장한다. 두 경로에서 호출하는 `normalizeContentType`은 함께하기의 종류만 보정하며 공개 값은 보정하지 않는다. `Bucket.isTogether`는 `friendUserIds` 지정 여부로 함께하기를 판단한다.
- `FeedService.copy`는 `Bucket.copy`로 스크랩 버킷을 저장한다. 이 팩토리는 원본 `friendUserIds`를 복사하지만 공개 값을 지정하지 않아 기본값 `PRIVATE`가 적용된다. 생성·수정만 보정하면 스크랩으로 생성된 함께하기 버킷에는 `PUBLIC` 고정 규칙이 적용되지 않는다.
- `BucketRepositoryImpl.findFeed`도 `FOLLOWER`를 맞팔로우 기준으로 판단한다. 일반 탐색과 검색은 `PUBLIC` 조건을 적용한다.
- `MyburyMigrationService.migrateBuckets`는 이관 버킷을 이미 `PRIVATE`로 저장한다. 타인 프로필의 필터 누락은 이관 버킷에도 영향을 준다.
- 저장소는 `pom.xml` 기준 Java 21, Spring Boot 3.5.4를 사용한다. 설치된 Java 21 경로는 `/Library/Java/JavaVirtualMachines/jdk-21.0.2.jdk/Contents/Home`이다.

## 합의

### 공개 범위 준수

- 바꾸는 것: 타인 프로필의 버킷 목록에 열람 권한 검사를 적용한다.
- 모양: 타인 프로필은 열람 가능한 삭제되지 않은 버킷만 반환하고, 전체·완료·진행 개수도 반환 목록으로 계산한다. `FOLLOWER`의 맞팔로우 공개 기준과 기존 응답 필드를 유지한다.
- 이유: 기존 `BucketAccessPolicy`를 재사용하면 프로필에 별도 권한 정책을 만들지 않고 누락된 검사를 보완할 수 있다.
- 적용 범위: `MyService`, `OtherMyResponse`와 관련 회귀 테스트. 사용자 요청 1번과 공개 대상 확인에 근거하며, 기존 구조를 활용하는 수정 방법은 요청된 버그 수정 범위에서 정한다.

### 함께하기 공개 값 고정

- 바꾸는 것: 버킷 생성·수정·스크랩 시 함께하기 버킷의 공개 값을 `PUBLIC`으로 고정한다.
- 모양: `friendUserIds`가 지정된 버킷은 요청의 `exposureStatus`가 `PRIVATE` 또는 `FOLLOWER`여도 `PUBLIC`으로 저장하고 응답한다. 일반 버킷은 요청의 공개 값을 사용한다. API 필드와 공개 상태 코드 목록은 유지한다.
- 이유: 생성·수정에서 공통으로 사용하는 함께하기 보정 경로에 공개 값 보정을 추가하면 클라이언트별 요청 차이와 무관하게 동일한 저장 규칙을 적용할 수 있다.
- 적용 범위: `BucketService`의 생성·수정 공통 보정 경로, `Bucket.copy`의 스크랩 생성, 생성·수정 요청의 API 설명, 관련 회귀 테스트. 출처는 사용자의 함께하기 버킷 `PUBLIC` 고정 지시다.

## 연결

- 사용자 지시에 따라 네 이슈의 조사와 수정 범위 확인을 먼저 끝내고 한 번에 수정한다. 이슈별 구현 승인을 반복하지 않는다.
- 수정·검증 후 커밋을 `develop`에 반영하고, 사용자의 최종 지시에 따라 원격 `develop`에 push한다.
- `mybury-private-buckets`의 타인 프로필 노출 차단은 이 이슈의 공개 범위 검사에 의존한다.
- 기존 저장 데이터의 공개 값과 현재 배포본은 조회하지 않았다. 생성·수정 코드 보정만으로 기존 행의 저장 값이 일괄 변경되지는 않는다.

## 검증

- 타인 프로필의 버킷 목록과 집계에서 권한 없는 버킷이 제외되는지 검증한다.
- 일반 버킷의 소유자, 맞팔로우 사용자, 한쪽만 팔로우한 사용자, 일반 사용자별 공개 범위 회귀 테스트를 작성한다.
- 함께하기 생성·수정 요청에 `PRIVATE`, `FOLLOWER`, `PUBLIC`을 전달해 모두 `PUBLIC`으로 저장·응답하는지 검증한다. 일반 버킷의 공개 값은 유지되는지 검증한다.
- 함께하기 스크랩의 공개 값도 확인해 생성 경로별 정책 누락을 검증한다.
- 기존 테스트 두 개는 `@SpringBootTest`로 전체 애플리케이션을 기동하며 DB·외부 서비스 설정에 의존하므로 이번 독립 검증에 포함하지 않았다. 실제 DB 접속과 기존 데이터 변경은 수행하지 않았다.
- Java 21에서 아래 명령으로 전체 소스를 컴파일하고 회귀 테스트 33개가 통과했다. 임시 Maven 설정은 `central`과 `team-store-groups` 저장소 ID를 모두 선언하여 기존 캐시를 사용하며 저장소의 빌드 설정은 변경하지 않았다.

```sh
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.0.2.jdk/Contents/Home \
mvn -o -s /private/tmp/waver-review-settings.xml \
  -Dtest=MyServicePrivacyTest,MyburyMigrationPrivacyTest,BucketVisibilityTest,MyControllerSortTest,AlarmServiceTest \
  -DargLine=-javaagent:/Users/a1000369/.m2/repository/org/mockito/mockito-core/5.17.0/mockito-core-5.17.0.jar test
```

- 공개 범위·프로필 회귀 검사는 5개, 함께하기 생성·수정·스크랩 검사는 18개가 통과했다. DB를 대체한 저장소 모의 객체를 사용하므로 실제 배포 환경의 저장 값 검증을 대신하지 않는다.
