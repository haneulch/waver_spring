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

CREATE TABLE IF NOT EXISTS mybury.mt_category (
  `id` varchar(255) NOT NULL,
  `created_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `is_default` char(1) DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqeb3efj9sv965flcarmqqhibj` (`name`,`user_id`),
  KEY `FKrhw1y4207hqcw7nl9518y8rt4` (`user_id`),
  CONSTRAINT `FKrhw1y4207hqcw7nl9518y8rt4` FOREIGN KEY (`user_id`) REFERENCES `mybury`.`mt_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS mybury.mt_bucketlist (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `d_date` datetime DEFAULT NULL,
  `goal_count` int DEFAULT '1',
  `img_url_1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `img_url_2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `img_url_3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `memo` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `open` bit(1) DEFAULT b'0',
  `pin` bit(1) DEFAULT b'0',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `title` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_count` int DEFAULT '0',
  `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `completed_dt` datetime DEFAULT NULL,
  `buck_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'O',
  `order_seq` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKeo64b2cjx685tx3vh9gr9q1rs` (`category_id`),
  KEY `FKpto3hlj91gwg11u1i4geljfx` (`user_id`),
  CONSTRAINT `FKeo64b2cjx685tx3vh9gr9q1rs` FOREIGN KEY (`category_id`) REFERENCES `mybury`.`mt_category` (`id`),
  CONSTRAINT `FKpto3hlj91gwg11u1i4geljfx` FOREIGN KEY (`user_id`) REFERENCES `mybury`.`mt_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 시드: init-data.sql의 waver 사용자(mybury999@gmail.com)와 이메일이 매칭되는 mybury 기존 회원.
-- 암호화 대상 컬럼(email/name/title/memo/img_url_*)은 레거시와 동일하게 HEX(AES_ENCRYPT(...)) 저장
INSERT INTO mybury.mt_user (id, account_type, email, name, user_seq)
VALUES ('local-mybury-user-001', 1,
        HEX(AES_ENCRYPT('mybury999@gmail.com', 'VN4A297LLXDHLN7G')),
        HEX(AES_ENCRYPT('mybury999', 'VN4A297LLXDHLN7G')),
        1)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO mybury.mt_category (id, name, priority, user_id, is_default)
VALUES ('local-mybury-cat-default', HEX(AES_ENCRYPT('없음', 'VN4A297LLXDHLN7G')), 0, 'local-mybury-user-001', 'Y'),
       ('local-mybury-cat-travel', HEX(AES_ENCRYPT('여행', 'VN4A297LLXDHLN7G')), 1, 'local-mybury-user-001', 'N')
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO mybury.mt_bucketlist (id, title, memo, img_url_1, d_date, goal_count, user_count,
                                  open, pin, category_id, user_id, completed_dt, order_seq)
VALUES ('local-mybury-bucket-001',
        HEX(AES_ENCRYPT('유럽 여행 가기', 'VN4A297LLXDHLN7G')),
        HEX(AES_ENCRYPT('파리, 로마 필수', 'VN4A297LLXDHLN7G')),
        HEX(AES_ENCRYPT('https://mybury.example.com/img/europe.jpg', 'VN4A297LLXDHLN7G')),
        '2027-01-01 00:00:00', 1, 0, b'1', b'1', 'local-mybury-cat-travel', 'local-mybury-user-001', NULL, 0),
       ('local-mybury-bucket-002',
        HEX(AES_ENCRYPT('책 10권 읽기', 'VN4A297LLXDHLN7G')),
        NULL, NULL, NULL, 10, 10, b'0', b'0', 'local-mybury-cat-default', 'local-mybury-user-001',
        '2025-12-31 09:00:00', 1),
       ('local-mybury-bucket-003',
        HEX(AES_ENCRYPT('카테고리 없는 버킷', 'VN4A297LLXDHLN7G')),
        NULL, NULL, NULL, 1, 0, b'1', b'0', NULL, 'local-mybury-user-001', NULL, 2)
ON DUPLICATE KEY UPDATE id = id;
