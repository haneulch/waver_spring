# bucket-list-order 내 버킷 목록 정렬 확인

## 의도

내 버킷 목록에서 업데이트순과 등록순을 선택해도 같은 응답이 오는 것 같다는 사용자 신고를 확인한다. 실제 요청이 각 정렬 기준에 맞게 처리되고, 생성 시각 순서와 수정 시각 순서가 다른 데이터에서 올바른 결과가 반환되면 완료한다.

## 확인한 사실

- `BucketController.bucketList`의 `GET /waver/bucket`은 `BucketRequest.sort`를 받고, `BucketService.bucketList`는 공개 조건을 보정할 때에도 이 값을 유지한다.
- `BucketRepositoryImpl.findBucketInternal`은 `CREATED`를 `createdAt ASC`, `UPDATED`를 `updatedAt ASC`, `CREATED_DESC`를 `createdAt DESC`, `UPDATED_DESC`를 `updatedAt DESC`로 처리한다. 생략 시 `updatedAt DESC`다. 정렬 필드 자체가 같은 필드로 연결된 오류는 확인되지 않았다.
- `MyController.my`의 `GET /waver/my`는 정렬 파라미터를 받지 않는다. `MyResponse`는 `user.getBucketlist()`의 순서를 그대로 사용한다.
- `BaseEntity.updatedAt`에는 `@LastModifiedDate`가 있고 `JpaConfig`는 Auditing을 활성화한다. `BucketService.update`는 트랜잭션 안에서 관리 엔티티의 내용을 수정한다.
- 달성·취소·초기화·목표 횟수 수정의 벌크 쿼리는 수정 시각을 명시적으로 변경하지 않는다. `BaseEntity`와 `sql/local-test-data.sql`은 DB의 `ON UPDATE CURRENT_TIMESTAMP`를 선언하지만 실제 운영 스키마는 확인하지 않았다. 이 사실만으로 수정 시각 갱신 실패를 단정할 수 없다.
- 함께하기에서 소유자가 아닌 참여자의 달성·취소·초기화는 `BucketMember`를 변경한다. 목록 정렬은 `Bucket.updatedAt`을 사용하므로 참여자 진행도 변경 시각 자체는 정렬 기준이 아니다.
- 생성 순서와 수정 순서가 같으면 등록순·업데이트순의 결과가 같은 것은 정상이다. 사용자 앱의 실제 두 요청 URL과 사용 데이터는 아직 전달되지 않았다.
- Java 21과 Spring 6.2.9를 사용한 임시 `SortBindingCheck.java`에서 다섯 `sort` 값의 요청 DTO 바인딩 및 `withExposureStatus` 이후 보존, 생략 시 null 처리까지 6개 검사가 통과했다. 위치는 `/private/tmp/waver-sort-binding-check/SortBindingCheck.java`다. 실제 컨트롤러·DB를 연결한 테스트는 아니다.
- `JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.0.2.jdk/Contents/Home mvn -o -DskipTests compile dependency:build-classpath -Dmdep.outputFile=/private/tmp/waver-issue-review-classpath.txt`는 gRPC 1.62.2 의존성의 로컬 캐시 저장소 ID와 현재 Maven 미러 ID가 달라 의존성 해석 단계에서 실패했다. 전체 빌드 성공으로 보고하지 않는다.

## 합의

### 선택한 정렬 기준 적용

- 바꾸는 것: `GET /waver/my`가 기존 버킷 목록과 동일한 `sort` 값을 받아 정렬하도록 수정한다.
- 모양: `CREATED`, `CREATED_DESC`, `UPDATED`, `UPDATED_DESC`, `LIKE_COUNT_DESC`를 받는다. 생략 시 최신 등록순 `CREATED_DESC`를 적용한다. 같은 정렬값은 ID로 순서를 고정하고 삭제되지 않은 본인 버킷을 반환한다. 기존 응답 필드를 유지한다.
- 이유: 사용자가 정렬 파라미터 추가와 기본 최신 등록순을 확인했다. Spring Data의 정렬 조회로 기존 전체 목록을 정렬하면 새 정렬 프레임워크 없이 요구를 충족한다.
- 적용 범위: `MyController`, `MyService`, `MyResponse`, `BucketRepository`와 관련 테스트. 기존 `/waver/bucket`의 정렬 규칙은 유지한다.

## 연결

- 네 이슈를 모두 확인한 뒤 한 번에 수정한다.

## 검증

- `GET /waver/my`에 각 sort 값을 전달했을 때 선택값이 조회 정렬까지 전달되는지 확인한다.
- 각 정렬 선택이 생성 시각·수정 시각·좋아요 필드 및 오름차순·내림차순으로 올바르게 전달되는지 검증한다.
- 정렬값 생략과 기존 오름차순·내림차순 계약도 확인한다.
- `MyControllerSortTest`의 MockMvc 검사 6개가 통과했다. 실제 컨트롤러·서비스를 실행하여 쿼리 파라미터부터 Spring Data `Sort` 전달과 응답 순서까지 확인했고, 저장소는 모의 객체여서 실제 DB 정렬 실행은 포함하지 않았다. 실행 명령은 `bucket-visibility/plan.md`의 검증 절을 따른다.
