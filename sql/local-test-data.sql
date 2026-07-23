-- =====================================================================
-- Waver 로컬 테스트용 DDL + 샘플 데이터 (MySQL)
-- =====================================================================
-- 용도: 로컬에서 스키마 + 시드 데이터를 한 번에 세팅.
-- 대상 DB: bucket (application.yml 기본값 jdbc:mysql://localhost:3306/bucket)
-- 실행:
--   mysql -u root -p bucket < sql/local-test-data.sql
-- 주의:
--   - 재실행 가능하도록 DROP TABLE IF EXISTS 로 초기화한다 (기존 데이터 삭제됨).
--   - 컬럼/타입은 Hibernate(ddl-auto) 생성 스키마와 최대한 일치.
--   - 앱을 ddl-auto=create 로 띄우면 스키마가 다시 생성되므로,
--     이 스크립트로 테스트하려면 ddl-auto 를 none/validate 로 두거나
--     앱 기동 후 데이터 부분만 실행할 것.
-- =====================================================================

SET NAMES utf8mb4;

-- ---------------------------------------------------------------------
-- DDL
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS `alarm`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `like_bucket`;
DROP TABLE IF EXISTS `report`;
DROP TABLE IF EXISTS `follow`;
DROP TABLE IF EXISTS `recent_search`;
DROP TABLE IF EXISTS `bucket`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `subscribe`;
DROP TABLE IF EXISTS `free_tier`;
DROP TABLE IF EXISTS `badge`;
DROP TABLE IF EXISTS `badge_type`;
DROP TABLE IF EXISTS `user_keyword`;
DROP TABLE IF EXISTS `keyword`;
DROP TABLE IF EXISTS `common_code`;
DROP TABLE IF EXISTS `config`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  id             BIGINT       NOT NULL AUTO_INCREMENT,
  uid            VARCHAR(1500) NOT NULL,
  email          VARCHAR(255) NOT NULL,
  account_type   VARCHAR(10)  NOT NULL DEFAULT 'ANDROID',
  name           VARCHAR(255) NOT NULL,
  img_url        VARCHAR(255) NULL,
  bio            VARCHAR(255) NULL,
  locale         VARCHAR(255) NULL,
  fcm_token      VARCHAR(255) NULL,
  alarm_yn       VARCHAR(1)   NOT NULL DEFAULT 'N',
  delete_yn      VARCHAR(1)   NOT NULL DEFAULT 'N',
  status         VARCHAR(10)  NOT NULL DEFAULT 'ACTIVE',
  premium_status VARCHAR(10)  NOT NULL DEFAULT 'NONE',
  last_login_at  DATETIME(6)  NULL,
  withdrawn_at   DATETIME(6)  NULL,
  created_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  -- uid VARCHAR(1500) 전체에 유니크를 걸면 인덱스 최대 길이(3072 bytes)를 초과하므로 prefix 인덱스 사용
  UNIQUE KEY uk_user_uid (uid(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `badge_type` (
  id       BIGINT       NOT NULL AUTO_INCREMENT,
  code     VARCHAR(20)  NOT NULL,
  title    VARCHAR(255) NULL,
  img_url1 VARCHAR(255) NULL,
  img_url2 VARCHAR(255) NULL,
  img_url3 VARCHAR(255) NULL,
  img_url4 VARCHAR(255) NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `badge` (
  id            BIGINT      NOT NULL AUTO_INCREMENT,
  user_id       BIGINT      NULL,
  badge_type_id BIGINT      NULL,
  achieve_count INT         NOT NULL DEFAULT 0,
  select_yn     VARCHAR(1)  NOT NULL DEFAULT 'N',
  achieve_yn    VARCHAR(1)  NOT NULL DEFAULT 'N',
  created_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `subscribe` (
  id            BIGINT       NOT NULL AUTO_INCREMENT,
  user_id       BIGINT       NOT NULL,
  billing_cycle VARCHAR(10)  NOT NULL,
  status        VARCHAR(25)  NOT NULL,
  start_at      DATETIME(6)  NOT NULL,
  expired_at    DATETIME(6)  NOT NULL,
  cancelled_at  DATETIME(6)  NULL,
  subscribe_id  VARCHAR(255) NOT NULL,
  created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `category` (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  name       VARCHAR(255) NULL,
  user_id    BIGINT       NULL,
  seq        INT          NOT NULL,
  deleted    VARCHAR(1)   NOT NULL DEFAULT 'N',
  default_yn VARCHAR(1)   NOT NULL DEFAULT 'N',
  created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `bucket` (
  id              BIGINT       NOT NULL AUTO_INCREMENT,
  title           VARCHAR(255) NOT NULL,
  memo            VARCHAR(255) NULL,
  user_id         BIGINT       NULL,
  category_id     BIGINT       NULL,
  type            VARCHAR(10)  NOT NULL DEFAULT 'ORIGINAL',
  exposure_status VARCHAR(10)  NOT NULL DEFAULT 'PRIVATE',
  pin             VARCHAR(1)   NOT NULL DEFAULT 'N',
  status          VARCHAR(10)  NOT NULL DEFAULT 'PROGRESS',
  scrap_yn        VARCHAR(1)   NOT NULL DEFAULT 'N',
  target_date     DATE         NULL,
  user_count      INT          NOT NULL DEFAULT 0,
  goal_count      INT          NOT NULL DEFAULT 0,
  completed_date  DATETIME(6)  NULL,
  seq             INT          NOT NULL DEFAULT 0,
  img_url         VARCHAR(255) NULL,
  like_count      INT          NOT NULL DEFAULT 0,
  keywords        VARCHAR(255) NULL,
  friend_user_ids VARCHAR(255) NULL,
  deleted         VARCHAR(1)   NOT NULL DEFAULT 'N',
  created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `comment` (
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  comment     VARCHAR(255) NOT NULL,
  mention_ids VARCHAR(255) NULL,
  user_id     BIGINT       NULL,
  bucket_id   BIGINT       NULL,
  is_blocked  VARCHAR(1)   NOT NULL DEFAULT 'N',
  is_hide     VARCHAR(1)   NOT NULL DEFAULT 'N',
  created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `like_bucket` (
  id         BIGINT    NOT NULL AUTO_INCREMENT,
  user_id    BIGINT    NULL,
  bucket_id  BIGINT    NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `follow` (
  id             BIGINT    NOT NULL AUTO_INCREMENT,
  user_id        BIGINT    NULL,
  follow_user_id BIGINT    NULL,
  created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `recent_search` (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  query      VARCHAR(255) NOT NULL,
  user_id    BIGINT       NULL,
  created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `report` (
  id             BIGINT       NOT NULL AUTO_INCREMENT,
  reason         VARCHAR(255) NOT NULL,
  report_type    VARCHAR(10)  NOT NULL DEFAULT 'BUCKET',
  bucketlist_id  BIGINT       NULL,
  comment_id     BIGINT       NULL,
  report_user_id BIGINT       NULL,
  created_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `alarm` (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  user_id    BIGINT       NULL,
  type       VARCHAR(20)  NULL,
  message    VARCHAR(255) NULL,
  img_url    VARCHAR(255) NULL,
  is_read    BIT(1)       NOT NULL,
  created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `free_tier` (
  id             BIGINT    NOT NULL AUTO_INCREMENT,
  user_id        BIGINT    NOT NULL,
  image_limit    INT       NOT NULL DEFAULT 0,
  together_limit INT       NOT NULL DEFAULT 0,
  image_used     INT       NOT NULL DEFAULT 0,
  together_used  INT       NOT NULL DEFAULT 0,
  created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_keyword` (
  id      INT          NOT NULL AUTO_INCREMENT,
  code    VARCHAR(255) NULL,
  user_id BIGINT       NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `keyword` (
  id           INT          NOT NULL AUTO_INCREMENT,
  type         VARCHAR(10)  NOT NULL DEFAULT 'KEYWORD',
  name         VARCHAR(255) NULL,
  seq          INT          NOT NULL DEFAULT 0,
  recommend_yn VARCHAR(1)   NOT NULL DEFAULT 'N',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `common_code` (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  group_code VARCHAR(255) NOT NULL,
  code       VARCHAR(255) NOT NULL,
  code_name  VARCHAR(255) NOT NULL,
  seq        INT          NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config` (
  id         INT          NOT NULL AUTO_INCREMENT,
  group_code VARCHAR(255) NULL,
  code       VARCHAR(255) NULL,
  content    VARCHAR(255) NULL,
  order_seq  INT          NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================================
-- 샘플 데이터
-- =====================================================================

-- ---- 유저 3명 (다양한 프리미엄 상태) -------------------------------
-- 100 민지  : 프리미엄 ACTIVE, 여행/운동 덕후, 공개 활동 많음
-- 101 준호  : 프리미엄 NONE, 자기계발/커리어, 비공개 위주
-- 102 서연  : 프리미엄 EXPIRED(구독 만료), 요리/취미
INSERT INTO `user` (id, uid, email, account_type, name, bio, locale, alarm_yn, delete_yn, status, premium_status, last_login_at) VALUES
(100, 'test-uid-minji-100',  'minji@test.com',  'ANDROID', '민지', '세계 일주가 꿈✈️',        'ko_KR', 'Y', 'N', 'ACTIVE', 'ACTIVE',  '2026-07-22 10:00:00'),
(101, 'test-uid-junho-101',  'junho@test.com',  'IOS',     '준호', '갓생 사는 중',             'ko_KR', 'Y', 'N', 'ACTIVE', 'NONE',    '2026-07-21 20:30:00'),
(102, 'test-uid-seoyeon-102','seoyeon@test.com','ANDROID', '서연', '오늘 뭐 먹지🍳',           'ko_KR', 'N', 'N', 'ACTIVE', 'EXPIRED', '2026-07-20 08:15:00');

-- ---- 무료 티어 (유저별) --------------------------------------------
INSERT INTO `free_tier` (user_id, image_limit, together_limit, image_used, together_used) VALUES
(100, 100, 10, 12, 3),
(101, 30,  3,  5,  1),
(102, 30,  3,  8,  2);

-- ---- 구독 (민지=활성, 서연=만료) -----------------------------------
INSERT INTO `subscribe` (id, user_id, billing_cycle, status, start_at, expired_at, cancelled_at, subscribe_id) VALUES
(1, 100, 'MONTHLY', 'ACTIVE',  '2026-07-01 09:00:00', '2026-08-01 09:00:00', NULL,                  'gpa.test-token-minji-active'),
(2, 102, 'YEARLY',  'EXPIRED', '2025-07-15 09:00:00', '2026-07-15 09:00:00', '2026-06-30 12:00:00', 'gpa.test-token-seoyeon-expired');

-- ---- 카테고리 (유저별, default 1개씩) ------------------------------
INSERT INTO `category` (id, name, user_id, seq, deleted, default_yn) VALUES
(200, '여행',     100, 0, 'N', 'Y'),
(201, '운동',     100, 1, 'N', 'N'),
(210, '자기계발', 101, 0, 'N', 'Y'),
(211, '커리어',   101, 1, 'N', 'N'),
(220, '요리',     102, 0, 'N', 'Y'),
(221, '취미',     102, 1, 'N', 'N');

-- ---- 버킷리스트 (상태/공개범위/타입 다양) --------------------------
-- 민지(100)
INSERT INTO `bucket` (id, title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids) VALUES
(300, '제주도 한 달 살기',   '9월에 떠나기',        100, 200, 'ORIGINAL',  'PUBLIC',  'Y', 'PROGRESS', 'N', '2026-09-01', 1, 0, NULL,                  0, 12, '여행,제주,한달살기', NULL),
(301, '유럽 배낭여행',       '10개국 돌기',         100, 200, 'ORIGINAL',  'FOLLOWER','N', 'PROGRESS', 'N', '2026-12-20', 1, 0, NULL,                  1,  5, '여행,유럽',          NULL),
(302, '풀코스 마라톤 완주',  '3시간대 목표',        100, 201, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', '2026-05-10', 1, 1, '2026-05-10 11:30:00', 2,  0, '운동,마라톤',        NULL);

-- 준호(101)
INSERT INTO `bucket` (id, title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids) VALUES
(310, '매일 독서 30분',    '100일 챌린지',        101, 210, 'CHALLENGE', 'PUBLIC',  'Y', 'PROGRESS', 'N', '2026-10-31', 1, 100, NULL, 0, 3, '독서,습관',   NULL),
(311, '이직 성공하기',     '연봉 협상까지',       101, 211, 'ORIGINAL',  'PRIVATE', 'N', 'PROGRESS', 'N', NULL,         1, 0,   NULL, 1, 0, '커리어,이직', NULL),
(312, '영어 회화 마스터',  '서연이랑 스터디',     101, 210, 'TOGETHER',  'FOLLOWER','N', 'PROGRESS', 'N', '2026-12-31', 2, 0,   NULL, 2, 1, '영어,스터디', '102');

-- 서연(102)
INSERT INTO `bucket` (id, title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids) VALUES
(320, '집밥 100끼 도전',   '요리 실력 UP',        102, 220, 'CHALLENGE', 'PUBLIC',  'Y', 'PROGRESS', 'N', '2026-12-31', 1, 100, NULL,                  0,  8, '요리,집밥',   NULL),
(321, '기타 배우기',       '버스킹이 목표🎸',     102, 221, 'ORIGINAL',  'PUBLIC',  'N', 'COMPLETE', 'N', '2026-04-01', 1, 0,   '2026-04-01 19:00:00', 1, 15, '취미,기타',   NULL),
(322, '홈베이킹 클래스',   '주말 원데이클래스',   102, 220, 'ORIGINAL',  'PRIVATE', 'N', 'PROGRESS', 'N', NULL,                  1, 0, NULL,                  2,  0, '요리,베이킹', NULL),
(323, '사진전 열기',       '친구들이랑 같이',     102, 221, 'TOGETHER',  'FOLLOWER','N', 'PROGRESS', 'Y', '2027-03-01', 3, 0,   NULL,                  3,  2, '취미,사진',   '100,101');

-- ---- 좋아요 (공개 버킷 위주) ---------------------------------------
INSERT INTO `like_bucket` (user_id, bucket_id) VALUES
(101, 300), (102, 300),  -- 제주도
(100, 310),              -- 독서
(100, 320), (101, 320),  -- 집밥
(100, 321), (101, 321);  -- 기타

-- ---- 댓글 (공개 버킷) ----------------------------------------------
INSERT INTO `comment` (id, comment, mention_ids, user_id, bucket_id, is_blocked, is_hide) VALUES
(400, '와 저도 제주도 한 달 살기 하고싶어요!', NULL, 101, 300, 'N', 'N'),
(401, '숙소 정해지면 공유해줘요~',            NULL, 102, 300, 'N', 'N'),
(402, '@민지 독서 챌린지 같이해요',           '100', 100, 310, 'N', 'N'),
(403, '기타 완주 축하해요🎉',                 NULL, 100, 321, 'N', 'N'),
(404, '(숨김 처리된 댓글)',                   NULL, 101, 320, 'N', 'Y');

-- ---- 팔로우 관계 ---------------------------------------------------
-- 101->100, 102->100, 100->102 (민지가 인기, 민지<->서연 맞팔)
INSERT INTO `follow` (user_id, follow_user_id) VALUES
(101, 100),
(102, 100),
(100, 102);

-- ---- 알림 (타입별 다양하게. FOLLOW는 팔로워 프로필 이미지 포함) ----
INSERT INTO `alarm` (user_id, type, message, img_url, is_read) VALUES
(100, 'FOLLOW',   '준호님이 회원님을 팔로우하기 시작했습니다.',        'https://cdn.test.com/profile/junho.png', 0),
(100, 'FOLLOW',   '서연님이 회원님을 팔로우하기 시작했습니다.',        'https://cdn.test.com/profile/seoyeon.png', 1),
(100, 'LIKE',     '서연님이 회원님의 버킷리스트를 좋아합니다.',        NULL, 1),
(100, 'COMMENT',  '준호님이 회원님의 버킷리스트에 댓글을 남겼습니다.', NULL, 0),
(100, 'BADGE',    '여행 뱃지를 획득했습니다.',                        NULL, 0),
(101, 'COMMENT',  '민지님이 회원님의 버킷리스트에 댓글을 남겼습니다.', NULL, 0),
(102, 'TOGETHER', '민지님이 함께하는 버킷 "사진전 열기"을(를) 완성했습니다.', NULL, 0),
(102, 'NOTICE',   '[공지] 서비스 점검 안내드립니다.',                 NULL, 1),
(101, 'EVENT',    '[이벤트] 첫 버킷 완성 시 뱃지 2배!',               NULL, 0);

-- ---- 최근 검색어 --------------------------------------------------
INSERT INTO `recent_search` (query, user_id) VALUES
('제주도', 100),
('마라톤', 100),
('영어회화', 101);

-- ---- 신고 (댓글 1건 예시) -----------------------------------------
INSERT INTO `report` (reason, report_type, bucketlist_id, comment_id, report_user_id) VALUES
('스팸/광고성 댓글', 'COMMENT', 320, 404, 100);

-- ---- 뱃지 타입 / 뱃지 (기본 + 카테고리 일부) -----------------------
INSERT INTO `badge_type` (id, code, title, img_url1, img_url2, img_url3, img_url4) VALUES
(1,  '',        '기본',     '/images/academics_01.png', '/images/academics_01.png', '/images/academics_01.png', '/images/academics_01.png'),
(30, 'cooking', '요리',     '/images/cooking_01.png',   '/images/cooking_02.png',   '/images/cooking_03.png',   '/images/cooking_04.png'),
(47, 'travel',  '여행',     '/images/travel_01.png',    '/images/travel_02.png',    '/images/travel_03.png',    '/images/travel_04.png'),
(49, 'workout', '운동',     '/images/workout_01.png',   '/images/workout_02.png',   '/images/workout_03.png',   '/images/workout_04.png');

INSERT INTO `badge` (user_id, badge_type_id, achieve_count, select_yn, achieve_yn) VALUES
(100, 1,  0, 'Y', 'Y'),
(100, 47, 3, 'N', 'Y'),
(101, 1,  0, 'Y', 'Y'),
(102, 1,  0, 'Y', 'Y'),
(102, 30, 2, 'N', 'Y');

-- ---- 추천 키워드 (검색 테스트용) -----------------------------------
INSERT INTO `keyword` (type, name, seq, recommend_yn) VALUES
('KEYWORD',  '여행',   0, 'Y'),
('KEYWORD',  '운동',   1, 'Y'),
('KEYWORD',  '독서',   2, 'Y'),
('CATEGORY', '자기계발', 0, 'N'),
('SEARCH',   '제주도', 0, 'N');
