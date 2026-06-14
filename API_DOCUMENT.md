# Waver API Document

> Base URL: `/waver`
> 
> Authentication: JWT Bearer Token (required for all endpoints unless marked **[Public]**)

---

## Table of Contents

- [Main (인증)](#main-인증)
- [User (유저)](#user-유저)
- [Bucket (버킷리스트)](#bucket-버킷리스트)
- [Category (카테고리)](#category-카테고리)
- [Feed (피드)](#feed-피드)
- [My (마이)](#my-마이)
- [Follow (팔로우)](#follow-팔로우)
- [Comment (댓글)](#comment-댓글)
- [Explore (검색)](#explore-검색)
- [Badge (배지)](#badge-배지)
- [Subscription (구독)](#subscription-구독)
- [Image (이미지)](#image-이미지)
- [Utility (유틸리티)](#utility-유틸리티)

---

## Main (인증)

### POST `/waver/login` [Public]

로그인

**Request Body** (`application/json`)

| Field | Type   | Required | Description     |
|-------|--------|----------|-----------------|
| uid   | String | ✅       | Firebase UID    |

**Response** `200 OK`

| Field         | Type   | Description                        |
|---------------|--------|------------------------------------|
| token         | String | JWT Access Token                   |
| premiumStatus | String | 프리미엄 상태 (`NONE`, `PLUS`, ...) |

**Error Responses**

| Code | Key              | Description    |
|------|------------------|----------------|
| 9000 | WITHDRAWAL_USER  | 탈퇴한 사용자  |

---

## User (유저)

### POST `/waver/user/join` [Public]

회원가입

**Request Body** (`multipart/form-data`)

| Field        | Type   | Required | Description  |
|--------------|--------|----------|--------------|
| profileImage | File   | ❌       | 프로필 이미지 |
| name         | String | ✅       | 닉네임       |
| bio          | String | ❌       | 소개글       |
| uid          | String | ✅       | Firebase UID |

**Response** `200 OK`

| Field         | Type   | Description        |
|---------------|--------|--------------------|
| token         | String | JWT Access Token   |
| premiumStatus | String | 프리미엄 상태 (`NONE`) |

---

### GET `/waver/user/profile`

프로필 조회 (본인 또는 타인)

**Query Parameters**

| Param       | Type | Required | Description                     |
|-------------|------|----------|---------------------------------|
| otherUserId | Long | ❌       | 타인 조회 시 해당 유저 ID 전달   |

**Response** `200 OK` — `ProfileResponse`

| Field       | Type    | Description     |
|-------------|---------|-----------------|
| userId      | Long    | 유저 ID         |
| name        | String  | 닉네임          |
| bio         | String  | 소개글          |
| profileImage| String  | 프로필 이미지 URL|
| isFollowing | Boolean | 팔로우 여부     |

---

### PATCH `/waver/user/profile`

프로필 수정

**Request Body** (`multipart/form-data`)

| Field        | Type   | Required | Description  |
|--------------|--------|----------|--------------|
| profileImage | File   | ❌       | 프로필 이미지 |
| name         | String | ❌       | 닉네임       |
| bio          | String | ❌       | 소개글       |

**Response** `200 OK` (no body)

---

### GET `/waver/user/profile/name` [Public]

프로필 이름 중복 확인

**Query Parameters**

| Param | Type   | Required | Description |
|-------|--------|----------|-------------|
| name  | String | ✅       | 확인할 닉네임 |

**Response** `200 OK` (no body — 사용 가능 시 정상, 중복 시 에러)

---

### GET `/waver/user/check/limit`

웨이버 플러스 제한 확인

**Response** `200 OK`

| Field         | Type   | Description      |
|---------------|--------|------------------|
| premiumStatus | String | 현재 프리미엄 상태 |

---

### POST `/waver/user/withdraw`

회원 탈퇴

**Response** `200 OK` (no body)

---

### POST `/waver/user/status` [Public]

사용자 상태 확인 (탈퇴 계정 포함)

**Request Body** (`application/json`)

| Field | Type   | Required | Description     |
|-------|--------|----------|-----------------|
| email | String | ✅       | 이메일          |
| uid   | String | ✅       | Firebase UID    |

**Response** `200 OK` — `UserStatusResponse`

---

### POST `/waver/user/fcm-token`

FCM 토큰 업데이트

**Request Body** (`text/plain`)

| Body    | Type   | Description    |
|---------|--------|----------------|
| (plain) | String | FCM 디바이스 토큰 |

**Response** `200 OK` (no body)

---

## Bucket (버킷리스트)

### POST `/waver/bucket`

버킷리스트 등록

**Request Body** (`multipart/form-data`)

| Field       | Type    | Required | Description          |
|-------------|---------|----------|----------------------|
| title       | String  | ✅       | 버킷리스트 제목        |
| categoryId  | Long    | ✅       | 카테고리 ID           |
| goalCount   | Integer | ❌       | 목표 달성 횟수         |
| image       | File    | ❌       | 첨부 이미지            |
| isPublic    | Boolean | ❌       | 공개 여부             |

**Response** `200 OK` — `BucketDetailResponse`

---

### GET `/waver/bucket`

버킷리스트 목록

**Query Parameters**

| Param      | Type    | Required | Description            |
|------------|---------|----------|------------------------|
| categoryId | Long    | ❌       | 카테고리 필터           |
| status     | String  | ❌       | 상태 필터 (진행중/완료 등)|

**Response** `200 OK` — `BucketResponse`

---

### GET `/waver/bucket/popular`

인기 버킷리스트 조회

**Response** `200 OK` — `GetPopularBucketResponse`

---

### POST `/waver/bucket/{id}`

버킷리스트 수정

**Path Parameters**

| Param | Type | Description   |
|-------|------|---------------|
| id    | Long | 버킷리스트 ID  |

**Request Body** (`multipart/form-data`) — `BucketUpdateRequest` (버킷 등록과 동일 구조)

**Response** `200 OK` — `BucketDetailResponse`

---

### GET `/waver/bucket/{id}`

버킷리스트 상세

**Path Parameters**

| Param | Type | Description   |
|-------|------|---------------|
| id    | Long | 버킷리스트 ID  |

**Response** `200 OK` — `BucketDetailResponse`

---

### DELETE `/waver/bucket/{id}`

버킷리스트 삭제

**Path Parameters**

| Param | Type | Description   |
|-------|------|---------------|
| id    | Long | 버킷리스트 ID  |

**Response** `200 OK` (no body)

---

### GET `/waver/bucket/{id}/achieve`

버킷리스트 달성

**Path Parameters**

| Param | Type | Description   |
|-------|------|---------------|
| id    | Long | 버킷리스트 ID  |

**Response** `200 OK` (no body)

---

### GET `/waver/bucket/{id}/achieve/cancel`

버킷리스트 달성 취소

**Path Parameters**

| Param | Type | Description   |
|-------|------|---------------|
| id    | Long | 버킷리스트 ID  |

**Response** `200 OK` (no body)

---

### GET `/waver/bucket/{id}/reset`

버킷리스트 다시 도전하기

**Path Parameters**

| Param | Type | Description   |
|-------|------|---------------|
| id    | Long | 버킷리스트 ID  |

**Response** `200 OK` (no body)

---

### PATCH `/waver/bucket/{id}/goalCount`

버킷리스트 달성 횟수 수정

**Path Parameters**

| Param | Type | Description   |
|-------|------|---------------|
| id    | Long | 버킷리스트 ID  |

**Request Body** (`application/json`)

| Field     | Type    | Required | Description  |
|-----------|---------|----------|--------------|
| goalCount | Integer | ✅       | 목표 달성 횟수 |

**Response** `200 OK` (no body)

---

## Category (카테고리)

### GET `/waver/category`

카테고리 목록

**Query Parameters**

| Param | Type   | Required | Description   |
|-------|--------|----------|---------------|
| query | String | ❌       | 검색 키워드    |

**Response** `200 OK` — `List<CategoryResponse>`

| Field | Type   | Description  |
|-------|--------|--------------|
| id    | Long   | 카테고리 ID   |
| name  | String | 카테고리 이름  |
| seq   | Integer| 순서         |

---

### POST `/waver/category`

카테고리 추가

**Request Body** (`application/json`)

| Field | Type   | Required | Description  |
|-------|--------|----------|--------------|
| name  | String | ✅       | 카테고리 이름  |

**Response** `200 OK` (no body)

---

### PATCH `/waver/category/{id}`

카테고리 이름 변경

**Path Parameters**

| Param | Type | Description  |
|-------|------|--------------|
| id    | Long | 카테고리 ID   |

**Request Body** (`application/json`)

| Field | Type   | Required | Description      |
|-------|--------|----------|------------------|
| name  | String | ✅       | 변경할 카테고리 이름 |

**Response** `200 OK` (no body)

---

### DELETE `/waver/category/{id}`

카테고리 삭제

**Path Parameters**

| Param | Type | Description  |
|-------|------|--------------|
| id    | Long | 카테고리 ID   |

**Response** `200 OK` (no body)

---

### PATCH `/waver/category/seq`

카테고리 순서 변경

**Request Body** (`application/json`)

| Field       | Type      | Required | Description              |
|-------------|-----------|----------|--------------------------|
| categoryIds | List<Long>| ✅       | 순서대로 정렬된 카테고리 ID 목록 |

**Response** `200 OK` (no body)

---

## Feed (피드)

### GET `/waver/feeds`

피드 목록

**Query Parameters**

| Param   | Type | Required | Description                           |
|---------|------|----------|---------------------------------------|
| nextKey | Long | ❌       | 페이지네이션 커서 (이전 응답의 nextKey 값) |

**Response** `200 OK` — `FeedResponse`

**Error Responses**

| Code | Key               | Description              |
|------|-------------------|--------------------------|
| 8000 | KEYWORD_NOT_FOUND | 저장된 관심 키워드 없음     |

---

### POST `/waver/feeds/keyword`

관심 키워드 저장

**Request Body** (`application/json`)

| Field        | Type        | Required | Description         |
|--------------|-------------|----------|---------------------|
| keywordCodes | List<String>| ✅       | 관심 키워드 코드 목록  |

**Response** `200 OK` (no body)

---

### POST `/waver/feeds/{id}/like`

피드 좋아요 / 취소 (토글)

**Path Parameters**

| Param | Type | Description |
|-------|------|-------------|
| id    | Long | 피드(버킷) ID |

**Response** `200 OK` (no body)

---

### POST `/waver/feeds/{id}/scrap`

피드 스크랩 (복사)

**Path Parameters**

| Param | Type | Description |
|-------|------|-------------|
| id    | Long | 피드(버킷) ID |

**Response** `200 OK` — `FeedCopyResponse`

| Field     | Type | Description       |
|-----------|------|-------------------|
| copiedId  | Long | 복사된 버킷리스트 ID |

---

### POST `/waver/feeds/{id}/report`

피드 신고

**Path Parameters**

| Param | Type | Description |
|-------|------|-------------|
| id    | Long | 피드(버킷) ID |

**Request Body** (`application/json`)

| Field  | Type   | Required | Description |
|--------|--------|----------|-------------|
| reason | String | ✅       | 신고 사유    |

**Response** `200 OK` (no body)

**Error Responses**

| Code | Key       | Description             |
|------|-----------|-------------------------|
| 4040 | NOT_FOUND | 버킷을 찾을 수 없음         |
| 4030 | FORBIDDEN | 자기 버킷 신고 불가          |

---

## My (마이)

### GET `/waver/my`

마이페이지 메인

**Response** `200 OK` — `MyResponse`

---

### GET `/waver/my/{otherUserId}`

타인 마이페이지 메인

**Path Parameters**

| Param       | Type | Description  |
|-------------|------|--------------|
| otherUserId | Long | 조회할 유저 ID |

**Response** `200 OK` — `OtherMyResponse`

---

### GET `/waver/my/push`

푸시 알림 목록

**Response** `200 OK` — `AlarmResponse`

---

### GET `/waver/my/info`

내 웨이브 정보

**Response** `200 OK` — `MyWaveInfoResponse`

---

## Follow (팔로우)

### GET `/waver/follow`

팔로우 / 팔로워 목록

**Response** `200 OK` — `GetFollowersResponse`

---

### POST `/waver/follow`

팔로우

**Request Body** (`application/json`)

| Field        | Type | Required | Description     |
|--------------|------|----------|-----------------|
| followUserId | Long | ✅       | 팔로우할 유저 ID  |

**Response** `200 OK` (no body)

---

### POST `/waver/follow/unfollow`

언팔로우

**Request Body** (`application/json`)

| Field        | Type | Required | Description       |
|--------------|------|----------|-------------------|
| followUserId | Long | ✅       | 언팔로우할 유저 ID   |

**Response** `200 OK` (no body)

---

### GET `/waver/follow/mutual`

맞팔 목록

**Response** `200 OK` — `List<FollowElement>`

| Field  | Type   | Description |
|--------|--------|-------------|
| userId | Long   | 유저 ID     |
| name   | String | 닉네임      |

---

## Comment (댓글)

### POST `/waver/comment`

댓글 등록

**Request Body** (`application/json`)

| Field    | Type   | Required | Description    |
|----------|--------|----------|----------------|
| bucketId | Long   | ✅       | 버킷리스트 ID   |
| content  | String | ✅       | 댓글 내용       |

**Response** `200 OK` (no body)

---

### PATCH `/waver/comment/{id}`

댓글 수정

**Path Parameters**

| Param | Type | Description |
|-------|------|-------------|
| id    | Long | 댓글 ID     |

**Request Body** (`application/json`)

| Field   | Type   | Required | Description  |
|---------|--------|----------|--------------|
| content | String | ✅       | 수정할 댓글 내용 |

**Response** `200 OK` (no body)

---

### DELETE `/waver/comment/{id}`

댓글 삭제

**Path Parameters**

| Param | Type | Description |
|-------|------|-------------|
| id    | Long | 댓글 ID     |

**Response** `200 OK` (no body)

---

### PATCH `/waver/comment/{id}/report`

댓글 신고 *(미구현)*

**Path Parameters**

| Param | Type | Description |
|-------|------|-------------|
| id    | Long | 댓글 ID     |

**Request Body** (`application/json`)

| Field  | Type   | Required | Description |
|--------|--------|----------|-------------|
| reason | String | ✅       | 신고 사유    |

---

### PATCH `/waver/comment/{id}/hide`

댓글 숨기기

**Path Parameters**

| Param | Type | Description |
|-------|------|-------------|
| id    | Long | 댓글 ID     |

**Response** `200 OK` (no body)

---

## Explore (검색)

### GET `/waver/explore`

검색

**Query Parameters**

| Param | Type   | Required | Description |
|-------|--------|----------|-------------|
| query | String | ✅       | 검색어       |

**Response** `200 OK` — `ExploreResponse`

---

### GET `/waver/explore/keywords`

키워드 전체 목록 조회

**Response** `200 OK` — `List<KeywordResponse>`

---

### GET `/waver/explore/searchOptions`

최근 검색어 & 추천 키워드 조회

**Response** `200 OK` — `SearchOptionResponse`

---

### DELETE `/waver/explore/recentSearch/all`

최근 검색어 전체 삭제

**Response** `200 OK` (no body)

---

### DELETE `/waver/explore/recentSearch/{keyword}`

최근 검색어 개별 삭제

**Path Parameters**

| Param   | Type   | Description |
|---------|--------|-------------|
| keyword | String | 삭제할 검색어 |

**Response** `200 OK` (no body)

---

## Badge (배지)

### GET `/waver/badge`

배지 목록 조회

**Response** `200 OK` — `List<BadgeResponse>`

---

### POST `/waver/badge/{badgeId}`

배지 선택

**Path Parameters**

| Param   | Type | Description |
|---------|------|-------------|
| badgeId | Long | 배지 ID     |

**Response** `200 OK` (no body)

---

## Subscription (구독)

### POST `/waver/subscribe`

구독

**Request Body** (`application/json`)

| Field       | Type | Required | Description  |
|-------------|------|----------|--------------|
| subscribeId | Long | ✅       | 구독 대상 ID  |

**Response** `200 OK` (no body)

---

## Image (이미지)

### GET `/waver/image/{*path}` [Public]

이미지 조회

**Path Parameters**

| Param | Type   | Description              |
|-------|--------|--------------------------|
| path  | String | 이미지 파일 경로 (와일드카드) |

**Response** `200 OK` — 이미지 바이너리 (Content-Type: 파일 형식에 따라 자동)

---

## Utility (유틸리티)

### GET `/waver/utility/codes/{groupCode}`

코드 목록 조회

**Path Parameters**

| Param     | Type   | Description                        |
|-----------|--------|------------------------------------|
| groupCode | String | 코드 그룹 코드 (예: `REPORT` — 신고 내용 목록) |

**Response** `200 OK` — `List<CodeResponse>`

| Field | Type   | Description |
|-------|--------|-------------|
| code  | String | 코드 값      |
| name  | String | 코드 이름     |

**Error Responses**

| Code | Key         | Description          |
|------|-------------|----------------------|
| 400  | BAD_REQUEST | 존재하지 않는 그룹 코드 |
