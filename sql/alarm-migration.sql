-- =====================================================================
-- alarm 테이블에 type, img_url, bucket_id 컬럼 추가 (운영/QA 마이그레이션)
-- - 데이터 파괴 없음(가법적, nullable)
-- - idempotent: 컬럼이 이미 있으면 건너뜀 → 재실행해도 안전
-- - 대상 스키마 = 접속 시 USE 한 DB (DATABASE())
-- 실행: mysql -h <host> -u <user> -p <db> < alarm-migration.sql
-- =====================================================================

DROP PROCEDURE IF EXISTS waver_add_alarm_cols;

DELIMITER $$
CREATE PROCEDURE waver_add_alarm_cols()
BEGIN
  IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                 WHERE table_schema = DATABASE()
                   AND table_name = 'alarm'
                   AND column_name = 'type') THEN
    ALTER TABLE `alarm` ADD COLUMN `type` VARCHAR(255) NULL;
  END IF;

  IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                 WHERE table_schema = DATABASE()
                   AND table_name = 'alarm'
                   AND column_name = 'img_url') THEN
    ALTER TABLE `alarm` ADD COLUMN `img_url` VARCHAR(255) NULL;
  END IF;

  IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                 WHERE table_schema = DATABASE()
                   AND table_name = 'alarm'
                   AND column_name = 'bucket_id') THEN
    ALTER TABLE `alarm` ADD COLUMN `bucket_id` BIGINT NULL;
  END IF;
END$$
DELIMITER ;

CALL waver_add_alarm_cols();
DROP PROCEDURE IF EXISTS waver_add_alarm_cols;

-- 확인
SELECT column_name, data_type, character_maximum_length, is_nullable
FROM information_schema.columns
WHERE table_schema = DATABASE() AND table_name = 'alarm'
ORDER BY ordinal_position;
