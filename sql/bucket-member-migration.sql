-- =====================================================================
-- 함께하기(TOGETHER) 참여자 테이블 신설 + 기존 데이터 백필 (운영/QA 마이그레이션)
-- 1) bucket_member 테이블 생성
-- 2) 기존 TOGETHER 버킷 백필:
--    - 소유자 row: 버킷의 category_id, 기존 공유 user_count/status/completed_date를 승계
--    - 친구 row: 각자의 기본(default) 카테고리, 진행도 0부터 시작
-- - idempotent: 테이블 IF NOT EXISTS + NOT EXISTS 조건으로 재실행 안전
-- - MySQL 8 필요 (JSON_TABLE)
-- 실행: mysql -h <host> -u <user> -p <db> < bucket-member-migration.sql
-- =====================================================================

CREATE TABLE IF NOT EXISTS `bucket_member` (
  `id`             BIGINT      NOT NULL AUTO_INCREMENT,
  `bucket_id`      BIGINT      NOT NULL,
  `user_id`        BIGINT      NOT NULL,
  `category_id`    BIGINT      NULL,
  `user_count`     INT         NOT NULL DEFAULT 0,
  `status`         VARCHAR(10) NOT NULL DEFAULT 'PROGRESS',
  `completed_date` DATETIME    NULL,
  `created_at`     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
  `updated_at`     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bucket_member` (`bucket_id`, `user_id`),
  KEY `idx_bucket_member_user_id` (`user_id`)
);

-- 소유자 백필 (기존 공유 진행도는 소유자에게 승계)
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date)
SELECT b.id, b.user_id, b.category_id, b.user_count, b.status, b.completed_date
FROM bucket b
WHERE b.type = 'TOGETHER'
  AND b.deleted = 'N'
  AND b.friend_user_ids IS NOT NULL AND TRIM(b.friend_user_ids) <> ''
  AND NOT EXISTS (SELECT 1 FROM bucket_member m WHERE m.bucket_id = b.id AND m.user_id = b.user_id);

-- 친구 백필 (각자 default 카테고리 매핑, 진행도 0)
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status)
SELECT b.id, jt.uid, c.category_id, 0, 'PROGRESS'
FROM bucket b
JOIN JSON_TABLE(
       CONCAT('[', TRIM(BOTH ',' FROM REPLACE(b.friend_user_ids, ' ', '')), ']'),
       '$[*]' COLUMNS (uid BIGINT PATH '$')
     ) jt
LEFT JOIN (SELECT user_id, MIN(id) AS category_id
           FROM category
           WHERE default_yn = 'Y' AND deleted = 'N'
           GROUP BY user_id) c ON c.user_id = jt.uid
WHERE b.type = 'TOGETHER'
  AND b.deleted = 'N'
  AND b.friend_user_ids IS NOT NULL AND TRIM(b.friend_user_ids) <> ''
  AND jt.uid <> b.user_id
  AND NOT EXISTS (SELECT 1 FROM bucket_member m WHERE m.bucket_id = b.id AND m.user_id = jt.uid);

-- 확인
SELECT m.bucket_id, m.user_id, m.category_id, m.user_count, m.status
FROM bucket_member m
ORDER BY m.bucket_id, m.id;
