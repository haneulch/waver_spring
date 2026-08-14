-- 로컬 개발용 mybury 레거시 database 생성 (운영과 같은 서버 내 별도 database 구조 재현)
-- 적용: docker exec -i bucket-mysql mysql -uroot -p12345 < docker/mysql/mybury-init.sql
CREATE DATABASE IF NOT EXISTS mybury;

CREATE TABLE IF NOT EXISTS mybury.mt_user (
  `id` varchar(255) NOT NULL,
  `created_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `account_type` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT b'1',
  `img_url` varchar(255) DEFAULT NULL,
  `last_login_dt` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `alarm_yn` char(1) DEFAULT 'N',
  `bio` varchar(255) DEFAULT NULL,
  `user_seq` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 시드: init-data.sql의 waver 사용자(mybury999@gmail.com)와 이메일이 매칭되는 mybury 기존 회원.
-- email/name은 레거시와 동일하게 HEX(AES_ENCRYPT(...)) 암호화 저장
INSERT INTO mybury.mt_user (id, account_type, email, name, user_seq)
VALUES ('local-mybury-user-001', 1,
        HEX(AES_ENCRYPT('mybury999@gmail.com', 'VN4A297LLXDHLN7G')),
        HEX(AES_ENCRYPT('mybury999', 'VN4A297LLXDHLN7G')),
        1)
ON DUPLICATE KEY UPDATE id = id;
