# alarm-newest-first 알림 최신순 조회

## 의도

알림 목록을 보는 사용자가 최근 알림부터 확인하도록 한다. 알림 조회 응답이 생성 시각 내림차순으로 반환되면 완료한다.

## 확인한 사실

- `MyController.push`의 `GET /waver/my/push`는 `AlarmService.getList`를 호출한다.
- `AlarmService.getList`는 `AlarmRepository.findByUserId`를 호출하며 정렬 조건이 없다.
- `AlarmResponse.of`는 조회 결과의 순서를 그대로 유지한다.
- `Alarm`은 `BaseEntity`의 `createdAt`을 상속하고, 고유 ID가 있다. 최신순 조회를 위해 새 컬럼을 만들 필요가 없다.

## 합의

### 알림 최신순 반환

- 바꾸는 것: 알림 목록을 최신순으로 조회한다.
- 모양: 기존 응답 필드를 유지하면서 생성 시각 내림차순, 같은 시각은 ID 내림차순으로 반환한다.
- 이유: 사용자 요청 3번에 따른다. 기존 저장 시각과 Spring Data 정렬 기능을 활용할 수 있다.
- 적용 범위: `AlarmRepository`, `AlarmService`의 목록 조회와 검증.

## 연결

- 네 이슈를 모두 확인한 뒤 한 번에 수정한다.

## 검증

- 생성 시각이 서로 다른 알림이 최신순으로 반환되는지 확인한다.
- 생성 시각이 같은 알림의 순서가 일관적인지 확인한다.
- 다른 사용자의 알림이 섞이지 않는지와 빈 목록을 확인한다.
- `AlarmServiceTest` 3개가 통과했다. Spring Data의 파생 쿼리 파서로 사용자 조건과 `createdAt DESC, id DESC`를 확인하고, 서비스의 사용자 ID 전달·응답 순서 유지·빈 목록을 검증했다. 실제 DB 쿼리 실행은 포함하지 않았다. 실행 명령은 `bucket-visibility/plan.md`의 검증 절을 따른다.
