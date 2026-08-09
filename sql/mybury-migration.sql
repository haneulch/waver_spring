-- =====================================================================
-- mybury 이관 대응 (운영/QA 마이그레이션)
-- 1) user 테이블에 mybury_yn 컬럼 추가 (기존 회원은 N)
-- 2) migration_info 테이블 생성 (이동요청 API 요청 건, 스케줄러 처리 대상)
-- - idempotent: 컬럼/테이블이 이미 있으면 건너뜀 → 재실행해도 안전
-- - 대상 스키마 = 접속 시 USE 한 DB (DATABASE())
-- 실행: mysql -h <host> -u <user> -p <db> < mybury-migration.sql
-- =====================================================================

DROP PROCEDURE IF EXISTS waver_add_mybury_cols;

DELIMITER $$
CREATE PROCEDURE waver_add_mybury_cols()
BEGIN
  IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                 WHERE table_schema = DATABASE()
                   AND table_name = 'user'
                   AND column_name = 'mybury_yn') THEN
    ALTER TABLE `user` ADD COLUMN `mybury_yn` VARCHAR(1) NOT NULL DEFAULT 'N';
  END IF;
END$$
DELIMITER ;

CALL waver_add_mybury_cols();
DROP PROCEDURE IF EXISTS waver_add_mybury_cols;

CREATE TABLE IF NOT EXISTS `migration_info` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT,
  `user_id`    BIGINT       NOT NULL,
  `status`     VARCHAR(10)  NOT NULL,
  `created_at` TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_migration_info_user_id` (`user_id`)
);

-- 확인
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_schema = DATABASE() AND table_name IN ('user', 'migration_info')
ORDER BY table_name, ordinal_position;
