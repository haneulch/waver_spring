-- =====================================================================
-- QA 테스트 데이터 (수기 작성)
-- 대상 유저: 30,32,33,34,35,36,37,38,40,41,42,43,44,45,47,48,49,50
-- 버킷 약 1,000건 + 함께하기 참여자/댓글/좋아요/팔로우/최근검색어/알림
-- 카테고리는 QA category 테이블 실측값 사용 (30→47, 32→49, ...)
-- 실행: mysql -h <host> -u <user> -p <db> < qa-test-data.sql
-- =====================================================================
SET NAMES utf8mb4;

-- ===== 팔로우 관계 (맞팔 포함 — FOLLOWER 공개 버킷 노출 테스트용) =====
INSERT INTO follow (user_id, follow_user_id) VALUES
(30,32),(32,30),(30,33),(33,30),(30,49),(49,30),
(32,49),(49,32),(32,35),(33,47),(47,33),(33,42),(42,33),
(34,40),(40,34),(34,45),(45,34),(35,48),(48,35),(35,50),(50,35),
(36,42),(42,36),(36,50),(37,30),(30,37),(37,49),
(38,42),(42,38),(38,47),(40,45),(45,40),(41,44),(44,41),(41,43),
(43,48),(48,43),(44,50),(50,44),(45,50),(47,50),(50,47),(48,50);

-- ===== 최근 검색어 =====
INSERT INTO recent_search (query, user_id) VALUES
('제주도',30),('유럽여행',30),('맛집투어',30),('캠핑',30),
('러닝',32),('바디프로필',32),('클라이밍',32),
('베스트셀러',33),('독서모임',33),('필사',33),
('아기 이유식',34),('유아 놀이',34),('가족여행',34),
('스팀 게임',35),('보드게임',35),
('주식',36),('부동산',36),('절약',36),('짠테크',36),
('홈베이킹',37),('비건 레시피',37),
('정보처리기사',38),('토익',38),('자격증 일정',38),
('강아지 훈련',40),('고양이 간식',40),
('메이크업',41),('피부관리',41),('명품',41),
('미라클모닝',42),('사이드프로젝트',42),
('전시회',43),('뮤지컬',43),('독립영화',43),
('소개팅',44),('모임 장소',44),
('연탄봉사',45),('유기견 봉사',45),
('중간고사',47),('공모전',47),('교환학생',47),
('원데이클래스',48),('도자기공방',48),
('식단표',49),('인터벌러닝',49),('바디프로필',49),
('버킷리스트',50),('새해목표',50),('챌린지',50);

-- =====================================================================
-- user 30 : 여행덕후 (default cat 47)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('제주도 한달살기', '가는 달 정하기부터', 30, 47, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2026-11-01', 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-07-21 10:12:00', '2026-07-21 10:12:00'),
('유럽 배낭여행 3개국 찍기', '파리-로마-바르셀로나', 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', '2027-01-15', 1, 3, NULL, 0, 0, 'travel,culture', NULL, 'N', '2026-06-02 21:40:00', '2026-06-02 21:40:00'),
('국내 5대 야시장 가보기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'travel,foodie', NULL, 'N', '2026-05-18 12:00:00', '2026-05-18 12:00:00'),
('부산 해운대 일출 보기', '새벽 기차 타고', 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'Y', NULL, 1, 1, '2026-01-01 07:40:00', 0, 0, 'travel', NULL, 'N', '2025-12-28 22:10:00', '2025-12-28 22:10:00'),
('전국 케이블카 다 타보기', NULL, 30, 47, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 3, 8, NULL, 0, 0, 'travel,hobby', NULL, 'N', '2026-03-09 15:33:00', '2026-03-09 15:33:00'),
('비행기 일등석 타보기', '마일리지 모으는 중', 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,flexing', NULL, 'N', '2026-02-14 09:00:00', '2026-02-14 09:00:00'),
('섬 여행 10곳 도장깨기', '울릉도부터', 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'Y', NULL, 4, 10, NULL, 0, 0, 'travel', NULL, 'N', '2026-04-25 18:20:00', '2026-04-25 18:20:00'),
('기차 타고 강릉 당일치기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-05 20:00:00', 0, 0, 'travel', NULL, 'N', '2026-05-01 08:15:00', '2026-05-01 08:15:00'),
('오사카 먹방 여행', '타코야키 원조집', 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'Y', NULL, 1, 1, '2026-03-22 19:30:00', 0, 0, 'travel,foodie', NULL, 'N', '2026-02-20 11:11:00', '2026-02-20 11:11:00'),
('전국 국밥 맛집 20곳', '순대국밥 최고', 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 7, 20, NULL, 0, 0, 'foodie', NULL, 'N', '2025-11-11 12:30:00', '2025-11-11 12:30:00'),
('캠핑카 렌트해서 동해안 일주', NULL, 30, 47, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-09-20', 0, 1, NULL, 0, 0, 'travel,family', NULL, 'N', '2026-07-30 22:45:00', '2026-07-30 22:45:00'),
('여행 사진으로 포토북 만들기', '연말에 정리', 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'hobby,travel', NULL, 'N', '2026-01-05 19:00:00', '2026-01-05 19:00:00'),
('스카이다이빙 해보기', '무섭지만 도전', 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,flexing', NULL, 'N', '2026-06-17 14:05:00', '2026-06-17 14:05:00'),
('남미 우유니 사막 가기', '버킷리스트 1순위', 30, 47, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', NULL, 0, 1, NULL, 0, 0, 'travel,flexing', NULL, 'N', '2025-10-02 23:59:00', '2025-10-02 23:59:00'),
('서울 5대 고궁 야간개장 가기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'culture,travel', NULL, 'N', '2026-04-01 17:20:00', '2026-04-01 17:20:00'),
('교토 사찰 단풍 투어', '가을 단풍 시즌', 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-11-20', 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-08-01 10:30:00', '2026-08-01 10:30:00'),
('숙소 없이 무계획 여행 떠나기', '즉흥 여행 로망', 30, 47, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-19 21:00:00', 0, 0, 'travel', NULL, 'N', '2025-09-30 13:00:00', '2025-09-30 13:00:00'),
('환전 없이 해외여행 가보기', '카드만 들고', 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,finance', NULL, 'N', '2026-07-07 07:07:00', '2026-07-07 07:07:00'),
('전주 한옥마을에서 한복 입기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'Y', NULL, 1, 1, '2026-06-06 15:00:00', 0, 0, 'travel,culture', NULL, 'N', '2026-05-25 09:40:00', '2026-05-25 09:40:00'),
('공항에서 즉석 티켓 끊고 떠나기', '어디든 좋아', 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-08-05 20:30:00', '2026-08-05 20:30:00');

-- user 30 추가 (진행중/완료 섞기)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('야간 비행기에서 노을 사진 찍기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-11 18:44:00', 0, 0, 'travel,hobby', NULL, 'N', '2026-01-19 10:00:00', '2026-01-19 10:00:00'),
('로컬 시장에서 장보고 요리하기', '여행지에서만', 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 0, 'travel,cooking', NULL, 'N', '2026-03-28 16:50:00', '2026-03-28 16:50:00'),
('침낭 하나로 비박 도전', NULL, 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-06-30 23:20:00', '2026-06-30 23:20:00'),
('올레길 완주', '1코스부터 차근차근', 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'Y', NULL, 6, 26, NULL, 0, 0, 'travel,workout', NULL, 'N', '2025-09-15 07:30:00', '2025-09-15 07:30:00'),
('해외에서 한 달 원격근무 해보기', '발리 노마드', 30, 47, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,career', NULL, 'N', '2026-05-11 13:13:00', '2026-05-11 13:13:00'),
('전국 휴게소 맛집 지도 만들기', '가평휴게소 잣라면', 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 5, 15, NULL, 0, 0, 'foodie,travel', NULL, 'N', '2026-02-02 12:02:00', '2026-02-02 12:02:00'),
('밤 기차 침대칸 타보기', NULL, 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-07-15 21:00:00', '2026-07-15 21:00:00'),
('현지인 집에서 홈스테이', '언어 걱정은 나중에', 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,socializing', NULL, 'N', '2026-04-10 10:10:00', '2026-04-10 10:10:00'),
('여행 유튜브 채널 개설', '편집 배워야 함', 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-10-01', 0, 1, NULL, 0, 0, 'hobby,career', NULL, 'N', '2026-06-20 22:00:00', '2026-06-20 22:00:00'),
('당일치기 등산 10회', '북한산부터', 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 10, 10, '2026-07-27 16:30:00', 0, 0, 'workout,travel', NULL, 'N', '2026-01-03 06:00:00', '2026-01-03 06:00:00');

-- user 30 함께하기 (친구 32, 49와 러닝 여행)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('마라톤 대회 같이 완주하기', '풀코스는 무리, 하프부터', 30, 47, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-25', 1, 3, NULL, 0, 2, 'workout,socializing', '32,49', 'N', '2026-06-01 09:00:00', '2026-06-01 09:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 30, 47, 1, 'PROGRESS', NULL),
(@b, 32, 49, 3, 'COMPLETE', '2026-07-19 11:20:00'),
(@b, 49, 96, 2, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(32, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=30), '님이 함께하는 버킷 "마라톤 대회 같이 완주하기"에 회원님을 초대했습니다.'), @b, 1),
(49, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=30), '님이 함께하는 버킷 "마라톤 대회 같이 완주하기"에 회원님을 초대했습니다.'), @b, 0),
(30, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=32), '님이 함께하는 버킷 "마라톤 대회 같이 완주하기"을(를) 완성했습니다.'), @b, 0),
(49, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=32), '님이 함께하는 버킷 "마라톤 대회 같이 완주하기"을(를) 완성했습니다.'), @b, 0);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (33, @b), (49, @b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('하프도 대단한 거예요 화이팅!', 33, @b, 'N', 'N'),
('저도 끼워주세요 ㅠㅠ', 37, @b, 'N', 'N');

-- user 30 함께하기 (친구 37과 맛집 투어)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('전국 빵지순례 10곳', '성심당은 필수', 30, 47, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'Y', NULL, 4, 10, NULL, 0, 1, 'foodie,travel', '37', 'N', '2026-03-03 11:30:00', '2026-03-03 11:30:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 30, 47, 4, 'PROGRESS', NULL),
(@b, 37, 54, 6, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(37, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=30), '님이 함께하는 버킷 "전국 빵지순례 10곳"에 회원님을 초대했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (49, @b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('빵순이로서 응원합니다', 41, @b, 'N', 'N'),
('성심당 튀김소보로 꼭 드세요', 43, @b, 'N', 'N'),
('광고성 댓글입니다', 44, @b, 'Y', 'N');

-- user 30 인기 버킷 (좋아요 몰아주기 — 인기 목록 테스트)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('세계 7대 불가사의 직접 보기', '피라미드부터 마추픽추까지', 30, 47, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', NULL, 2, 7, NULL, 0, 6, 'travel,flexing', NULL, 'N', '2026-07-25 12:00:00', '2026-07-25 12:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO like_bucket (user_id, bucket_id) VALUES (32,@b),(33,@b),(35,@b),(41,@b),(43,@b),(50,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('스케일이 다르네요 ㄷㄷ', 35, @b, 'N', 'N'),
('마추픽추 저도 가고 싶어요', 42, @b, 'N', 'N'),
('사진 꼭 공유해주세요!', 48, @b, 'N', 'N'),
('혼자 보기 아까운 버킷', 50, @b, 'N', 'N');

-- =====================================================================
-- user 32 : 운동러 (default cat 49)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('바디프로필 찍기', '3개월 벌크업부터', 32, 49, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2026-11-30', 0, 1, NULL, 0, 0, 'workout,diet', NULL, 'N', '2026-07-01 06:30:00', '2026-07-01 06:30:00'),
('한강 10km 러닝 완주', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-13 08:20:00', 0, 0, 'workout', NULL, 'N', '2026-03-15 07:00:00', '2026-03-15 07:00:00'),
('풀업 20개 연속', '현재 8개', 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 8, 20, NULL, 0, 0, 'workout', NULL, 'N', '2026-05-20 19:00:00', '2026-05-20 19:00:00'),
('클라이밍 V5 완등', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', NULL, 0, 1, NULL, 0, 0, 'workout,hobby', NULL, 'N', '2026-06-11 20:30:00', '2026-06-11 20:30:00'),
('새벽 6시 기상 30일', '미라클모닝 도전', 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 17, 30, NULL, 0, 0, 'selfimprovement,workout', NULL, 'N', '2026-07-10 06:00:00', '2026-07-10 06:00:00'),
('스쿼트 100kg 치기', '현재 80', 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-02-08 18:40:00', '2026-02-08 18:40:00'),
('수영 자유형 마스터', '주 2회 강습', 32, 49, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-01-20 21:00:00', '2026-01-20 21:00:00'),
('철인3종 스프린트 코스 완주', '수영이 문제', 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2027-05-01', 0, 1, NULL, 0, 0, 'workout,grinding', NULL, 'N', '2026-06-25 12:00:00', '2026-06-25 12:00:00'),
('헬스장 100일 연속 출석', NULL, 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 62, 100, NULL, 0, 0, 'workout,grinding', NULL, 'N', '2026-05-02 06:10:00', '2026-05-02 06:10:00'),
('단백질 식단 4주 유지', '닭가슴살 그만 먹고 싶다', 32, 49, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 4, 4, '2026-03-30 09:00:00', 0, 0, 'diet', NULL, 'N', '2026-03-01 08:00:00', '2026-03-01 08:00:00'),
('등산으로 지리산 종주', '1박 2일 코스', 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', '2026-10-10', 0, 1, NULL, 0, 0, 'workout,travel', NULL, 'N', '2026-07-19 22:00:00', '2026-07-19 22:00:00'),
('복싱 스파링 데뷔', NULL, 32, 49, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-04-04 19:30:00', '2026-04-04 19:30:00'),
('인바디 체지방률 15% 만들기', '현재 21%', 32, 49, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-01', 0, 1, NULL, 0, 0, 'diet,workout', NULL, 'N', '2026-06-15 10:00:00', '2026-06-15 10:00:00'),
('요가 자격증 따기', '취미가 스펙으로', 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification,workout', NULL, 'N', '2025-12-01 09:30:00', '2025-12-01 09:30:00'),
('자전거로 출퇴근 한 달', '왕복 18km', 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 20, 20, '2025-10-31 19:00:00', 0, 0, 'workout', NULL, 'N', '2025-10-01 07:30:00', '2025-10-01 07:30:00'),
('겨울 스키 시즌권 뽕뽑기', '시즌 20회 목표', 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2027-02-28', 3, 20, NULL, 0, 0, 'hobby,workout', NULL, 'N', '2025-11-25 21:30:00', '2025-11-25 21:30:00'),
('플랭크 5분 버티기', '현재 2분 30초', 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-08-02 07:45:00', '2026-08-02 07:45:00'),
('운동 유튜버 루틴 4주 따라하기', NULL, 32, 49, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 9, 28, NULL, 0, 0, 'workout,selfimprovement', NULL, 'N', '2026-07-14 06:20:00', '2026-07-14 06:20:00'),
('격투기 직관 가기', 'UFC 아니어도 OK', 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-23 22:30:00', 0, 0, 'culture,hobby', NULL, 'N', '2026-01-30 12:00:00', '2026-01-30 12:00:00'),
('스트레칭 매일 10분 100일', NULL, 32, 49, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 41, 100, NULL, 0, 0, 'workout,selfimprovement', NULL, 'N', '2026-06-28 23:00:00', '2026-06-28 23:00:00');

-- user 32 함께하기 (49와 바디프로필, 30과 등산)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('같이 바디프로필 찍기', '스튜디오 예약은 내가', 32, 49, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-20', 2, 5, NULL, 0, 3, 'workout,diet', '49', 'N', '2026-07-05 08:00:00', '2026-07-05 08:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 32, 49, 2, 'PROGRESS', NULL),
(@b, 49, 96, 4, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(49, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=32), '님이 함께하는 버킷 "같이 바디프로필 찍기"에 회원님을 초대했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (30,@b),(35,@b),(41,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('둘 다 독하다 진짜 ㅋㅋ', 30, @b, 'N', 'N'),
('결과물 기대할게요', 41, @b, 'N', 'N');

INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('한라산 백록담 같이 찍기', '겨울 설경 버전', 32, 49, 'TOGETHER', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2027-01-15', 0, 1, NULL, 0, 0, 'workout,travel', '30', 'N', '2026-08-03 21:10:00', '2026-08-03 21:10:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 32, 49, 0, 'PROGRESS', NULL),
(@b, 30, 47, 0, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(30, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=32), '님이 함께하는 버킷 "한라산 백록담 같이 찍기"에 회원님을 초대했습니다.'), @b, 0);

-- =====================================================================
-- user 33 : 독서가 (default cat 50)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('올해 책 50권 읽기', '월 4권 페이스', 33, 50, 'CHALLENGE', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2026-12-31', 31, 50, NULL, 0, 0, 'reading', NULL, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00'),
('고전문학 10권 완독', '죄와 벌부터', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 3, 10, NULL, 0, 0, 'reading,culture', NULL, 'N', '2026-02-14 20:00:00', '2026-02-14 20:00:00'),
('독서모임 만들어서 운영하기', '멤버 5명 모집', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-10 19:30:00', 0, 0, 'reading,socializing', NULL, 'N', '2026-02-01 10:00:00', '2026-02-01 10:00:00'),
('서평 블로그 100개 포스팅', NULL, 33, 50, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 44, 100, NULL, 0, 0, 'reading,hobby', NULL, 'N', '2025-09-01 22:00:00', '2025-09-01 22:00:00'),
('밤새워서 소설 한 권 다 읽기', '주말에 도전', 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-17 04:50:00', 0, 0, 'reading', NULL, 'N', '2026-05-10 23:00:00', '2026-05-10 23:00:00'),
('작가 북토크 직접 가보기', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'Y', NULL, 1, 1, '2026-06-21 17:00:00', 0, 0, 'reading,culture', NULL, 'N', '2026-06-01 12:30:00', '2026-06-01 12:30:00'),
('필사 노트 한 권 채우기', '하루 한 문장', 33, 50, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading,selfimprovement', NULL, 'N', '2026-07-01 21:30:00', '2026-07-01 21:30:00'),
('동네 책방 20곳 방문', '독립서점 투어', 33, 50, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 12, 20, NULL, 0, 0, 'reading,travel', NULL, 'N', '2025-11-20 14:00:00', '2025-11-20 14:00:00'),
('원서 한 권 사전 없이 읽기', '해리포터 1권', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading,academics', NULL, 'N', '2026-04-20 20:20:00', '2026-04-20 20:20:00'),
('내 이름으로 된 책 출간하기', '에세이 초고 쓰는 중', 33, 50, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2027-06-30', 0, 1, NULL, 0, 0, 'career,reading', NULL, 'N', '2026-01-15 23:40:00', '2026-01-15 23:40:00'),
('오디오북으로 출퇴근 독서 30일', NULL, 33, 50, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 30, 30, '2026-04-30 18:30:00', 0, 0, 'reading', NULL, 'N', '2026-04-01 08:10:00', '2026-04-01 08:10:00'),
('도서관에서 하루 종일 있어보기', '아침부터 마감까지', 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading', NULL, 'N', '2026-06-08 11:00:00', '2026-06-08 11:00:00'),
('시집 10권 읽고 필사하기', NULL, 33, 50, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 4, 10, NULL, 0, 0, 'reading,culture', NULL, 'N', '2026-03-25 21:15:00', '2026-03-25 21:15:00'),
('책 정리하고 중고서점에 팔기', '미니멀 라이프 시작', 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-14 16:00:00', 0, 0, 'hobby', NULL, 'N', '2025-12-01 10:00:00', '2025-12-01 10:00:00'),
('독서 기록 앱 1년 개근', NULL, 33, 50, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 220, 365, NULL, 0, 0, 'reading,grinding', NULL, 'N', '2026-01-01 00:10:00', '2026-01-01 00:10:00'),
('북유럽 문학 5권 읽기', '요 네스뵈 말고 순문학', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 1, 5, NULL, 0, 0, 'reading', NULL, 'N', '2026-07-22 22:40:00', '2026-07-22 22:40:00'),
('만화책 전권 정주행', '슬램덩크 31권', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 31, 31, '2026-02-28 23:59:00', 0, 0, 'hobby,reading', NULL, 'N', '2026-02-10 19:00:00', '2026-02-10 19:00:00'),
('서점에서 표지만 보고 책 사기', '직감 테스트', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-05 15:20:00', 0, 0, 'reading,hobby', NULL, 'N', '2026-07-01 13:00:00', '2026-07-01 13:00:00'),
('글쓰기 강좌 수료하기', '브런치 작가 신청까지', 33, 50, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-09-30', 0, 1, NULL, 0, 0, 'career,selfimprovement', NULL, 'N', '2026-06-10 20:00:00', '2026-06-10 20:00:00'),
('책 읽어주는 봉사 해보기', '도서관 낭독 봉사', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering,reading', NULL, 'N', '2026-05-28 09:45:00', '2026-05-28 09:45:00');

-- user 33 함께하기 (47과 스터디)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('주 1회 독서 스터디 12주', '벌금제 운영', 33, 50, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-31', 7, 12, NULL, 0, 1, 'reading,study', '47,42', 'N', '2026-05-15 19:00:00', '2026-05-15 19:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 33, 50, 7, 'PROGRESS', NULL),
(@b, 47, 89, 5, 'PROGRESS', NULL),
(@b, 42, 81, 12, 'COMPLETE', '2026-08-01 20:30:00');
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(47, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=33), '님이 함께하는 버킷 "주 1회 독서 스터디 12주"에 회원님을 초대했습니다.'), @b, 1),
(42, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=33), '님이 함께하는 버킷 "주 1회 독서 스터디 12주"에 회원님을 초대했습니다.'), @b, 1),
(33, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=42), '님이 함께하는 버킷 "주 1회 독서 스터디 12주"을(를) 완성했습니다.'), @b, 0),
(47, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=42), '님이 함께하는 버킷 "주 1회 독서 스터디 12주"을(를) 완성했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (30,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('벌금 얼마나 모였나요 ㅋㅋ', 30, @b, 'N', 'N'),
('다음 기수 모집하면 알려주세요', 48, @b, 'N', 'N'),
('(작성자가 숨긴 댓글)', 44, @b, 'N', 'Y');

-- =====================================================================
-- user 34 : 육아맘 (default cat 51)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('아이랑 첫 캠핑 가기', '텐트부터 사야 함', 34, 51, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2026-09-15', 0, 1, NULL, 0, 0, 'parenting,family', NULL, 'N', '2026-07-20 21:00:00', '2026-07-20 21:00:00'),
('이유식 30가지 직접 만들기', NULL, 34, 51, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 18, 30, NULL, 0, 0, 'parenting,cooking', NULL, 'N', '2026-04-01 10:00:00', '2026-04-01 10:00:00'),
('아이 성장앨범 만들기', '돌 전까지', 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-01', 0, 1, NULL, 0, 0, 'parenting,hobby', NULL, 'N', '2026-02-14 23:30:00', '2026-02-14 23:30:00'),
('가족사진 스튜디오 촬영', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-08 14:00:00', 0, 0, 'family', NULL, 'N', '2026-04-20 09:30:00', '2026-04-20 09:30:00'),
('아이랑 도서관 매주 가기 12주', '주말 루틴 만들기', 34, 51, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 8, 12, NULL, 0, 0, 'parenting,reading', NULL, 'N', '2026-06-07 10:30:00', '2026-06-07 10:30:00'),
('육아일기 100일 쓰기', NULL, 34, 51, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 55, 100, NULL, 0, 0, 'parenting', NULL, 'N', '2026-05-15 22:50:00', '2026-05-15 22:50:00'),
('혼자만의 반차 데이트', '미용실+카페+낮잠', 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-27 17:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2026-06-20 08:00:00', '2026-06-20 08:00:00'),
('아이 첫 여권 만들어서 해외여행', '오키나와 후보', 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', '2027-03-01', 0, 1, NULL, 0, 0, 'family,travel', NULL, 'N', '2026-07-01 21:20:00', '2026-07-01 21:20:00'),
('부모님 모시고 온천 여행', '효도 버킷', 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-10', 0, 1, NULL, 0, 0, 'family,travel', NULL, 'N', '2026-06-15 20:10:00', '2026-06-15 20:10:00'),
('아이 손잡고 벚꽃 구경', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-05 15:30:00', 0, 0, 'family', NULL, 'N', '2026-03-28 11:00:00', '2026-03-28 11:00:00'),
('유아식 자격증 공부하기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification,parenting', NULL, 'N', '2026-03-05 13:40:00', '2026-03-05 13:40:00'),
('중고 육아용품 정리해서 나눔하기', '5가족한테 나눔 목표', 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'volunteering,parenting', NULL, 'N', '2026-05-30 16:00:00', '2026-05-30 16:00:00'),
('산후 요가 8주 코스 완주', NULL, 34, 51, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 8, 8, '2026-03-15 11:00:00', 0, 0, 'workout,parenting', NULL, 'N', '2026-01-20 10:00:00', '2026-01-20 10:00:00'),
('아이 어린이집 적응 응원 일기', '한 달만 버티자', 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-01 18:00:00', 0, 0, 'parenting', NULL, 'N', '2026-03-02 08:30:00', '2026-03-02 08:30:00'),
('남편이랑 둘이 영화관 가기', '아기는 할머니 찬스', 34, 51, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'dating,family', NULL, 'N', '2026-08-04 22:15:00', '2026-08-04 22:15:00'),
-- 34 시리즈: 월간 가족나들이 기록
('1월 가족나들이 - 눈썰매장', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-25 17:00:00', 0, 0, 'family', NULL, 'N', '2026-01-10 09:00:00', '2026-01-10 09:00:00'),
('2월 가족나들이 - 아쿠아리움', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-22 16:30:00', 0, 0, 'family', NULL, 'N', '2026-02-05 09:00:00', '2026-02-05 09:00:00'),
('3월 가족나들이 - 동물원', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-21 15:00:00', 0, 0, 'family', NULL, 'N', '2026-03-03 09:00:00', '2026-03-03 09:00:00'),
('4월 가족나들이 - 튤립축제', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-19 14:00:00', 0, 0, 'family,travel', NULL, 'N', '2026-04-02 09:00:00', '2026-04-02 09:00:00'),
('5월 가족나들이 - 어린이대공원', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-05 13:00:00', 0, 0, 'family', NULL, 'N', '2026-05-01 09:00:00', '2026-05-01 09:00:00'),
('6월 가족나들이 - 물놀이장', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-28 16:00:00', 0, 0, 'family', NULL, 'N', '2026-06-10 09:00:00', '2026-06-10 09:00:00'),
('7월 가족나들이 - 계곡 피서', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-26 15:30:00', 0, 0, 'family,travel', NULL, 'N', '2026-07-08 09:00:00', '2026-07-08 09:00:00'),
('8월 가족나들이 - 키즈카페 신상 탐방', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-30', 0, 1, NULL, 0, 0, 'family,parenting', NULL, 'N', '2026-08-02 09:00:00', '2026-08-02 09:00:00'),
('9월 가족나들이 - 밤 줍기 체험', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-27', 0, 1, NULL, 0, 0, 'family', NULL, 'N', '2026-08-05 09:00:00', '2026-08-05 09:00:00'),
('10월 가족나들이 - 단풍 구경', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-25', 0, 1, NULL, 0, 0, 'family,travel', NULL, 'N', '2026-08-05 09:05:00', '2026-08-05 09:05:00');

-- user 34 함께하기 (40, 45와 육아 품앗이)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('아이들 데리고 월 1회 모임 6개월', '품앗이 육아', 34, 51, 'TOGETHER', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-31', 3, 6, NULL, 0, 0, 'parenting,socializing', '40,45', 'N', '2026-06-01 14:00:00', '2026-06-01 14:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 34, 51, 3, 'PROGRESS', NULL),
(@b, 40, 57, 3, 'PROGRESS', NULL),
(@b, 45, 85, 2, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(40, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=34), '님이 함께하는 버킷 "아이들 데리고 월 1회 모임 6개월"에 회원님을 초대했습니다.'), @b, 1),
(45, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=34), '님이 함께하는 버킷 "아이들 데리고 월 1회 모임 6개월"에 회원님을 초대했습니다.'), @b, 0);

-- =====================================================================
-- user 35 : 게이머 (default cat 52)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('스팀 찜목록 다 클리어하기', '백로그 23개', 35, 52, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 9, 23, NULL, 0, 0, 'gaming', NULL, 'N', '2026-01-11 01:30:00', '2026-01-11 01:30:00'),
('철권 랭크 텍갓 찍기', '현재 후지산', 35, 52, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,grinding', NULL, 'N', '2026-03-19 22:00:00', '2026-03-19 22:00:00'),
('게임 대회 오프라인 직관', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-12 21:00:00', 0, 0, 'gaming,culture', NULL, 'N', '2026-03-25 18:00:00', '2026-03-25 18:00:00'),
('보드게임 카페 정모 열기', '인생게임 밤샘', 35, 52, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,socializing', NULL, 'N', '2026-06-02 20:30:00', '2026-06-02 20:30:00'),
('레트로 게임기 수집 5종', '슈패미 구함', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'gaming,hobby,flexing', NULL, 'N', '2025-10-30 23:00:00', '2025-10-30 23:00:00'),
('밤새 게임하고 해돋이 보기', '이게 낭만이지', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-01 07:30:00', 0, 0, 'gaming,hobby', NULL, 'N', '2025-12-30 20:00:00', '2025-12-30 20:00:00'),
('인디게임 직접 만들어보기', '유니티 공부 중', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', '2027-01-31', 0, 1, NULL, 0, 0, 'gaming,career', NULL, 'N', '2026-05-06 21:40:00', '2026-05-06 21:40:00'),
('게임 방송 100시간 스트리밍', NULL, 35, 52, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 37, 100, NULL, 0, 0, 'gaming,hobby', NULL, 'N', '2026-02-20 19:00:00', '2026-02-20 19:00:00'),
('피시방에서 하루 12시간', '학생 때 로망', 35, 52, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-13 23:50:00', 0, 0, 'gaming', NULL, 'N', '2026-06-10 15:00:00', '2026-06-10 15:00:00'),
('젤다 100% 클리어', '코록 900개...', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,grinding', NULL, 'N', '2026-04-08 00:30:00', '2026-04-08 00:30:00'),
('게임 OST 콘서트 가기', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,culture', NULL, 'N', '2026-07-17 12:20:00', '2026-07-17 12:20:00'),
('체스 온라인 레이팅 1500', '현재 1180', 35, 52, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,study', NULL, 'N', '2026-05-22 22:10:00', '2026-05-22 22:10:00'),
('닌텐도 스위치2 발매일에 사기', '오픈런 각오', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-07 10:00:00', 0, 0, 'gaming,flexing', NULL, 'N', '2026-02-15 09:00:00', '2026-02-15 09:00:00'),
('모바일게임 무과금 만렙', '의지의 시험', 35, 52, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,grinding', NULL, 'N', '2026-06-30 08:00:00', '2026-06-30 08:00:00'),
('방탈출 카페 20방 탈출', '현재 성공률 60%', 35, 52, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 11, 20, NULL, 0, 0, 'gaming,socializing', NULL, 'N', '2025-12-05 19:30:00', '2025-12-05 19:30:00');

-- user 35 함께하기 (48, 50과 방탈출)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('공포 테마 방탈출 5개 도전', '무서운 건 네가 앞장', 35, 52, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 2, 'gaming,socializing', '48,50', 'N', '2026-07-11 20:00:00', '2026-07-11 20:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 35, 52, 2, 'PROGRESS', NULL),
(@b, 48, 94, 2, 'PROGRESS', NULL),
(@b, 50, 97, 1, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(48, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=35), '님이 함께하는 버킷 "공포 테마 방탈출 5개 도전"에 회원님을 초대했습니다.'), @b, 1),
(50, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=35), '님이 함께하는 버킷 "공포 테마 방탈출 5개 도전"에 회원님을 초대했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (32,@b),(44,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('공포는 못 참지 ㅋㅋㅋ', 44, @b, 'N', 'N'),
('저 지난주에 그 테마 성공함 꿀팁 있음', 43, @b, 'N', 'N');

-- =====================================================================
-- user 36 : 재테크 (default cat 53)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('1년에 1000만원 모으기', '월 84만원 적금', 36, 53, 'ORIGINAL', 'PRIVATE', 'Y', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'finance,grinding', NULL, 'N', '2026-01-02 07:00:00', '2026-01-02 07:00:00'),
('가계부 365일 쓰기', NULL, 36, 53, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-12-31', 221, 365, NULL, 0, 0, 'finance,grinding', NULL, 'N', '2026-01-01 08:00:00', '2026-01-01 08:00:00'),
('주식 책 5권 읽고 계좌 개설', '공부 먼저', 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 5, 5, '2026-02-28 21:00:00', 0, 0, 'finance,reading', NULL, 'N', '2026-01-15 20:00:00', '2026-01-15 20:00:00'),
('배당주 포트폴리오 만들기', '월 배당 5만원 목표', 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2026-03-10 22:30:00', '2026-03-10 22:30:00'),
('한 달 무지출 챌린지 10일', '주말이 고비', 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 10, 10, '2026-04-30 23:00:00', 0, 0, 'finance,grinding', NULL, 'N', '2026-04-01 07:30:00', '2026-04-01 07:30:00'),
('청약통장 1순위 만들기', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2025-09-20 12:00:00', '2025-09-20 12:00:00'),
('경제 뉴스레터 90일 구독하고 정리', NULL, 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 66, 90, NULL, 0, 0, 'finance,study', NULL, 'N', '2026-06-01 07:15:00', '2026-06-01 07:15:00'),
('점심 도시락 싸기 20일', '한 달 식비 반토막 내기', 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 13, 20, NULL, 0, 0, 'finance,cooking', NULL, 'N', '2026-07-01 07:00:00', '2026-07-01 07:00:00'),
('연말정산 공부해서 환급 최대로', '작년엔 뱉었다...', 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2027-01-31', 0, 1, NULL, 0, 0, 'finance,study', NULL, 'N', '2026-08-01 19:00:00', '2026-08-01 19:00:00'),
('부동산 임장 10곳 다니기', '발품이 답', 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 4, 10, NULL, 0, 0, 'finance,travel', NULL, 'N', '2026-05-10 10:00:00', '2026-05-10 10:00:00'),
('중고거래로 안 쓰는 물건 30개 팔기', NULL, 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 19, 30, NULL, 0, 0, 'finance,hobby', NULL, 'N', '2026-02-11 13:20:00', '2026-02-11 13:20:00'),
('비상금 500만원 만들기', '월급의 10%씩', 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2026-04-25 08:45:00', '2026-04-25 08:45:00'),
('커피값 아껴서 ETF 사기 3개월', '하루 5천원의 기적', 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-06-30 09:00:00', 0, 0, 'finance,grinding', NULL, 'N', '2026-04-01 08:00:00', '2026-04-01 08:00:00'),
('재테크 스터디 참여하기', NULL, 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,socializing', NULL, 'N', '2026-07-28 21:30:00', '2026-07-28 21:30:00'),
('신용점수 950점 만들기', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2026-03-17 18:00:00', '2026-03-17 18:00:00');

-- user 36 함께하기 (42와 무지출 챌린지)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('둘이서 무지출 챌린지 14일', '실패하면 상대에게 치킨', 36, 53, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-31', 6, 14, NULL, 0, 4, 'finance,grinding', '42', 'N', '2026-08-01 09:00:00', '2026-08-01 09:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 36, 53, 6, 'PROGRESS', NULL),
(@b, 42, 81, 8, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(42, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=36), '님이 함께하는 버킷 "둘이서 무지출 챌린지 14일"에 회원님을 초대했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (30,@b),(33,@b),(49,@b),(50,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('치킨 내기라니 이건 못 참지', 50, @b, 'N', 'N'),
('저도 다음 기수 참여할래요', 49, @b, 'N', 'N'),
('무지출 3일차부터 현타옴 조심', 41, @b, 'N', 'N');

-- =====================================================================
-- user 37 : 요리 (default cat 54, 추가 cat 59, 84)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('김치 직접 담가보기', '엄마 레시피 전수받기', 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', '2026-11-20', 0, 1, NULL, 0, 0, 'cooking,family', NULL, 'N', '2026-07-30 18:00:00', '2026-07-30 18:00:00'),
('홈베이킹 마카롱 성공하기', '벌써 3번 실패', 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,hobby', NULL, 'N', '2026-06-14 15:30:00', '2026-06-14 15:30:00'),
('한식 조리기능사 따기', NULL, 37, 84, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'N', '2026-10-30', 0, 1, NULL, 0, 0, 'certification,cooking', NULL, 'N', '2026-05-01 09:00:00', '2026-05-01 09:00:00'),
('일주일 비건 식단 도전', NULL, 37, 54, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 7, 7, '2026-03-14 20:00:00', 0, 0, 'cooking,diet', NULL, 'N', '2026-03-07 10:00:00', '2026-03-07 10:00:00'),
('수제 파스타 면부터 뽑기', '파스타 머신 삼', 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-24 19:30:00', 0, 0, 'cooking', NULL, 'N', '2026-05-18 12:00:00', '2026-05-18 12:00:00'),
('나만의 레시피북 30개 완성', NULL, 37, 54, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 21, 30, NULL, 0, 0, 'cooking,hobby', NULL, 'N', '2025-11-08 21:00:00', '2025-11-08 21:00:00'),
('오마카세 셰프 카운터석 앉기', '월급날 플렉스', 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-27 21:30:00', 0, 0, 'foodie,flexing', NULL, 'N', '2026-02-01 12:00:00', '2026-02-01 12:00:00'),
('발효빵 사워도우 굽기', '스타터 키우는 중', 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,grinding', NULL, 'N', '2026-07-07 08:20:00', '2026-07-07 08:20:00'),
('주말마다 신메뉴 도전 12주', NULL, 37, 54, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 7, 12, NULL, 0, 0, 'cooking', NULL, 'N', '2026-06-06 11:00:00', '2026-06-06 11:00:00'),
('도시락 예쁘게 싸서 SNS 올리기', '한 달 도전', 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,hobby', NULL, 'N', '2026-08-03 07:30:00', '2026-08-03 07:30:00'),
('시장에서 제철 재료로 일주일 살기', NULL, 37, 54, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,foodie', NULL, 'N', '2026-04-16 09:40:00', '2026-04-16 09:40:00'),
('칼질 제대로 배우기', '양파 눈물 없이 썰기', 37, 84, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-20 19:00:00', 0, 0, 'cooking,study', NULL, 'N', '2026-01-05 18:30:00', '2026-01-05 18:30:00'),
('전통주 빚기 클래스 수강', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,culture', NULL, 'N', '2026-06-25 20:10:00', '2026-06-25 20:10:00'),
('우리집 시그니처 메뉴 만들기', '손님 초대용', 37, 54, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking', NULL, 'N', '2026-05-29 17:50:00', '2026-05-29 17:50:00');

-- user 37 함께하기 (30과 쿠킹클래스)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('원데이 쿠킹클래스 3회 같이 가기', '이탈리안-중식-디저트 순서', 37, 54, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 1, 'cooking,socializing', '30', 'N', '2026-07-12 13:00:00', '2026-07-12 13:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 37, 54, 1, 'PROGRESS', NULL),
(@b, 30, 47, 1, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(30, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=37), '님이 함께하는 버킷 "원데이 쿠킹클래스 3회 같이 가기"에 회원님을 초대했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (34,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('디저트 클래스 정보 공유해주세요', 34, @b, 'N', 'N');

-- =====================================================================
-- user 38 : 자격증 헌터 (default cat 55, 추가 cat 70~79 — 카테고리 많은 유저 케이스)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('정보처리기사 합격', '실기가 고비', 38, 70, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2026-11-08', 0, 1, NULL, 0, 0, 'certification,career', NULL, 'N', '2026-06-20 08:00:00', '2026-06-20 08:00:00'),
('토익 900점 넘기기', '현재 820', 38, 71, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-15', 0, 1, NULL, 0, 0, 'academics,career', NULL, 'N', '2026-05-15 07:30:00', '2026-05-15 07:30:00'),
('컴활 1급 필기+실기 한 번에', NULL, 38, 72, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-29 15:00:00', 0, 0, 'certification', NULL, 'N', '2026-02-10 19:00:00', '2026-02-10 19:00:00'),
('한국사능력검정 1급', '벼락치기 가능?', 38, 73, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-25 14:00:00', 0, 0, 'certification,academics', NULL, 'N', '2026-04-28 20:30:00', '2026-04-28 20:30:00'),
('기출문제 1000제 풀기', NULL, 38, 70, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 640, 1000, NULL, 0, 0, 'certification,grinding', NULL, 'N', '2026-06-01 06:30:00', '2026-06-01 06:30:00'),
('아침 스터디카페 30일 출석', '7시 도장', 38, 74, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 30, 30, '2026-04-30 07:10:00', 0, 0, 'study,grinding', NULL, 'N', '2026-04-01 06:50:00', '2026-04-01 06:50:00'),
('자격증 5개 콜렉션 완성', '올해 목표', 38, 55, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 2, 5, NULL, 0, 0, 'certification,career', NULL, 'N', '2026-01-03 09:00:00', '2026-01-03 09:00:00'),
('영어 회화 스터디 20회 참석', NULL, 38, 71, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 12, 20, NULL, 0, 0, 'academics,socializing', NULL, 'N', '2026-03-20 19:30:00', '2026-03-20 19:30:00'),
('노션으로 공부 기록 시스템 만들기', '갓생 템플릿', 38, 75, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-18 22:00:00', 0, 0, 'selfimprovement,study', NULL, 'N', '2026-01-10 21:00:00', '2026-01-10 21:00:00'),
('국비지원 코딩 부트캠프 수료', NULL, 38, 76, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2027-02-28', 0, 1, NULL, 0, 0, 'career,study', NULL, 'N', '2026-07-25 10:00:00', '2026-07-25 10:00:00'),
('자소서 10개 기업 지원', '하반기 공채', 38, 77, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-10-31', 3, 10, NULL, 0, 0, 'career', NULL, 'N', '2026-08-01 23:00:00', '2026-08-01 23:00:00'),
('모의면접 스터디 만들기', NULL, 38, 77, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'career,socializing', NULL, 'N', '2026-07-18 14:20:00', '2026-07-18 14:20:00'),
('운전면허 1종 보통 따기', '장롱면허 탈출기 시작', 38, 78, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-30 16:40:00', 0, 0, 'certification', NULL, 'N', '2025-10-15 09:20:00', '2025-10-15 09:20:00'),
('공부 브이로그 10편 올리기', NULL, 38, 79, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 4, 10, NULL, 0, 0, 'hobby,study', NULL, 'N', '2026-05-05 20:45:00', '2026-05-05 20:45:00');

-- user 38 함께하기 (47과 아침 스터디)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('아침 7시 스터디 인증 21일', '카톡 인증샷 필수', 38, 74, 'TOGETHER', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-09-10', 14, 21, NULL, 0, 0, 'study,grinding', '47', 'N', '2026-07-21 06:40:00', '2026-07-21 06:40:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 38, 74, 14, 'PROGRESS', NULL),
(@b, 47, 89, 21, 'COMPLETE', '2026-08-08 07:05:00');
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(47, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=38), '님이 함께하는 버킷 "아침 7시 스터디 인증 21일"에 회원님을 초대했습니다.'), @b, 1),
(38, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=47), '님이 함께하는 버킷 "아침 7시 스터디 인증 21일"을(를) 완성했습니다.'), @b, 0);

-- =====================================================================
-- user 40 : 반려인 (default cat 57)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('강아지랑 애견동반 카페 10곳', NULL, 40, 57, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'Y', NULL, 6, 10, NULL, 0, 0, 'pet,foodie', NULL, 'N', '2026-03-08 14:00:00', '2026-03-08 14:00:00'),
('반려견 수제간식 만들기', '닭가슴살 육포부터', 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-19 16:00:00', 0, 0, 'pet,cooking', NULL, 'N', '2026-04-05 11:00:00', '2026-04-05 11:00:00'),
('강아지 훈련사 자격 과정 수강', '분리불안 고치자', 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'pet,certification', NULL, 'N', '2026-06-10 19:30:00', '2026-06-10 19:30:00'),
('애견동반 제주여행', '비행기 첫 탑승', 40, 57, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2026-10-05', 0, 1, NULL, 0, 0, 'pet,travel', NULL, 'N', '2026-07-15 20:00:00', '2026-07-15 20:00:00'),
('반려견 프로필 사진 찍어주기', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-14 15:30:00', 0, 0, 'pet,hobby', NULL, 'N', '2026-02-01 10:30:00', '2026-02-01 10:30:00'),
('아침 산책 100일 채우기', '비 오는 날 제외', 40, 57, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 71, 100, NULL, 0, 0, 'pet,workout', NULL, 'N', '2026-05-01 07:00:00', '2026-05-01 07:00:00'),
('유기견 보호소 정기 봉사 6회', NULL, 40, 57, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 4, 6, NULL, 0, 0, 'pet,volunteering', NULL, 'N', '2026-02-20 09:00:00', '2026-02-20 09:00:00'),
('고양이 캣타워 직접 만들기', '원목으로 DIY', 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet,hobby', NULL, 'N', '2026-06-28 13:15:00', '2026-06-28 13:15:00'),
('펫티켓 교육 이수하기', NULL, 40, 57, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-10 18:00:00', 0, 0, 'pet,study', NULL, 'N', '2025-11-28 10:00:00', '2025-11-28 10:00:00'),
('반려견 어질리티 대회 출전', '일단 기초 훈련부터', 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2027-04-30', 0, 1, NULL, 0, 0, 'pet,hobby', NULL, 'N', '2026-07-08 21:00:00', '2026-07-08 21:00:00'),
('강아지 유치원 한 달 보내보기', NULL, 40, 57, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet', NULL, 'N', '2026-08-06 09:30:00', '2026-08-06 09:30:00'),
('펫 동반 글램핑 가기', '바베큐는 내 몫', 40, 57, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-09-26', 0, 1, NULL, 0, 0, 'pet,travel', NULL, 'N', '2026-08-01 19:45:00', '2026-08-01 19:45:00');

-- =====================================================================
-- user 41 : 뷰티/플렉스 (default cat 58)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('퍼스널컬러 진단 받기', '웜톤일 듯', 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'Y', NULL, 1, 1, '2026-03-02 14:30:00', 0, 0, 'beauty', NULL, 'N', '2026-02-15 12:00:00', '2026-02-15 12:00:00'),
('메이크업 클래스 수강하기', '데일리 메이크업 마스터', 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty,study', NULL, 'N', '2026-05-20 11:30:00', '2026-05-20 11:30:00'),
('피부과 관리 10회 코스', NULL, 41, 58, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 6, 10, NULL, 0, 0, 'beauty', NULL, 'N', '2026-04-10 15:00:00', '2026-04-10 15:00:00'),
('명품 가방 첫 구매', '1년 적금 완성하면', 41, 58, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2026-12-24', 0, 1, NULL, 0, 0, 'flexing,finance', NULL, 'N', '2026-01-01 10:00:00', '2026-01-01 10:00:00'),
('호캉스 스위트룸 묵기', '생일 셀프 선물', 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-14 12:00:00', 0, 0, 'flexing,travel', NULL, 'N', '2026-05-30 22:00:00', '2026-05-30 22:00:00'),
('네일아트 셀프로 배우기', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty,hobby', NULL, 'N', '2026-06-22 21:15:00', '2026-06-22 21:15:00'),
('헤어 스타일 과감하게 바꾸기', '단발? 탈색?', 41, 58, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty', NULL, 'N', '2026-07-19 17:40:00', '2026-07-19 17:40:00'),
('화장품 다이어트 - 다 쓰고 사기', '파데 3개 비우기', 41, 58, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 0, 'beauty,finance', NULL, 'N', '2026-03-25 09:20:00', '2026-03-25 09:20:00'),
('바디스크럽 홈케어 루틴 4주', NULL, 41, 58, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 4, 4, '2026-05-01 23:00:00', 0, 0, 'beauty,selfimprovement', NULL, 'N', '2026-04-02 22:30:00', '2026-04-02 22:30:00'),
('향수 시향 모임 가보기', '니치 향수 입문', 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty,socializing', NULL, 'N', '2026-08-02 16:10:00', '2026-08-02 16:10:00'),
('스냅사진 촬영하기', '한강 노을 배경', 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-19', 0, 1, NULL, 0, 0, 'hobby,flexing', NULL, 'N', '2026-07-28 18:30:00', '2026-07-28 18:30:00'),
('미쉐린 레스토랑 도장깨기 3곳', NULL, 41, 58, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 0, 'foodie,flexing', NULL, 'N', '2026-02-28 19:00:00', '2026-02-28 19:00:00');

-- user 41 함께하기 (44와 스냅사진)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('우정 스냅 촬영하기', '컨셉은 Y2K', 41, 58, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-11', 0, 1, NULL, 0, 2, 'hobby,socializing', '44,43', 'N', '2026-08-05 13:20:00', '2026-08-05 13:20:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 41, 58, 0, 'PROGRESS', NULL),
(@b, 44, 83, 0, 'PROGRESS', NULL),
(@b, 43, 82, 0, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(44, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=41), '님이 함께하는 버킷 "우정 스냅 촬영하기"에 회원님을 초대했습니다.'), @b, 0),
(43, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=41), '님이 함께하는 버킷 "우정 스냅 촬영하기"에 회원님을 초대했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (43,@b),(44,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('Y2K 컨셉 완전 기대돼', 44, @b, 'N', 'N'),
('의상은 각자 준비?', 43, @b, 'N', 'N');

-- =====================================================================
-- user 42 : 자기계발 (default cat 81)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('미라클모닝 66일 습관화', '5시 30분 기상', 42, 81, 'CHALLENGE', 'PUBLIC', 'Y', 'PROGRESS', 'Y', NULL, 48, 66, NULL, 0, 0, 'selfimprovement,grinding', NULL, 'N', '2026-06-15 05:30:00', '2026-06-15 05:30:00'),
('사이드프로젝트 출시하기', '가계부 앱 만드는 중', 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', '2026-12-01', 0, 1, NULL, 0, 0, 'career,selfimprovement', NULL, 'N', '2026-04-01 23:00:00', '2026-04-01 23:00:00'),
('하루 계획 세우고 자기 90일', NULL, 42, 81, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 90, 90, '2026-05-31 23:30:00', 0, 0, 'selfimprovement', NULL, 'N', '2026-03-02 22:00:00', '2026-03-02 22:00:00'),
('명상 앱 30일 연속 사용', '마음챙김 입문', 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 30, 30, '2026-02-28 07:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2026-01-29 06:50:00', '2026-01-29 06:50:00'),
('연봉 협상 성공하기', '자료 준비 철저히', 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2027-01-31', 0, 1, NULL, 0, 0, 'career,finance', NULL, 'N', '2026-07-20 21:30:00', '2026-07-20 21:30:00'),
('개발 컨퍼런스 발표하기', '올해는 청중 말고 연사로', 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'career', NULL, 'N', '2026-03-14 20:20:00', '2026-03-14 20:20:00'),
('기술 블로그 주 1회 발행 24주', NULL, 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 15, 24, NULL, 0, 0, 'career,study', NULL, 'N', '2026-04-06 22:40:00', '2026-04-06 22:40:00'),
('디지털 디톡스 주말 4번', '폰 없이 48시간', 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 4, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-06-01 09:00:00', '2026-06-01 09:00:00'),
('멘토 찾아서 커피챗 5회', NULL, 42, 81, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'career,socializing', NULL, 'N', '2026-05-12 12:30:00', '2026-05-12 12:30:00'),
('이력서 영문 버전 만들기', '링크드인도 정리', 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-20 23:50:00', 0, 0, 'career', NULL, 'N', '2026-04-11 21:00:00', '2026-04-11 21:00:00'),
('TED 강연 30편 보고 요약', NULL, 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 22, 30, NULL, 0, 0, 'selfimprovement,study', NULL, 'N', '2026-02-08 19:15:00', '2026-02-08 19:15:00'),
('아침 찬물 샤워 21일', '정신력 테스트', 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 21, 21, '2026-07-21 06:20:00', 0, 0, 'selfimprovement,grinding', NULL, 'N', '2026-07-01 06:15:00', '2026-07-01 06:15:00');

-- =====================================================================
-- user 43 : 문화생활 (default cat 82)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('올해 전시회 20개 관람', NULL, 43, 82, 'CHALLENGE', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2026-12-31', 13, 20, NULL, 0, 0, 'culture', NULL, 'N', '2026-01-04 11:00:00', '2026-01-04 11:00:00'),
('뮤지컬 오리지널 내한공연 보기', '위키드 재내한 기원', 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture,flexing', NULL, 'N', '2026-02-22 20:00:00', '2026-02-22 20:00:00'),
('독립영화관에서 GV 참석', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-26 21:30:00', 0, 0, 'culture', NULL, 'N', '2026-04-10 18:00:00', '2026-04-10 18:00:00'),
('클래식 공연 처음 가보기', '드레스코드 뭐지', 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-15 20:00:00', 0, 0, 'culture', NULL, 'N', '2026-02-25 12:40:00', '2026-02-25 12:40:00'),
('페스티벌 3개 참가', '재즈-록-EDM', 43, 82, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-31', 1, 3, NULL, 0, 0, 'culture,socializing', NULL, 'N', '2026-05-01 15:00:00', '2026-05-01 15:00:00'),
('필름카메라로 한 롤 찍기', '현상소 가는 재미', 43, 82, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-20 17:00:00', 0, 0, 'hobby,culture', NULL, 'N', '2026-05-28 14:30:00', '2026-05-28 14:30:00'),
('연극 소극장 공연 5편 보기', '대학로 나들이', 43, 82, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 3, 5, NULL, 0, 0, 'culture', NULL, 'N', '2026-03-30 19:20:00', '2026-03-30 19:20:00'),
('오페라 한 편 완주하기', '3시간 버틸 수 있을까', 43, 82, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture', NULL, 'N', '2026-07-09 22:10:00', '2026-07-09 22:10:00'),
('미술관 도슨트 투어 참여', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-17 15:00:00', 0, 0, 'culture,study', NULL, 'N', '2026-05-02 10:00:00', '2026-05-02 10:00:00'),
('직접 단편 시나리오 써보기', NULL, 43, 82, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2027-03-31', 0, 1, NULL, 0, 0, 'hobby,career', NULL, 'N', '2026-06-18 23:40:00', '2026-06-18 23:40:00'),
('부산국제영화제 가기', '숙소 예약 전쟁', 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', '2026-10-04', 0, 1, NULL, 0, 0, 'culture,travel', NULL, 'N', '2026-07-30 09:30:00', '2026-07-30 09:30:00'),
('LP 턴테이블 장만하고 10장 모으기', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 4, 10, NULL, 0, 0, 'hobby,flexing', NULL, 'N', '2025-12-20 21:50:00', '2025-12-20 21:50:00');

-- user 43 함께하기 (48과 전시 투어)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('한 달에 한 번 전시 데이트 6회', '전시 후 카페 후기 필수', 43, 82, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 4, 6, NULL, 0, 3, 'culture,socializing', '48', 'N', '2026-03-01 10:00:00', '2026-03-01 10:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 43, 82, 4, 'PROGRESS', NULL),
(@b, 48, 95, 4, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(48, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=43), '님이 함께하는 버킷 "한 달에 한 번 전시 데이트 6회"에 회원님을 초대했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (41,@b),(33,@b),(50,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('이번 달은 어디 가세요?', 33, @b, 'N', 'N'),
('저도 다음에 껴주세요~', 41, @b, 'N', 'N');

-- =====================================================================
-- user 44 : 사교/연애 (default cat 83, 추가 86,87,91,92,93)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('한 달에 새로운 모임 하나씩 나가기', '6개월 도전', 44, 86, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 4, 6, NULL, 0, 0, 'socializing', NULL, 'N', '2026-03-01 18:00:00', '2026-03-01 18:00:00'),
('와인 모임 호스트 해보기', '홈파티 준비', 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-27', 0, 1, NULL, 0, 0, 'socializing,foodie', NULL, 'N', '2026-07-25 19:30:00', '2026-07-25 19:30:00'),
('소개팅 어플 말고 자연스럽게 만나기', '동호회부터', 44, 83, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'dating', NULL, 'N', '2026-05-05 23:20:00', '2026-05-05 23:20:00'),
('100일 기념 여행 계획 짜기', NULL, 44, 91, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-11-14', 0, 1, NULL, 0, 0, 'dating,travel', NULL, 'N', '2026-08-02 22:30:00', '2026-08-02 22:30:00'),
('동창회 주최하기', '10년 만의 모임', 44, 92, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-12-27', 0, 1, NULL, 0, 0, 'socializing', NULL, 'N', '2026-06-30 20:40:00', '2026-06-30 20:40:00'),
('보드게임 모임 정기화하기', NULL, 44, 86, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-06 22:00:00', 0, 0, 'socializing,gaming', NULL, 'N', '2026-03-15 19:00:00', '2026-03-15 19:00:00'),
('지인 결혼식 사회 보기', '떨린다', 44, 93, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-24 13:00:00', 0, 0, 'socializing', NULL, 'N', '2026-04-20 21:10:00', '2026-04-20 21:10:00'),
('버스킹 구경하다가 즉석 합창 참여', '용기 테스트', 44, 83, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'socializing,culture', NULL, 'N', '2026-06-12 17:50:00', '2026-06-12 17:50:00'),
('혼자 온 사람들 모임 참석해보기', NULL, 44, 86, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-21 20:30:00', 0, 0, 'socializing', NULL, 'N', '2026-02-10 12:00:00', '2026-02-10 12:00:00'),
('연애편지 손글씨로 써보기', NULL, 44, 91, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'dating', NULL, 'N', '2026-07-14 23:50:00', '2026-07-14 23:50:00'),
('MBTI 파티 열기', '16유형 다 모으면 성공', 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'socializing,hobby', NULL, 'N', '2026-05-19 21:00:00', '2026-05-19 21:00:00'),
('선셋 피크닉 준비해서 친구들 초대', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-07 19:40:00', 0, 0, 'socializing,foodie', NULL, 'N', '2026-05-25 13:30:00', '2026-05-25 13:30:00');

-- =====================================================================
-- user 45 : 봉사 (default cat 85)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('연탄봉사 겨울 시즌 4회', NULL, 45, 85, 'CHALLENGE', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2027-02-28', 1, 4, NULL, 0, 0, 'volunteering', NULL, 'N', '2025-12-01 08:00:00', '2025-12-01 08:00:00'),
('헌혈 10회 달성', '헌혈증 모으기', 45, 85, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 7, 10, NULL, 0, 0, 'volunteering', NULL, 'N', '2025-10-10 12:00:00', '2025-10-10 12:00:00'),
('유기견 보호소 임시보호 해보기', '한 마리라도', 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering,pet', NULL, 'N', '2026-04-15 10:30:00', '2026-04-15 10:30:00'),
('플로깅 10회 하기', '러닝+쓰레기줍기', 45, 85, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 6, 10, NULL, 0, 0, 'volunteering,workout', NULL, 'N', '2026-03-22 07:30:00', '2026-03-22 07:30:00'),
('어르신 스마트폰 교육 봉사', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-30 16:00:00', 0, 0, 'volunteering,study', NULL, 'N', '2026-05-01 09:00:00', '2026-05-01 09:00:00'),
('기부 정기결제 시작하기', '월 3만원부터', 45, 85, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-05 10:00:00', 0, 0, 'volunteering,finance', NULL, 'N', '2026-01-01 09:30:00', '2026-01-01 09:30:00'),
('보육원 아이들과 놀이 봉사 6회', NULL, 45, 85, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 3, 6, NULL, 0, 0, 'volunteering,parenting', NULL, 'N', '2026-02-14 13:00:00', '2026-02-14 13:00:00'),
('재능기부로 무료 사진 촬영', '어르신 장수사진', 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-15', 0, 1, NULL, 0, 0, 'volunteering,hobby', NULL, 'N', '2026-07-01 11:20:00', '2026-07-01 11:20:00'),
('환경 다큐 보고 제로웨이스트 실천 30일', NULL, 45, 85, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 19, 30, NULL, 0, 0, 'volunteering,selfimprovement', NULL, 'N', '2026-07-20 08:40:00', '2026-07-20 08:40:00'),
('텀블러만 쓰기 100일', '일회용컵 제로', 45, 85, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 100, 100, '2026-06-10 09:00:00', 0, 0, 'volunteering,grinding', NULL, 'N', '2026-03-02 08:30:00', '2026-03-02 08:30:00'),
('마을 도서관 책 기부 50권', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 32, 50, NULL, 0, 0, 'volunteering,reading', NULL, 'N', '2026-06-05 14:00:00', '2026-06-05 14:00:00');

-- =====================================================================
-- user 47 : 대학생 (default cat 89, 추가 90)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('이번 학기 올 A 받기', '재수강 없는 삶', 47, 89, 'ORIGINAL', 'PRIVATE', 'Y', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'academics,grinding', NULL, 'N', '2026-08-01 10:00:00', '2026-08-01 10:00:00'),
('교환학생 지원하기', '독일 아니면 캐나다', 47, 89, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'Y', '2026-09-30', 0, 1, NULL, 0, 0, 'academics,travel', NULL, 'N', '2026-06-20 15:00:00', '2026-06-20 15:00:00'),
('공모전 3개 참가하기', NULL, 47, 90, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-30', 1, 3, NULL, 0, 0, 'career,academics', NULL, 'N', '2026-04-10 21:00:00', '2026-04-10 21:00:00'),
('과탑 한 번 해보기', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'academics', NULL, 'N', '2026-03-02 09:00:00', '2026-03-02 09:00:00'),
('동아리 회장 임기 무사히 마치기', '축제 부스가 최대 과제', 47, 90, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'socializing,career', NULL, 'N', '2026-03-15 18:30:00', '2026-03-15 18:30:00'),
('밤샘 과제 없는 학기 만들기', '계획적으로 살기', 47, 89, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'academics,selfimprovement', NULL, 'N', '2026-08-05 23:40:00', '2026-08-05 23:40:00'),
('대학 축제 무대 서보기', '밴드부 보컬 오디션', 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-22 21:00:00', 0, 0, 'culture,socializing', NULL, 'N', '2026-04-01 19:00:00', '2026-04-01 19:00:00'),
('전공 서적 원서로 한 권 떼기', NULL, 47, 89, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'academics,reading', NULL, 'N', '2026-07-03 14:20:00', '2026-07-03 14:20:00'),
('학식 전 메뉴 정복', '43개 메뉴', 47, 89, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 28, 43, NULL, 0, 0, 'foodie,hobby', NULL, 'N', '2026-03-05 12:30:00', '2026-03-05 12:30:00'),
('아르바이트로 등록금 한 학기 벌기', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2027-02-28', 0, 1, NULL, 0, 0, 'finance,grinding', NULL, 'N', '2026-06-25 22:00:00', '2026-06-25 22:00:00'),
('졸업 전에 논문 학회 제출', '지도교수님 컨택 완료', 47, 89, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2027-06-30', 0, 1, NULL, 0, 0, 'academics,career', NULL, 'N', '2026-07-28 16:45:00', '2026-07-28 16:45:00'),
('시험기간 도서관 오픈런 5회', NULL, 47, 89, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 5, 5, '2026-06-19 08:00:00', 0, 0, 'academics,grinding', NULL, 'N', '2026-06-01 07:50:00', '2026-06-01 07:50:00');

-- =====================================================================
-- user 48 : 취미부자 (default cat 94, 추가 95)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('도자기 공방에서 그릇 만들기', '물레 체험', 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'Y', NULL, 1, 1, '2026-04-13 16:00:00', 0, 0, 'hobby,culture', NULL, 'N', '2026-03-28 11:00:00', '2026-03-28 11:00:00'),
('원데이클래스 12종 도전', '가죽공예까지 왔다', 48, 94, 'CHALLENGE', 'PUBLIC', 'Y', 'PROGRESS', 'Y', NULL, 8, 12, NULL, 0, 0, 'hobby', NULL, 'N', '2026-01-10 13:00:00', '2026-01-10 13:00:00'),
('기타 배워서 한 곡 완주', '체리필터 낭만고양이', 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-05-06 20:30:00', '2026-05-06 20:30:00'),
('뜨개질로 목도리 완성', '겨울 선물용', 48, 94, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-12-10', 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-07-22 21:40:00', '2026-07-22 21:40:00'),
('수채화 스케치북 한 권 채우기', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,culture', NULL, 'N', '2026-02-18 15:20:00', '2026-02-18 15:20:00'),
('홈가드닝 식물 10종 키우기', '몬스테라부터', 48, 94, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 6, 10, NULL, 0, 0, 'hobby', NULL, 'N', '2025-11-15 10:00:00', '2025-11-15 10:00:00'),
('캘리그라피로 명언 30개 쓰기', NULL, 48, 94, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 17, 30, NULL, 0, 0, 'hobby,selfimprovement', NULL, 'N', '2026-04-25 22:10:00', '2026-04-25 22:10:00'),
('목공으로 협탁 만들기', '주말 목공소 예약', 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-18', 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-08-03 12:50:00', '2026-08-03 12:50:00'),
('자수 놓아서 액자 걸기', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-29 18:30:00', 0, 0, 'hobby', NULL, 'N', '2026-06-01 20:00:00', '2026-06-01 20:00:00'),
('향초 만들기 클래스 듣기', '선물용 3개 제작', 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-08 15:30:00', 0, 0, 'hobby', NULL, 'N', '2026-01-25 12:00:00', '2026-01-25 12:00:00'),
('전통 매듭 팔찌 만들어 판매', '플리마켓 도전', 48, 94, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,finance', NULL, 'N', '2026-07-10 17:00:00', '2026-07-10 17:00:00'),
('사진 출사 모임 5회 참여', NULL, 48, 95, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'hobby,socializing', NULL, 'N', '2026-06-14 09:00:00', '2026-06-14 09:00:00');

-- =====================================================================
-- user 49 : 다이어터 (default cat 96)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('5kg 감량하기', '건강하게 천천히', 49, 96, 'ORIGINAL', 'PRIVATE', 'Y', 'PROGRESS', 'N', '2026-11-30', 0, 1, NULL, 0, 0, 'diet', NULL, 'N', '2026-06-01 07:00:00', '2026-06-01 07:00:00'),
('설탕 끊기 30일', '커피는 아메리카노만', 49, 96, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 21, 30, NULL, 0, 0, 'diet,grinding', NULL, 'N', '2026-07-15 08:00:00', '2026-07-15 08:00:00'),
('식단 사진 기록 60일', NULL, 49, 96, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 42, 60, NULL, 0, 0, 'diet', NULL, 'N', '2026-06-25 12:10:00', '2026-06-25 12:10:00'),
('야식 끊기 3주', '10시 이후 금식', 49, 96, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 21, 21, '2026-05-21 22:00:00', 0, 0, 'diet', NULL, 'N', '2026-05-01 21:00:00', '2026-05-01 21:00:00'),
('제로콜라도 끊기', '탄산수로 대체', 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'diet', NULL, 'N', '2026-08-04 13:30:00', '2026-08-04 13:30:00'),
('샐러드 도시락 직접 싸기 20회', NULL, 49, 96, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 11, 20, NULL, 0, 0, 'diet,cooking', NULL, 'N', '2026-07-01 07:20:00', '2026-07-01 07:20:00'),
('인터벌 러닝 주 3회 8주', '지옥의 400m', 49, 96, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 14, 24, NULL, 0, 0, 'workout,diet', NULL, 'N', '2026-06-20 06:30:00', '2026-06-20 06:30:00'),
('바디체크 사진 매주 남기기 12주', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 7, 12, NULL, 0, 0, 'diet,workout', NULL, 'N', '2026-06-22 08:15:00', '2026-06-22 08:15:00'),
('치팅데이 계획적으로 즐기기', '월 2회만', 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'diet,foodie', NULL, 'N', '2026-07-06 19:00:00', '2026-07-06 19:00:00'),
('영양사 상담 받고 식단 짜기', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-05 14:00:00', 0, 0, 'diet,study', NULL, 'N', '2026-05-28 10:30:00', '2026-05-28 10:30:00'),
('등산으로 유산소 대체 5회', '주말마다 관악산', 49, 96, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 5, 5, '2026-04-27 15:00:00', 0, 0, 'workout,travel', NULL, 'N', '2026-03-29 08:00:00', '2026-03-29 08:00:00');

-- user 49 함께하기 (32와 식단 인증)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('아침 공복 유산소 인증 30일', '서로 감시하기', 49, 96, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-08', 24, 30, NULL, 0, 5, 'workout,diet', '32', 'N', '2026-07-10 06:00:00', '2026-07-10 06:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 49, 96, 24, 'PROGRESS', NULL),
(@b, 32, 49, 30, 'COMPLETE', '2026-08-08 06:40:00');
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(32, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=49), '님이 함께하는 버킷 "아침 공복 유산소 인증 30일"에 회원님을 초대했습니다.'), @b, 1),
(49, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=32), '님이 함께하는 버킷 "아침 공복 유산소 인증 30일"을(를) 완성했습니다.'), @b, 0);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (30,@b),(36,@b),(41,@b),(42,@b),(50,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('두 분 다 대단.. 저는 이불 유산소 중', 50, @b, 'N', 'N'),
('공복 유산소 효과 확실해요?', 41, @b, 'N', 'N'),
('30일 완주자 등장 ㄷㄷ', 30, @b, 'N', 'N');

-- =====================================================================
-- user 50 : 올라운더 (default cat 97)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('죽기 전에 오로라 보기', '아이슬란드 or 캐나다', 50, 97, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', NULL, 0, 1, NULL, 0, 0, 'travel,flexing', NULL, 'N', '2025-09-09 21:00:00', '2025-09-09 21:00:00'),
('번지점프 해보기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-05 14:30:00', 0, 0, 'hobby,flexing', NULL, 'N', '2026-04-15 12:00:00', '2026-04-15 12:00:00'),
('혼자 영화관 가보기', '이게 왜 어렵지', 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-24 22:00:00', 0, 0, 'culture', NULL, 'N', '2026-01-20 18:00:00', '2026-01-20 18:00:00'),
('하프마라톤 완주', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-25', 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-06-15 07:00:00', '2026-06-15 07:00:00'),
('일주일 폰 없이 살기', '피처폰 개통?', 50, 97, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-07-07 09:00:00', '2026-07-07 09:00:00'),
('부모님께 손편지 쓰기', '어버이날 말고 평일에', 50, 97, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-18 21:30:00', 0, 0, 'family', NULL, 'N', '2026-03-15 20:00:00', '2026-03-15 20:00:00'),
('새벽 수산시장 가서 회 먹기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-07 05:30:00', 0, 0, 'foodie', NULL, 'N', '2026-02-01 22:00:00', '2026-02-01 22:00:00'),
('버킷리스트 100개 작성하기', '일단 목록부터', 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 73, 100, NULL, 0, 0, 'selfimprovement,hobby', NULL, 'N', '2026-01-01 00:30:00', '2026-01-01 00:30:00'),
('템플스테이 1박 2일', '디지털 디톡스 겸', 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-13', 0, 1, NULL, 0, 0, 'culture,selfimprovement', NULL, 'N', '2026-07-30 10:00:00', '2026-07-30 10:00:00'),
('스승의 날에 은사님 찾아뵙기', NULL, 50, 97, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-15 17:00:00', 0, 0, 'socializing,family', NULL, 'N', '2026-05-10 09:00:00', '2026-05-10 09:00:00'),
('요트 투어 해보기', '한강 요트도 OK', 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'flexing,travel', NULL, 'N', '2026-06-28 15:40:00', '2026-06-28 15:40:00'),
('국내 미술관 야외 조각공원 피크닉', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture,socializing', NULL, 'N', '2026-08-06 11:30:00', '2026-08-06 11:30:00');

-- user 50 함께하기 (44, 35와 새해 버킷 공유)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('한 달에 한 번 새로운 경험 12개', '뭐든 처음 해보는 걸로', 50, 97, 'TOGETHER', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 7, 12, NULL, 0, 4, 'hobby,socializing', '44,35', 'N', '2026-01-05 12:00:00', '2026-01-05 12:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO bucket_member (bucket_id, user_id, category_id, user_count, status, completed_date) VALUES
(@b, 50, 97, 7, 'PROGRESS', NULL),
(@b, 44, 83, 5, 'PROGRESS', NULL),
(@b, 35, 52, 8, 'PROGRESS', NULL);
INSERT INTO alarm (user_id, type, message, bucket_id, is_read) VALUES
(44, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=50), '님이 함께하는 버킷 "한 달에 한 번 새로운 경험 12개"에 회원님을 초대했습니다.'), @b, 1),
(35, 'TOGETHER', CONCAT((SELECT name FROM user WHERE id=50), '님이 함께하는 버킷 "한 달에 한 번 새로운 경험 12개"에 회원님을 초대했습니다.'), @b, 1);
INSERT INTO like_bucket (user_id, bucket_id) VALUES (30,@b),(41,@b),(43,@b),(48,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('이번 달은 뭐 하셨어요?', 30, @b, 'N', 'N'),
('7월은 서핑이었대요 부럽', 43, @b, 'N', 'N'),
('스팸 링크 klsd.xyz', 44, @b, 'Y', 'N'),
('12월엔 다 같이 스키장 어때요', 48, @b, 'N', 'N');

-- =====================================================================
-- 시리즈: user 30 국내 여행지 도장깨기 (완료 위주, 날짜 분산)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('여행 도장깨기 - 경주 불국사', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-14 15:00:00', 0, 0, 'travel', NULL, 'N', '2025-09-01 09:00:00', '2025-09-01 09:00:00'),
('여행 도장깨기 - 여수 밤바다', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-28 21:00:00', 0, 0, 'travel', NULL, 'N', '2025-09-15 09:00:00', '2025-09-15 09:00:00'),
('여행 도장깨기 - 남해 독일마을', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-12 14:00:00', 0, 0, 'travel', NULL, 'N', '2025-10-01 09:00:00', '2025-10-01 09:00:00'),
('여행 도장깨기 - 안동 하회마을', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-26 13:30:00', 0, 0, 'travel,culture', NULL, 'N', '2025-10-13 09:00:00', '2025-10-13 09:00:00'),
('여행 도장깨기 - 담양 죽녹원', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-09 11:00:00', 0, 0, 'travel', NULL, 'N', '2025-11-01 09:00:00', '2025-11-01 09:00:00'),
('여행 도장깨기 - 정선 레일바이크', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-23 15:30:00', 0, 0, 'travel,hobby', NULL, 'N', '2025-11-10 09:00:00', '2025-11-10 09:00:00'),
('여행 도장깨기 - 태백산 눈꽃', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-21 12:00:00', 0, 0, 'travel,workout', NULL, 'N', '2025-12-08 09:00:00', '2025-12-08 09:00:00'),
('여행 도장깨기 - 강화도 루지', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-11 14:20:00', 0, 0, 'travel,hobby', NULL, 'N', '2026-01-02 09:00:00', '2026-01-02 09:00:00'),
('여행 도장깨기 - 통영 케이블카', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-25 13:00:00', 0, 0, 'travel', NULL, 'N', '2026-01-12 09:00:00', '2026-01-12 09:00:00'),
('여행 도장깨기 - 보성 녹차밭', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-08 11:30:00', 0, 0, 'travel', NULL, 'N', '2026-02-01 09:00:00', '2026-02-01 09:00:00'),
('여행 도장깨기 - 군산 시간여행마을', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-22 14:40:00', 0, 0, 'travel,culture', NULL, 'N', '2026-02-09 09:00:00', '2026-02-09 09:00:00'),
('여행 도장깨기 - 속초 아바이마을', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-08 12:10:00', 0, 0, 'travel,foodie', NULL, 'N', '2026-03-01 09:00:00', '2026-03-01 09:00:00'),
('여행 도장깨기 - 광양 매화마을', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-22 13:50:00', 0, 0, 'travel', NULL, 'N', '2026-03-09 09:00:00', '2026-03-09 09:00:00'),
('여행 도장깨기 - 진해 군항제', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-05 16:00:00', 0, 0, 'travel,culture', NULL, 'N', '2026-03-25 09:00:00', '2026-03-25 09:00:00'),
('여행 도장깨기 - 태안 튤립축제', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-19 14:30:00', 0, 0, 'travel', NULL, 'N', '2026-04-06 09:00:00', '2026-04-06 09:00:00'),
('여행 도장깨기 - 울릉도 독도', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-04 10:00:00', 0, 0, 'travel', NULL, 'N', '2026-04-20 09:00:00', '2026-04-20 09:00:00'),
('여행 도장깨기 - 곡성 기차마을', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-17 15:20:00', 0, 0, 'travel,family', NULL, 'N', '2026-05-05 09:00:00', '2026-05-05 09:00:00'),
('여행 도장깨기 - 단양 패러글라이딩', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-31 11:40:00', 0, 0, 'travel,hobby', NULL, 'N', '2026-05-18 09:00:00', '2026-05-18 09:00:00'),
('여행 도장깨기 - 포항 스페이스워크', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-14 17:30:00', 0, 0, 'travel', NULL, 'N', '2026-06-01 09:00:00', '2026-06-01 09:00:00'),
('여행 도장깨기 - 무주 반딧불축제', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-28 21:20:00', 0, 0, 'travel', NULL, 'N', '2026-06-15 09:00:00', '2026-06-15 09:00:00'),
('여행 도장깨기 - 부여 궁남지', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-12 10:50:00', 0, 0, 'travel,culture', NULL, 'N', '2026-07-01 09:00:00', '2026-07-01 09:00:00'),
('여행 도장깨기 - 평창 대관령 양떼목장', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-26 13:10:00', 0, 0, 'travel', NULL, 'N', '2026-07-13 09:00:00', '2026-07-13 09:00:00'),
('여행 도장깨기 - 거제 바람의언덕', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-23', 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-08-01 09:00:00', '2026-08-01 09:00:00'),
('여행 도장깨기 - 영월 별마로천문대', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-06', 0, 1, NULL, 0, 0, 'travel,hobby', NULL, 'N', '2026-08-03 09:00:00', '2026-08-03 09:00:00'),
('여행 도장깨기 - 순천만 갈대밭', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-18', 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-08-05 09:00:00', '2026-08-05 09:00:00'),
('여행 도장깨기 - 제주 우도', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-08-06 09:00:00', '2026-08-06 09:00:00'),
('여행 도장깨기 - 대구 서문시장 야시장', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,foodie', NULL, 'N', '2026-08-07 09:00:00', '2026-08-07 09:00:00'),
('여행 도장깨기 - 인천 차이나타운', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,foodie', NULL, 'N', '2026-08-08 09:00:00', '2026-08-08 09:00:00');

-- =====================================================================
-- 시리즈: user 32 주간 러닝 결산 (1~30주차, 대부분 완료)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('주간 러닝 1주차 - 15km', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-01-11 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-01-05 06:30:00', '2026-01-05 06:30:00'),
('주간 러닝 2주차 - 16km', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-01-18 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-01-12 06:30:00', '2026-01-12 06:30:00'),
('주간 러닝 3주차 - 혹한기 실내 대체', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-01-25 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-01-19 06:30:00', '2026-01-19 06:30:00'),
('주간 러닝 4주차 - 18km', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-02-01 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-01-26 06:30:00', '2026-01-26 06:30:00'),
('주간 러닝 5주차 - 20km 첫 돌파', '기록 갱신!', 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-02-08 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-02-02 06:30:00', '2026-02-02 06:30:00'),
('주간 러닝 6주차 - 감기로 휴식', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 0, 'workout', NULL, 'N', '2026-02-09 06:30:00', '2026-02-09 06:30:00'),
('주간 러닝 7주차 - 복귀 런', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-02-22 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-02-16 06:30:00', '2026-02-16 06:30:00'),
('주간 러닝 8주차 - 인터벌 도입', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-03-01 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-02-23 06:30:00', '2026-02-23 06:30:00'),
('주간 러닝 9주차 - 22km', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-03-08 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-03-02 06:30:00', '2026-03-02 06:30:00'),
('주간 러닝 10주차 - 10km 대회 참가', '48분 12초', 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-03-15 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-03-09 06:30:00', '2026-03-09 06:30:00'),
('주간 러닝 11주차 - 리커버리 위크', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-03-22 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-03-16 06:30:00', '2026-03-16 06:30:00'),
('주간 러닝 12주차 - 벚꽃런', '여의도 코스', 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-03-29 20:00:00', 0, 0, 'workout,travel', NULL, 'N', '2026-03-23 06:30:00', '2026-03-23 06:30:00'),
('주간 러닝 13주차 - 25km', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-04-05 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-03-30 06:30:00', '2026-03-30 06:30:00'),
('주간 러닝 14주차 - 트랙 훈련', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-04-12 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-04-06 06:30:00', '2026-04-06 06:30:00'),
('주간 러닝 15주차 - 비와서 2회만', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 2, 3, NULL, 0, 0, 'workout', NULL, 'N', '2026-04-13 06:30:00', '2026-04-13 06:30:00'),
('주간 러닝 16주차 - 새 러닝화 테스트', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-04-26 20:00:00', 0, 0, 'workout,flexing', NULL, 'N', '2026-04-20 06:30:00', '2026-04-20 06:30:00'),
('주간 러닝 17주차 - 남산 업힐', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-05-03 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-04-27 06:30:00', '2026-04-27 06:30:00'),
('주간 러닝 18주차 - 28km', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-05-10 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-05-04 06:30:00', '2026-05-04 06:30:00'),
('주간 러닝 19주차 - 러닝크루 합류', '드디어 크루 가입', 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-05-17 20:00:00', 0, 0, 'workout,socializing', NULL, 'N', '2026-05-11 06:30:00', '2026-05-11 06:30:00'),
('주간 러닝 20주차 - 30km 달성', NULL, 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-05-24 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-05-18 06:30:00', '2026-05-18 06:30:00'),
('주간 러닝 21주차 - 야간런 전환', '더워서 밤에 뜀', 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-05-31 22:00:00', 0, 0, 'workout', NULL, 'N', '2026-05-25 06:30:00', '2026-05-25 06:30:00'),
('주간 러닝 22주차 - 하프 페이스 훈련', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-06-07 22:00:00', 0, 0, 'workout', NULL, 'N', '2026-06-01 06:30:00', '2026-06-01 06:30:00'),
('주간 러닝 23주차 - 장마 대비 실내', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-06-14 22:00:00', 0, 0, 'workout', NULL, 'N', '2026-06-08 06:30:00', '2026-06-08 06:30:00'),
('주간 러닝 24주차 - 부상 조심 주간', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 2, 3, NULL, 0, 0, 'workout', NULL, 'N', '2026-06-15 06:30:00', '2026-06-15 06:30:00'),
('주간 러닝 25주차 - 재활 걷기런', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-06-28 22:00:00', 0, 0, 'workout', NULL, 'N', '2026-06-22 06:30:00', '2026-06-22 06:30:00'),
('주간 러닝 26주차 - 상반기 결산 320km', '반년 총결산', 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-07-05 22:00:00', 0, 0, 'workout,grinding', NULL, 'N', '2026-06-29 06:30:00', '2026-06-29 06:30:00'),
('주간 러닝 27주차 - 새벽런 복귀', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-07-12 07:00:00', 0, 0, 'workout', NULL, 'N', '2026-07-06 06:30:00', '2026-07-06 06:30:00'),
('주간 러닝 28주차 - 폭염주의보 단축런', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-07-19 07:00:00', 0, 0, 'workout', NULL, 'N', '2026-07-13 06:30:00', '2026-07-13 06:30:00'),
('주간 러닝 29주차 - 트레일런 입문', '산길은 다르다', 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-07-26 07:00:00', 0, 0, 'workout,travel', NULL, 'N', '2026-07-20 06:30:00', '2026-07-20 06:30:00'),
('주간 러닝 30주차 - 이번 주 진행 중', NULL, 32, 49, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 0, 'workout', NULL, 'N', '2026-08-03 06:30:00', '2026-08-03 06:30:00');

-- =====================================================================
-- 시리즈: user 33 읽고 싶은 책 리스트 (일부 완독)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('완독 - 데미안', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-08 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-01-02 21:00:00', '2026-01-02 21:00:00'),
('완독 - 1984', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-17 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-01-09 21:00:00', '2026-01-09 21:00:00'),
('완독 - 어린 왕자 원서', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-26 22:00:00', 0, 0, 'reading,academics', NULL, 'N', '2026-01-18 21:00:00', '2026-01-18 21:00:00'),
('완독 - 코스모스', '두껍지만 재밌음', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-15 22:00:00', 0, 0, 'reading,study', NULL, 'N', '2026-01-27 21:00:00', '2026-01-27 21:00:00'),
('완독 - 총 균 쇠', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-08 22:00:00', 0, 0, 'reading,study', NULL, 'N', '2026-02-16 21:00:00', '2026-02-16 21:00:00'),
('완독 - 사피엔스', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-29 22:00:00', 0, 0, 'reading,study', NULL, 'N', '2026-03-09 21:00:00', '2026-03-09 21:00:00'),
('완독 - 미움받을 용기', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-07 22:00:00', 0, 0, 'reading,selfimprovement', NULL, 'N', '2026-03-30 21:00:00', '2026-03-30 21:00:00'),
('완독 - 아몬드', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-14 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-04-08 21:00:00', '2026-04-08 21:00:00'),
('완독 - 불편한 편의점', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-22 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-04-15 21:00:00', '2026-04-15 21:00:00'),
('완독 - 파친코', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-06 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-04-23 21:00:00', '2026-04-23 21:00:00'),
('완독 - 종의 기원', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-15 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-05-07 21:00:00', '2026-05-07 21:00:00'),
('완독 - 달러구트 꿈 백화점', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-23 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-05-16 21:00:00', '2026-05-16 21:00:00'),
('완독 - 역행자', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-01 22:00:00', 0, 0, 'reading,selfimprovement', NULL, 'N', '2026-05-24 21:00:00', '2026-05-24 21:00:00'),
('완독 - 물고기는 존재하지 않는다', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-12 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-06-02 21:00:00', '2026-06-02 21:00:00'),
('완독 - 구의 증명', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-19 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-06-13 21:00:00', '2026-06-13 21:00:00'),
('완독 - 급류', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-27 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-06-20 21:00:00', '2026-06-20 21:00:00'),
('완독 - 세상의 마지막 기차역', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-05 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-06-28 21:00:00', '2026-06-28 21:00:00'),
('완독 - 트렌드 코리아 2026', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-15 22:00:00', 0, 0, 'reading,career', NULL, 'N', '2026-07-06 21:00:00', '2026-07-06 21:00:00'),
('읽는 중 - 카라마조프가의 형제들', '3권 중 1권째', 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 0, 'reading', NULL, 'N', '2026-07-16 21:00:00', '2026-07-16 21:00:00'),
('읽는 중 - 코스믹 호러 단편선', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading', NULL, 'N', '2026-08-01 21:00:00', '2026-08-01 21:00:00'),
('읽을 예정 - 모비딕', '올가을 목표', 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-11-30', 0, 1, NULL, 0, 0, 'reading', NULL, 'N', '2026-08-05 21:00:00', '2026-08-05 21:00:00'),
('읽을 예정 - 안나 카레니나', NULL, 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading', NULL, 'N', '2026-08-06 21:00:00', '2026-08-06 21:00:00');

-- =====================================================================
-- 시리즈: user 34 아이 성장 기록 (월령별)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('성장기록 - 첫 뒤집기 영상 남기기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-08 14:00:00', 0, 0, 'parenting', NULL, 'N', '2025-10-01 10:00:00', '2025-10-01 10:00:00'),
('성장기록 - 첫 이유식 반응 찍기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-02 11:30:00', 0, 0, 'parenting', NULL, 'N', '2025-10-25 10:00:00', '2025-10-25 10:00:00'),
('성장기록 - 첫 옹알이 녹음', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-20 09:00:00', 0, 0, 'parenting', NULL, 'N', '2025-11-10 10:00:00', '2025-11-10 10:00:00'),
('성장기록 - 첫 배밀이', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-15 16:20:00', 0, 0, 'parenting', NULL, 'N', '2025-12-01 10:00:00', '2025-12-01 10:00:00'),
('성장기록 - 첫니 나온 날 기록', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-19 08:40:00', 0, 0, 'parenting', NULL, 'N', '2026-01-05 10:00:00', '2026-01-05 10:00:00'),
('성장기록 - 첫 걸음마 영상', '드디어!!', 34, 51, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-30 17:50:00', 0, 0, 'parenting,family', NULL, 'N', '2026-03-01 10:00:00', '2026-03-01 10:00:00'),
('성장기록 - 첫 엄마 발음', NULL, 34, 51, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-22 19:10:00', 0, 0, 'parenting', NULL, 'N', '2026-04-01 10:00:00', '2026-04-01 10:00:00'),
('성장기록 - 돌잔치 준비', '돌잡이 뭐 잡을까', 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-20 13:00:00', 0, 0, 'parenting,family', NULL, 'N', '2026-05-15 10:00:00', '2026-05-15 10:00:00'),
('성장기록 - 첫 바다 구경시켜주기', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-30', 0, 1, NULL, 0, 0, 'parenting,travel', NULL, 'N', '2026-08-01 10:00:00', '2026-08-01 10:00:00'),
('성장기록 - 18개월 검진', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-15', 0, 1, NULL, 0, 0, 'parenting', NULL, 'N', '2026-08-04 10:00:00', '2026-08-04 10:00:00'),
('성장기록 - 두돌 사진 앨범 만들기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2027-06-01', 0, 1, NULL, 0, 0, 'parenting,hobby', NULL, 'N', '2026-08-06 10:00:00', '2026-08-06 10:00:00');

-- =====================================================================
-- 시리즈: user 35 명작 게임 클리어 도장깨기
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('클리어 - 엘든 링', '첫 소울류 입문', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-20 02:30:00', 0, 0, 'gaming', NULL, 'N', '2025-09-10 20:00:00', '2025-09-10 20:00:00'),
('클리어 - 발더스 게이트 3', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-05 01:00:00', 0, 0, 'gaming', NULL, 'N', '2025-10-21 20:00:00', '2025-10-21 20:00:00'),
('클리어 - 갓 오브 워 라그나로크', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-02 23:40:00', 0, 0, 'gaming', NULL, 'N', '2025-12-06 20:00:00', '2025-12-06 20:00:00'),
('클리어 - 젤다 왕국의 눈물', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-14 22:20:00', 0, 0, 'gaming', NULL, 'N', '2026-01-03 20:00:00', '2026-01-03 20:00:00'),
('클리어 - 페르소나 5 로열', '120시간 순삭', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-01 03:10:00', 0, 0, 'gaming', NULL, 'N', '2026-02-15 20:00:00', '2026-02-15 20:00:00'),
('클리어 - 호그와트 레거시', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-10 00:50:00', 0, 0, 'gaming', NULL, 'N', '2026-04-02 20:00:00', '2026-04-02 20:00:00'),
('클리어 - 스타듀밸리 4년차', '이건 클리어가 없는 게임', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-08 01:20:00', 0, 0, 'gaming', NULL, 'N', '2026-05-11 20:00:00', '2026-05-11 20:00:00'),
('클리어 - 사이버펑크 2077', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-18 02:40:00', 0, 0, 'gaming', NULL, 'N', '2026-06-09 20:00:00', '2026-06-09 20:00:00'),
('도전 중 - 세키로 무상비검', '9시간째 못 잡는 중', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,grinding', NULL, 'N', '2026-07-19 20:00:00', '2026-07-19 20:00:00'),
('도전 예정 - 데스 스트랜딩 2', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming', NULL, 'N', '2026-08-05 20:00:00', '2026-08-05 20:00:00');

-- =====================================================================
-- 시리즈: user 36 월간 지출 결산 (1~7월 완료, 8월 진행)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('1월 지출 결산 - 예산 지킴', '식비 32만', 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-01 10:00:00', 0, 0, 'finance', NULL, 'N', '2026-01-31 22:00:00', '2026-01-31 22:00:00'),
('2월 지출 결산 - 설날 지출 초과', '세뱃돈은 못 참지', 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-01 10:00:00', 0, 0, 'finance', NULL, 'N', '2026-02-28 22:00:00', '2026-02-28 22:00:00'),
('3월 지출 결산 - 예산 5% 절감', NULL, 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-01 10:00:00', 0, 0, 'finance', NULL, 'N', '2026-03-31 22:00:00', '2026-03-31 22:00:00'),
('4월 지출 결산 - 무지출 10일 효과', NULL, 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-01 10:00:00', 0, 0, 'finance', NULL, 'N', '2026-04-30 22:00:00', '2026-04-30 22:00:00'),
('5월 지출 결산 - 가정의달 적자', '어버이날+어린이날 콤보', 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-01 10:00:00', 0, 0, 'finance,family', NULL, 'N', '2026-05-31 22:00:00', '2026-05-31 22:00:00'),
('6월 지출 결산 - 흑자 전환', NULL, 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-01 10:00:00', 0, 0, 'finance', NULL, 'N', '2026-06-30 22:00:00', '2026-06-30 22:00:00'),
('7월 지출 결산 - 휴가비 방어 성공', NULL, 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-08-01 10:00:00', 0, 0, 'finance,travel', NULL, 'N', '2026-07-31 22:00:00', '2026-07-31 22:00:00'),
('8월 지출 결산 - 진행 중', NULL, 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-08-31', 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2026-08-01 08:00:00', '2026-08-01 08:00:00'),
('통신비 알뜰폰으로 갈아타기', '월 3만원 절약', 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-15 13:00:00', 0, 0, 'finance', NULL, 'N', '2026-02-05 12:00:00', '2026-02-05 12:00:00'),
('구독 서비스 3개 해지하기', 'OTT 정리', 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-03-10 21:00:00', 0, 0, 'finance', NULL, 'N', '2026-03-01 20:00:00', '2026-03-01 20:00:00'),
('보험 리모델링 상담 받기', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-09-30', 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2026-08-03 14:00:00', '2026-08-03 14:00:00'),
('앱테크 포인트 10만원 모으기', '만보기+설문', 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 6, 10, NULL, 0, 0, 'finance,grinding', NULL, 'N', '2026-05-20 07:30:00', '2026-05-20 07:30:00');

-- =====================================================================
-- 시리즈: user 37 세계 요리 도전
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('세계요리 - 이탈리아 라자냐', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-11 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-01-05 17:00:00', '2026-01-05 17:00:00'),
('세계요리 - 태국 팟타이', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-25 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-01-19 17:00:00', '2026-01-19 17:00:00'),
('세계요리 - 스페인 빠에야', '샤프란 비쌈...', 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-08 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-02-01 17:00:00', '2026-02-01 17:00:00'),
('세계요리 - 인도 버터치킨 커리', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-22 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-02-15 17:00:00', '2026-02-15 17:00:00'),
('세계요리 - 베트남 쌀국수', '육수 8시간', 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-08 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-03-01 17:00:00', '2026-03-01 17:00:00'),
('세계요리 - 멕시코 타코', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-22 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-03-15 17:00:00', '2026-03-15 17:00:00'),
('세계요리 - 일본 규동', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-05 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-03-29 17:00:00', '2026-03-29 17:00:00'),
('세계요리 - 프랑스 라따뚜이', '영화처럼', 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-19 19:00:00', 0, 0, 'cooking,culture', NULL, 'N', '2026-04-12 17:00:00', '2026-04-12 17:00:00'),
('세계요리 - 그리스 수블라키', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-03 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-04-26 17:00:00', '2026-04-26 17:00:00'),
('세계요리 - 터키 케밥', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-17 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-05-10 17:00:00', '2026-05-10 17:00:00'),
('세계요리 - 독일 슈니첼', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-31 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-05-24 17:00:00', '2026-05-24 17:00:00'),
('세계요리 - 중국 마파두부', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-14 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-06-07 17:00:00', '2026-06-07 17:00:00'),
('세계요리 - 모로코 쿠스쿠스', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-28 19:00:00', 0, 0, 'cooking', NULL, 'N', '2026-06-21 17:00:00', '2026-06-21 17:00:00'),
('세계요리 - 브라질 슈하스코', '고기파티', 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-12 19:00:00', 0, 0, 'cooking,socializing', NULL, 'N', '2026-07-05 17:00:00', '2026-07-05 17:00:00'),
('세계요리 - 러시아 보르시', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-16', 0, 1, NULL, 0, 0, 'cooking', NULL, 'N', '2026-08-02 17:00:00', '2026-08-02 17:00:00'),
('세계요리 - 페루 세비체', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-30', 0, 1, NULL, 0, 0, 'cooking', NULL, 'N', '2026-08-06 17:00:00', '2026-08-06 17:00:00');

-- =====================================================================
-- 시리즈: user 38 자격증 위시리스트
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('위시 - SQLD 자격증', NULL, 38, 70, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-22', 0, 1, NULL, 0, 0, 'certification', NULL, 'N', '2026-07-01 09:00:00', '2026-07-01 09:00:00'),
('위시 - 리눅스마스터 2급', NULL, 38, 70, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification', NULL, 'N', '2026-07-02 09:00:00', '2026-07-02 09:00:00'),
('위시 - 네트워크관리사 2급', NULL, 38, 70, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification', NULL, 'N', '2026-07-03 09:00:00', '2026-07-03 09:00:00'),
('위시 - 사회조사분석사 2급', NULL, 38, 71, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification,academics', NULL, 'N', '2026-07-05 09:00:00', '2026-07-05 09:00:00'),
('위시 - 텝스 400점', NULL, 38, 71, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'academics', NULL, 'N', '2026-07-08 09:00:00', '2026-07-08 09:00:00'),
('위시 - JLPT N2', '애니로 공부한 일본어 검증', 38, 71, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-06', 0, 1, NULL, 0, 0, 'academics,hobby', NULL, 'N', '2026-07-10 09:00:00', '2026-07-10 09:00:00'),
('위시 - 지게차운전기능사', '이건 진짜 쓸모있대', 38, 78, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification', NULL, 'N', '2026-07-12 09:00:00', '2026-07-12 09:00:00'),
('위시 - 드론 조종 자격증', NULL, 38, 78, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification,hobby', NULL, 'N', '2026-07-15 09:00:00', '2026-07-15 09:00:00'),
('위시 - 바리스타 2급', NULL, 38, 79, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification,cooking', NULL, 'N', '2026-07-18 09:00:00', '2026-07-18 09:00:00'),
('위시 - 스포츠지도사 보디빌딩', NULL, 38, 79, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification,workout', NULL, 'N', '2026-07-21 09:00:00', '2026-07-21 09:00:00'),
('취득 - ADsP 데이터분석 준전문가', '3주 벼락치기 성공', 38, 70, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-21 15:00:00', 0, 0, 'certification', NULL, 'N', '2026-05-30 09:00:00', '2026-05-30 09:00:00'),
('취득 - 워드프로세서', NULL, 38, 72, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-15 14:00:00', 0, 0, 'certification', NULL, 'N', '2026-01-25 09:00:00', '2026-01-25 09:00:00');

-- =====================================================================
-- 시리즈: user 40 산책 코스 개척
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('산책코스 - 서울숲 은행나무길', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-02 16:00:00', 0, 0, 'pet,travel', NULL, 'N', '2025-10-26 09:00:00', '2025-10-26 09:00:00'),
('산책코스 - 올림픽공원 들꽃마루', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-16 15:30:00', 0, 0, 'pet', NULL, 'N', '2025-11-09 09:00:00', '2025-11-09 09:00:00'),
('산책코스 - 하늘공원 억새밭', '강아지 인생샷 성공', 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-30 15:00:00', 0, 0, 'pet,hobby', NULL, 'N', '2025-11-23 09:00:00', '2025-11-23 09:00:00'),
('산책코스 - 경의선숲길', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-15 14:00:00', 0, 0, 'pet', NULL, 'N', '2026-03-08 09:00:00', '2026-03-08 09:00:00'),
('산책코스 - 석촌호수 벚꽃', '사람 많아서 아침 일찍', 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-05 08:00:00', 0, 0, 'pet,travel', NULL, 'N', '2026-03-29 09:00:00', '2026-03-29 09:00:00'),
('산책코스 - 북서울꿈의숲', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-19 15:20:00', 0, 0, 'pet', NULL, 'N', '2026-04-12 09:00:00', '2026-04-12 09:00:00'),
('산책코스 - 양재천 메타세쿼이아길', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-10 16:40:00', 0, 0, 'pet', NULL, 'N', '2026-05-03 09:00:00', '2026-05-03 09:00:00'),
('산책코스 - 월드컵공원 반려견 놀이터', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-24 15:10:00', 0, 0, 'pet,socializing', NULL, 'N', '2026-05-17 09:00:00', '2026-05-17 09:00:00'),
('산책코스 - 인천대공원 애견운동장', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-07 14:50:00', 0, 0, 'pet,travel', NULL, 'N', '2026-05-31 09:00:00', '2026-05-31 09:00:00'),
('산책코스 - 남산 둘레길 새벽 산책', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-21 06:30:00', 0, 0, 'pet,workout', NULL, 'N', '2026-06-14 09:00:00', '2026-06-14 09:00:00'),
('산책코스 - 한강 반포지구 야간 산책', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-05 21:30:00', 0, 0, 'pet', NULL, 'N', '2026-06-28 09:00:00', '2026-06-28 09:00:00'),
('산책코스 - 춘천 의암호 스카이워크', '첫 근교 원정', 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-23', 0, 1, NULL, 0, 0, 'pet,travel', NULL, 'N', '2026-08-02 09:00:00', '2026-08-02 09:00:00'),
('산책코스 - 파주 임진각 평화누리', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet,travel', NULL, 'N', '2026-08-06 09:00:00', '2026-08-06 09:00:00');

-- =====================================================================
-- 시리즈: user 42 주간 회고 (최근 12주)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('주간회고 5월 3주 - 사이드프로젝트 DB 설계', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-17 22:00:00', 0, 0, 'career,selfimprovement', NULL, 'N', '2026-05-11 09:00:00', '2026-05-11 09:00:00'),
('주간회고 5월 4주 - 블로그 2편 발행', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-24 22:00:00', 0, 0, 'career', NULL, 'N', '2026-05-18 09:00:00', '2026-05-18 09:00:00'),
('주간회고 5월 5주 - 번아웃 조짐, 휴식', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-31 22:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2026-05-25 09:00:00', '2026-05-25 09:00:00'),
('주간회고 6월 1주 - API 개발 완료', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-07 22:00:00', 0, 0, 'career', NULL, 'N', '2026-06-01 09:00:00', '2026-06-01 09:00:00'),
('주간회고 6월 2주 - 커피챗 2건', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-14 22:00:00', 0, 0, 'career,socializing', NULL, 'N', '2026-06-08 09:00:00', '2026-06-08 09:00:00'),
('주간회고 6월 3주 - 발표자료 초안', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-21 22:00:00', 0, 0, 'career', NULL, 'N', '2026-06-15 09:00:00', '2026-06-15 09:00:00'),
('주간회고 6월 4주 - 운동 병행 시작', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-28 22:00:00', 0, 0, 'selfimprovement,workout', NULL, 'N', '2026-06-22 09:00:00', '2026-06-22 09:00:00'),
('주간회고 7월 1주 - 앱 베타 배포', '드디어 배포!', 42, 81, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-05 22:00:00', 0, 0, 'career', NULL, 'N', '2026-06-29 09:00:00', '2026-06-29 09:00:00'),
('주간회고 7월 2주 - 피드백 반영', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-12 22:00:00', 0, 0, 'career', NULL, 'N', '2026-07-06 09:00:00', '2026-07-06 09:00:00'),
('주간회고 7월 3주 - 찬물샤워 완주', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-19 22:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2026-07-13 09:00:00', '2026-07-13 09:00:00'),
('주간회고 7월 4주 - 연봉협상 자료 조사', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-26 22:00:00', 0, 0, 'career,finance', NULL, 'N', '2026-07-20 09:00:00', '2026-07-20 09:00:00'),
('주간회고 8월 1주 - 진행 중', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-08-09', 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-08-03 09:00:00', '2026-08-03 09:00:00');

-- =====================================================================
-- 시리즈: user 41 뷰티/카페 신상 체험
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('신상 체험 - 성수 팝업스토어 3곳', NULL, 41, 58, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-03-16 18:00:00', 0, 0, 'flexing,culture', NULL, 'N', '2026-03-02 12:00:00', '2026-03-02 12:00:00'),
('신상 체험 - 한남동 편집숍 투어', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-06 17:30:00', 0, 0, 'flexing', NULL, 'N', '2026-03-30 12:00:00', '2026-03-30 12:00:00'),
('신상 체험 - 신상 립 5종 스와치', NULL, 41, 58, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 3, 5, NULL, 0, 0, 'beauty', NULL, 'N', '2026-06-01 20:00:00', '2026-06-01 20:00:00'),
('신상 체험 - 을지로 루프탑 카페', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-20 19:40:00', 0, 0, 'foodie,socializing', NULL, 'N', '2026-06-13 12:00:00', '2026-06-13 12:00:00'),
('신상 체험 - 여름 향수 시향 리스트', '5개 중 1픽 고르기', 41, 58, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'beauty,flexing', NULL, 'N', '2026-07-04 15:00:00', '2026-07-04 15:00:00'),
('신상 체험 - 압구정 헤어쇼 관람', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-14', 0, 1, NULL, 0, 0, 'beauty,culture', NULL, 'N', '2026-08-01 12:00:00', '2026-08-01 12:00:00'),
('올리브영 세일 참전 후 리뷰 쓰기', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-02 21:00:00', 0, 0, 'beauty,finance', NULL, 'N', '2026-05-28 12:00:00', '2026-05-28 12:00:00'),
('두피 케어 4주 프로그램', NULL, 41, 58, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 2, 4, NULL, 0, 0, 'beauty', NULL, 'N', '2026-07-20 10:00:00', '2026-07-20 10:00:00');

-- =====================================================================
-- 시리즈: user 43 명작 영화 도장깨기
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('명작 도장깨기 - 대부 1,2,3', NULL, 43, 82, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-01-18 23:50:00', 0, 0, 'culture', NULL, 'N', '2026-01-03 20:00:00', '2026-01-03 20:00:00'),
('명작 도장깨기 - 쇼생크 탈출', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-25 23:00:00', 0, 0, 'culture', NULL, 'N', '2026-01-19 20:00:00', '2026-01-19 20:00:00'),
('명작 도장깨기 - 시네마 천국', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-08 22:40:00', 0, 0, 'culture', NULL, 'N', '2026-02-01 20:00:00', '2026-02-01 20:00:00'),
('명작 도장깨기 - 12인의 성난 사람들', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-22 22:30:00', 0, 0, 'culture', NULL, 'N', '2026-02-15 20:00:00', '2026-02-15 20:00:00'),
('명작 도장깨기 - 인생은 아름다워', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-08 23:10:00', 0, 0, 'culture', NULL, 'N', '2026-03-01 20:00:00', '2026-03-01 20:00:00'),
('명작 도장깨기 - 세븐', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-22 23:20:00', 0, 0, 'culture', NULL, 'N', '2026-03-15 20:00:00', '2026-03-15 20:00:00'),
('명작 도장깨기 - 2001 스페이스 오디세이', '숙제 같은 영화', 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-05 23:40:00', 0, 0, 'culture', NULL, 'N', '2026-03-29 20:00:00', '2026-03-29 20:00:00'),
('명작 도장깨기 - 올드보이 재개봉판', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-19 22:50:00', 0, 0, 'culture', NULL, 'N', '2026-04-12 20:00:00', '2026-04-12 20:00:00'),
('명작 도장깨기 - 라라랜드 재관람', '재즈바 장면 최고', 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-03 23:00:00', 0, 0, 'culture', NULL, 'N', '2026-04-26 20:00:00', '2026-04-26 20:00:00'),
('명작 도장깨기 - 헤어질 결심', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-17 23:30:00', 0, 0, 'culture', NULL, 'N', '2026-05-10 20:00:00', '2026-05-10 20:00:00'),
('명작 도장깨기 - 문라이트', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-31 22:20:00', 0, 0, 'culture', NULL, 'N', '2026-05-24 20:00:00', '2026-05-24 20:00:00'),
('명작 도장깨기 - 매드맥스 분노의 도로', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-14 23:50:00', 0, 0, 'culture', NULL, 'N', '2026-06-07 20:00:00', '2026-06-07 20:00:00'),
('명작 도장깨기 - 기생충 흑백판', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-28 23:10:00', 0, 0, 'culture', NULL, 'N', '2026-06-21 20:00:00', '2026-06-21 20:00:00'),
('명작 도장깨기 - 스탠 바이 미', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-12 22:00:00', 0, 0, 'culture', NULL, 'N', '2026-07-05 20:00:00', '2026-07-05 20:00:00'),
('명작 도장깨기 - 노인을 위한 나라는 없다', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture', NULL, 'N', '2026-08-02 20:00:00', '2026-08-02 20:00:00'),
('명작 도장깨기 - 8월의 크리스마스', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture', NULL, 'N', '2026-08-06 20:00:00', '2026-08-06 20:00:00');

-- =====================================================================
-- 시리즈: user 44 전국 핫플 모임장소 답사
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('핫플 답사 - 성수 대림창고', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-14 19:00:00', 0, 0, 'socializing,foodie', NULL, 'N', '2026-02-07 12:00:00', '2026-02-07 12:00:00'),
('핫플 답사 - 연남동 보드게임카페', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-28 20:30:00', 0, 0, 'socializing,gaming', NULL, 'N', '2026-02-21 12:00:00', '2026-02-21 12:00:00'),
('핫플 답사 - 익선동 한옥 술집', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-14 21:00:00', 0, 0, 'socializing,foodie', NULL, 'N', '2026-03-07 12:00:00', '2026-03-07 12:00:00'),
('핫플 답사 - 문래동 철공소 카페', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-28 18:40:00', 0, 0, 'socializing,culture', NULL, 'N', '2026-03-21 12:00:00', '2026-03-21 12:00:00'),
('핫플 답사 - 해방촌 루프탑바', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-11 21:30:00', 0, 0, 'socializing', NULL, 'N', '2026-04-04 12:00:00', '2026-04-04 12:00:00'),
('핫플 답사 - 망원동 티하우스', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-25 16:20:00', 0, 0, 'socializing,foodie', NULL, 'N', '2026-04-18 12:00:00', '2026-04-18 12:00:00'),
('핫플 답사 - 광장시장 야시장 투어', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-09 20:50:00', 0, 0, 'socializing,foodie', NULL, 'N', '2026-05-02 12:00:00', '2026-05-02 12:00:00'),
('핫플 답사 - 서촌 골목 산책 코스', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-23 15:30:00', 0, 0, 'socializing,travel', NULL, 'N', '2026-05-16 12:00:00', '2026-05-16 12:00:00'),
('핫플 답사 - 판교 신상 브런치', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-06 11:30:00', 0, 0, 'socializing,foodie', NULL, 'N', '2026-05-30 12:00:00', '2026-05-30 12:00:00'),
('핫플 답사 - 부산 전포 카페거리', '모임 MT 후보지', 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-05', 0, 1, NULL, 0, 0, 'socializing,travel', NULL, 'N', '2026-08-01 12:00:00', '2026-08-01 12:00:00'),
('핫플 답사 - 제주 애월 선셋바', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'socializing,travel', NULL, 'N', '2026-08-05 12:00:00', '2026-08-05 12:00:00');

-- =====================================================================
-- 시리즈: user 45 월간 봉사 기록
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('1월 봉사 - 연탄 나르기 200장', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-17 16:00:00', 0, 0, 'volunteering', NULL, 'N', '2026-01-10 08:00:00', '2026-01-10 08:00:00'),
('2월 봉사 - 급식소 배식 보조', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-21 14:00:00', 0, 0, 'volunteering', NULL, 'N', '2026-02-14 08:00:00', '2026-02-14 08:00:00'),
('3월 봉사 - 유기견 보호소 청소', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-21 15:30:00', 0, 0, 'volunteering,pet', NULL, 'N', '2026-03-14 08:00:00', '2026-03-14 08:00:00'),
('4월 봉사 - 벚꽃축제 환경정화', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-18 17:00:00', 0, 0, 'volunteering', NULL, 'N', '2026-04-11 08:00:00', '2026-04-11 08:00:00'),
('5월 봉사 - 어린이날 놀이 도우미', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-05 16:30:00', 0, 0, 'volunteering,parenting', NULL, 'N', '2026-05-01 08:00:00', '2026-05-01 08:00:00'),
('6월 봉사 - 헌혈 + 헌혈증 기부', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-20 13:00:00', 0, 0, 'volunteering', NULL, 'N', '2026-06-13 08:00:00', '2026-06-13 08:00:00'),
('7월 봉사 - 폭염 취약가구 생수 전달', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-18 15:00:00', 0, 0, 'volunteering', NULL, 'N', '2026-07-11 08:00:00', '2026-07-11 08:00:00'),
('8월 봉사 - 해변 플로깅 원정', '양양으로', 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-22', 0, 1, NULL, 0, 0, 'volunteering,travel', NULL, 'N', '2026-08-03 08:00:00', '2026-08-03 08:00:00');

-- =====================================================================
-- 시리즈: user 47 전공/교양 정복 + 대학생활
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('강의 정복 - 자료구조 A+', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-25 17:00:00', 0, 0, 'academics', NULL, 'N', '2026-03-02 09:00:00', '2026-03-02 09:00:00'),
('강의 정복 - 운영체제 재수강 탈출', '작년의 한', 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-25 17:10:00', 0, 0, 'academics,grinding', NULL, 'N', '2026-03-02 09:05:00', '2026-03-02 09:05:00'),
('강의 정복 - 교양 와인의 이해', '꿀교양 소문 검증', 47, 89, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-25 17:20:00', 0, 0, 'academics,foodie', NULL, 'N', '2026-03-02 09:10:00', '2026-03-02 09:10:00'),
('강의 정복 - 2학기 알고리즘', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'academics', NULL, 'N', '2026-08-03 09:00:00', '2026-08-03 09:00:00'),
('대학생활 - 밤샘 조별과제 없이 한 학기', NULL, 47, 89, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'academics,selfimprovement', NULL, 'N', '2026-08-04 09:00:00', '2026-08-04 09:00:00'),
('대학생활 - 도서관 시험기간 자리 양보 이벤트', '선행 미션', 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-15 10:00:00', 0, 0, 'volunteering,academics', NULL, 'N', '2026-06-10 09:00:00', '2026-06-10 09:00:00'),
('대학생활 - 총MT 장기자랑 1등', NULL, 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-28 23:00:00', 0, 0, 'socializing,culture', NULL, 'N', '2026-03-20 09:00:00', '2026-03-20 09:00:00'),
('대학생활 - 창업동아리 아이디어톤 참가', NULL, 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-14', 0, 1, NULL, 0, 0, 'career,socializing', NULL, 'N', '2026-07-25 09:00:00', '2026-07-25 09:00:00'),
('대학생활 - 방학 때 지방 국토대장정', NULL, 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2027-01-31', 0, 1, NULL, 0, 0, 'travel,workout', NULL, 'N', '2026-08-05 09:00:00', '2026-08-05 09:00:00'),
('대학생활 - 첫 해외 배낭여행 (방콕)', '알바비 모아서', 47, 89, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2027-02-15', 0, 1, NULL, 0, 0, 'travel,finance', NULL, 'N', '2026-08-06 09:00:00', '2026-08-06 09:00:00');

-- =====================================================================
-- 시리즈: user 48 취미 위시리스트
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('취미 위시 - 유리공예 체험', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-07-01 10:00:00', '2026-07-01 10:00:00'),
('취미 위시 - 실버링 만들기', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-07-02 10:00:00', '2026-07-02 10:00:00'),
('취미 위시 - 소이캔들 클래스', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-07-03 10:00:00', '2026-07-03 10:00:00'),
('취미 위시 - 라탄 바구니 짜기', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-07-05 10:00:00', '2026-07-05 10:00:00'),
('취미 위시 - 우쿨렐레 입문', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-07-08 10:00:00', '2026-07-08 10:00:00'),
('취미 위시 - 포슬린 페인팅', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-07-10 10:00:00', '2026-07-10 10:00:00'),
('취미 완료 - 마크라메 월행잉', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-18 16:00:00', 0, 0, 'hobby', NULL, 'N', '2026-05-02 10:00:00', '2026-05-02 10:00:00'),
('취미 완료 - 티 블렌딩 클래스', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-09 15:00:00', 0, 0, 'hobby,foodie', NULL, 'N', '2026-03-01 10:00:00', '2026-03-01 10:00:00'),
('취미 완료 - 석고방향제 만들기', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-31 14:30:00', 0, 0, 'hobby', NULL, 'N', '2026-01-20 10:00:00', '2026-01-20 10:00:00'),
('취미 완료 - 클레이 미니어처', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-21 17:20:00', 0, 0, 'hobby', NULL, 'N', '2026-04-10 10:00:00', '2026-04-10 10:00:00');

-- =====================================================================
-- 시리즈: user 49 주간 체중/식단 기록
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('주간 기록 6월 1주 - 식단 6/7 성공', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 6, 6, '2026-06-07 21:00:00', 0, 0, 'diet', NULL, 'N', '2026-06-01 08:00:00', '2026-06-01 08:00:00'),
('주간 기록 6월 2주 - 회식 2번 방어', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 5, 5, '2026-06-14 21:00:00', 0, 0, 'diet', NULL, 'N', '2026-06-08 08:00:00', '2026-06-08 08:00:00'),
('주간 기록 6월 3주 - 정체기 시작', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 6, 6, '2026-06-21 21:00:00', 0, 0, 'diet', NULL, 'N', '2026-06-15 08:00:00', '2026-06-15 08:00:00'),
('주간 기록 6월 4주 - 치팅 없이 통과', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 7, 7, '2026-06-28 21:00:00', 0, 0, 'diet,grinding', NULL, 'N', '2026-06-22 08:00:00', '2026-06-22 08:00:00'),
('주간 기록 7월 1주 - 정체기 탈출', '-0.8kg', 49, 96, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 6, 6, '2026-07-05 21:00:00', 0, 0, 'diet', NULL, 'N', '2026-06-29 08:00:00', '2026-06-29 08:00:00'),
('주간 기록 7월 2주 - 휴가 중 유지', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 5, 5, '2026-07-12 21:00:00', 0, 0, 'diet,travel', NULL, 'N', '2026-07-06 08:00:00', '2026-07-06 08:00:00'),
('주간 기록 7월 3주 - 운동량 늘림', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 6, 6, '2026-07-19 21:00:00', 0, 0, 'diet,workout', NULL, 'N', '2026-07-13 08:00:00', '2026-07-13 08:00:00'),
('주간 기록 7월 4주 - 목표까지 2kg', NULL, 49, 96, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 6, 6, '2026-07-26 21:00:00', 0, 0, 'diet', NULL, 'N', '2026-07-20 08:00:00', '2026-07-20 08:00:00'),
('주간 기록 8월 1주 - 진행 중', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-08-09', 4, 6, NULL, 0, 0, 'diet', NULL, 'N', '2026-08-03 08:00:00', '2026-08-03 08:00:00');

-- =====================================================================
-- 시리즈: user 50 인생 버킷 100선 (다양한 카테고리 위시)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('인생버킷 - 사막에서 별 보기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,flexing', NULL, 'N', '2026-01-02 12:00:00', '2026-01-02 12:00:00'),
('인생버킷 - 열기구 타기', '터키 카파도키아', 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-01-02 12:05:00', '2026-01-02 12:05:00'),
('인생버킷 - 산티아고 순례길 일부 구간', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,workout', NULL, 'N', '2026-01-02 12:10:00', '2026-01-02 12:10:00'),
('인생버킷 - 스쿠버다이빙 자격증', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'certification,hobby', NULL, 'N', '2026-01-02 12:15:00', '2026-01-02 12:15:00'),
('인생버킷 - 마라톤 풀코스', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,grinding', NULL, 'N', '2026-01-02 12:20:00', '2026-01-02 12:20:00'),
('인생버킷 - 내 가게 차리기', '작은 카페라도', 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'career,finance', NULL, 'N', '2026-01-02 12:25:00', '2026-01-02 12:25:00'),
('인생버킷 - 오케스트라 공연 백스테이지', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture', NULL, 'N', '2026-01-02 12:30:00', '2026-01-02 12:30:00'),
('인생버킷 - 가족 다 같이 세계여행', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'family,travel', NULL, 'N', '2026-01-02 12:35:00', '2026-01-02 12:35:00'),
('인생버킷 - 책 1000권 읽기', '현재 200권쯤', 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 214, 1000, NULL, 0, 0, 'reading,grinding', NULL, 'N', '2026-01-02 12:40:00', '2026-01-02 12:40:00'),
('인생버킷 - 극한 스포츠 하나 마스터', '서핑 유력', 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,hobby', NULL, 'N', '2026-01-02 12:45:00', '2026-01-02 12:45:00'),
('인생버킷 - 유스호스텔에서 외국인 친구 사귀기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-15 20:00:00', 0, 0, 'socializing,travel', NULL, 'N', '2026-01-02 12:50:00', '2026-01-02 12:50:00'),
('인생버킷 - 피아노로 한 곡 완주', '캐논 변주곡', 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,culture', NULL, 'N', '2026-01-02 12:55:00', '2026-01-02 12:55:00'),
('인생버킷 - 헌혈 30회', NULL, 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 9, 30, NULL, 0, 0, 'volunteering', NULL, 'N', '2026-01-02 13:00:00', '2026-01-02 13:00:00'),
('인생버킷 - 어학연수 없이 영어 프리토킹', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'academics,grinding', NULL, 'N', '2026-01-02 13:05:00', '2026-01-02 13:05:00'),
('인생버킷 - 반려식물 정글 만들기', '거실을 숲으로', 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-01-02 13:10:00', '2026-01-02 13:10:00'),
('인생버킷 - 시골 빈집 고쳐서 별장 만들기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,hobby', NULL, 'N', '2026-01-02 13:15:00', '2026-01-02 13:15:00'),
('인생버킷 - 오미자밭 하루 농활', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-11 17:00:00', 0, 0, 'volunteering,travel', NULL, 'N', '2025-09-20 13:20:00', '2025-09-20 13:20:00'),
('인생버킷 - 밤바다에서 폭죽 쏘기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-27 22:30:00', 0, 0, 'hobby,socializing', NULL, 'N', '2025-09-20 13:25:00', '2025-09-20 13:25:00'),
('인생버킷 - 첫차 타고 아무 역에서 내리기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-21 06:20:00', 0, 0, 'travel,hobby', NULL, 'N', '2026-01-02 13:30:00', '2026-01-02 13:30:00'),
('인생버킷 - 무인도 캠핑', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,hobby', NULL, 'N', '2026-01-02 13:35:00', '2026-01-02 13:35:00');

-- =====================================================================
-- 추가 볼륨: user 30 해외 위시 + 국내 맛집
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('해외 위시 - 스위스 융프라우', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-01-06 12:00:00', '2026-01-06 12:00:00'),
('해외 위시 - 하와이 스노클링', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-01-06 12:05:00', '2026-01-06 12:05:00'),
('해외 위시 - 뉴욕 브로드웨이 뮤지컬', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,culture', NULL, 'N', '2026-01-06 12:10:00', '2026-01-06 12:10:00'),
('해외 위시 - 이집트 피라미드', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-01-06 12:15:00', '2026-01-06 12:15:00'),
('해외 위시 - 몰디브 수상가옥', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,flexing', NULL, 'N', '2026-01-06 12:20:00', '2026-01-06 12:20:00'),
('해외 위시 - 호주 그레이트오션로드', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-01-06 12:25:00', '2026-01-06 12:25:00'),
('해외 위시 - 핀란드 산타마을', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-01-06 12:30:00', '2026-01-06 12:30:00'),
('해외 위시 - 베트남 다낭 바나힐', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-08 15:00:00', 0, 0, 'travel', NULL, 'N', '2025-09-06 12:35:00', '2025-09-06 12:35:00'),
('해외 위시 - 대만 야시장 투어', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-11 21:00:00', 0, 0, 'travel,foodie', NULL, 'N', '2026-01-06 12:40:00', '2026-01-06 12:40:00'),
('해외 위시 - 싱가포르 마리나베이 수영장', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,flexing', NULL, 'N', '2026-01-06 12:45:00', '2026-01-06 12:45:00'),
('맛집 - 을지로 노포 골목 정복', NULL, 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 5, 12, NULL, 0, 0, 'foodie', NULL, 'N', '2026-02-16 12:50:00', '2026-02-16 12:50:00'),
('맛집 - 제주 흑돼지 3대 맛집', NULL, 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2026-05-30 19:00:00', 0, 0, 'foodie,travel', NULL, 'N', '2026-02-16 12:55:00', '2026-02-16 12:55:00'),
('맛집 - 춘천 닭갈비 원조집', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-14 13:00:00', 0, 0, 'foodie,travel', NULL, 'N', '2026-02-16 13:00:00', '2026-02-16 13:00:00'),
('맛집 - 속초 물회 도장깨기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-04 12:30:00', 0, 0, 'foodie', NULL, 'N', '2026-02-16 13:05:00', '2026-02-16 13:05:00'),
('맛집 - 전국 냉면 5대 계보 비교', NULL, 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'foodie', NULL, 'N', '2026-02-16 13:10:00', '2026-02-16 13:10:00'),
('맛집 - 부산 돼지국밥 투어', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'foodie,travel', NULL, 'N', '2026-02-16 13:15:00', '2026-02-16 13:15:00'),
('맛집 - 노포 다방 커피 마셔보기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'foodie,culture', NULL, 'N', '2026-02-16 13:20:00', '2026-02-16 13:20:00'),
('맛집 - 시장 호떡 전국 비교', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'foodie', NULL, 'N', '2026-02-16 13:25:00', '2026-02-16 13:25:00');

-- =====================================================================
-- 추가 볼륨: user 32 월별 PR 기록 + user 49 홈트
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('1월 PR - 데드리프트 120kg', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-29 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-01-03 19:00:00', '2026-01-03 19:00:00'),
('2월 PR - 벤치프레스 85kg', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-26 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-02-02 19:00:00', '2026-02-02 19:00:00'),
('3월 PR - 스쿼트 90kg', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-27 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-03-02 19:00:00', '2026-03-02 19:00:00'),
('4월 PR - 5km 22분대', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-24 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-04-01 19:00:00', '2026-04-01 19:00:00'),
('5월 PR - 풀업 12개', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-28 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-05-01 19:00:00', '2026-05-01 19:00:00'),
('6월 PR - 벤치 90kg 실패, 유지', '다음 달 재도전', 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,grinding', NULL, 'N', '2026-06-01 19:00:00', '2026-06-01 19:00:00'),
('7월 PR - 데드 130kg', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-30 20:00:00', 0, 0, 'workout', NULL, 'N', '2026-07-01 19:00:00', '2026-07-01 19:00:00'),
('8월 PR - 스쿼트 100kg 도전', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-29', 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-08-01 19:00:00', '2026-08-01 19:00:00'),
('홈트 - 스트레칭 루틴 만들기', NULL, 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-10 21:00:00', 0, 0, 'workout', NULL, 'N', '2026-02-01 20:00:00', '2026-02-01 20:00:00'),
('홈트 - 층간소음 없는 유산소 찾기', '무릎 안 아픈 걸로', 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-20 21:00:00', 0, 0, 'workout,diet', NULL, 'N', '2026-02-11 20:00:00', '2026-02-11 20:00:00'),
('홈트 - 요가매트 위 코어 4주', NULL, 49, 96, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 4, 4, '2026-03-25 21:00:00', 0, 0, 'workout', NULL, 'N', '2026-03-01 20:00:00', '2026-03-01 20:00:00'),
('홈트 - 폼롤러 마사지 습관화', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-04-05 20:00:00', '2026-04-05 20:00:00'),
('홈트 - 버피 100개 무정지', '현재 40개 한계', 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,grinding', NULL, 'N', '2026-05-10 20:00:00', '2026-05-10 20:00:00'),
('홈트 - 아침 스트레칭 30일 연속', NULL, 49, 96, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 22, 30, NULL, 0, 0, 'workout,selfimprovement', NULL, 'N', '2026-07-12 07:00:00', '2026-07-12 07:00:00');

-- =====================================================================
-- 추가 볼륨: user 33 독서노트 / user 34 놀이 아이디어
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('독서노트 - 인상 깊은 문장 100개 수집', NULL, 33, 50, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 67, 100, NULL, 0, 0, 'reading,hobby', NULL, 'N', '2026-01-20 21:00:00', '2026-01-20 21:00:00'),
('독서노트 - 한 작가 전작주의 도전 (김초엽)', NULL, 33, 50, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 4, 7, NULL, 0, 0, 'reading', NULL, 'N', '2026-03-11 21:00:00', '2026-03-11 21:00:00'),
('독서노트 - 노벨문학상 수상작 5권', NULL, 33, 50, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'reading,culture', NULL, 'N', '2026-04-02 21:00:00', '2026-04-02 21:00:00'),
('독서노트 - 서점 큐레이션 따라 읽기', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading', NULL, 'N', '2026-05-21 21:00:00', '2026-05-21 21:00:00'),
('독서노트 - 저자 강연 3회 참석', NULL, 33, 50, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 0, 'reading,culture', NULL, 'N', '2026-06-09 21:00:00', '2026-06-09 21:00:00'),
('독서노트 - 책 물물교환 모임 참여', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-19 15:00:00', 0, 0, 'reading,socializing', NULL, 'N', '2026-07-01 21:00:00', '2026-07-01 21:00:00'),
('놀이 - 촉감놀이 재료 10가지 시도', NULL, 34, 51, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 6, 10, NULL, 0, 0, 'parenting', NULL, 'N', '2026-04-14 10:00:00', '2026-04-14 10:00:00'),
('놀이 - 집에서 볼풀장 만들기', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-11 15:00:00', 0, 0, 'parenting,hobby', NULL, 'N', '2026-05-02 10:00:00', '2026-05-02 10:00:00'),
('놀이 - 아기 수영장 데뷔', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-13 11:00:00', 0, 0, 'parenting,workout', NULL, 'N', '2026-07-01 10:00:00', '2026-07-01 10:00:00'),
('놀이 - 그림책 잠자리 독서 100일', NULL, 34, 51, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 61, 100, NULL, 0, 0, 'parenting,reading', NULL, 'N', '2026-06-08 21:00:00', '2026-06-08 21:00:00'),
('놀이 - 문화센터 오감놀이 등록', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-05 11:00:00', 0, 0, 'parenting', NULL, 'N', '2026-02-25 10:00:00', '2026-02-25 10:00:00'),
('놀이 - 아빠표 주말 놀이 정착시키기', '남편 교육 중', 34, 51, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'parenting,family', NULL, 'N', '2026-07-27 10:00:00', '2026-07-27 10:00:00');

-- =====================================================================
-- 추가 볼륨: user 35 스팀 백로그 / user 36 절약 미션
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('백로그 - 하데스 2', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming', NULL, 'N', '2026-02-03 20:00:00', '2026-02-03 20:00:00'),
('백로그 - 홀로우 나이트 실크송', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming', NULL, 'N', '2026-02-03 20:05:00', '2026-02-03 20:05:00'),
('백로그 - 디스코 엘리시움', '텍스트 폭탄이라던데', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,reading', NULL, 'N', '2026-02-03 20:10:00', '2026-02-03 20:10:00'),
('백로그 - 아우터 와일즈', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-15 02:00:00', 0, 0, 'gaming', NULL, 'N', '2026-02-03 20:15:00', '2026-02-03 20:15:00'),
('백로그 - 포탈 1+2 재클리어', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 2, 2, '2026-04-20 01:30:00', 0, 0, 'gaming', NULL, 'N', '2026-02-03 20:20:00', '2026-02-03 20:20:00'),
('백로그 - 팩토리오 우주 진출', '시간 도둑 주의', 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,grinding', NULL, 'N', '2026-02-03 20:25:00', '2026-02-03 20:25:00'),
('절약 - 편의점 출입 금지 2주', NULL, 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 14, 14, '2026-03-21 22:00:00', 0, 0, 'finance,grinding', NULL, 'N', '2026-03-07 08:00:00', '2026-03-07 08:00:00'),
('절약 - 배달앱 삭제 한 달', '요리 실력 상승 부작용', 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 30, 30, '2026-05-14 22:00:00', 0, 0, 'finance,cooking', NULL, 'N', '2026-04-14 08:00:00', '2026-04-14 08:00:00'),
('절약 - 대중교통만 이용 한 달', NULL, 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 17, 30, NULL, 0, 0, 'finance', NULL, 'N', '2026-07-20 08:00:00', '2026-07-20 08:00:00'),
('절약 - 냉장고 파먹기 1주일', NULL, 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 7, 7, '2026-06-15 21:00:00', 0, 0, 'finance,cooking', NULL, 'N', '2026-06-08 08:00:00', '2026-06-08 08:00:00'),
('절약 - 커피 홈카페로 전환', '원두값도 아끼면 더 좋고', 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,hobby', NULL, 'N', '2026-07-02 08:00:00', '2026-07-02 08:00:00'),
('절약 - 만보기 앱으로 걸어서 포인트', NULL, 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,workout', NULL, 'N', '2026-07-25 08:00:00', '2026-07-25 08:00:00');

-- =====================================================================
-- 추가 볼륨: 37 베이킹 / 38 모의고사 / 40 훈련 미션 / 41 스킨케어
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('베이킹 - 바스크 치즈케이크', NULL, 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-31 16:00:00', 0, 0, 'cooking', NULL, 'N', '2026-01-24 10:00:00', '2026-01-24 10:00:00'),
('베이킹 - 소금빵 겉바속촉 달성', '3트만에 성공', 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-21 15:30:00', 0, 0, 'cooking,grinding', NULL, 'N', '2026-02-07 10:00:00', '2026-02-07 10:00:00'),
('베이킹 - 크루아상 결 내기', '버터 접기 지옥', 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking', NULL, 'N', '2026-03-14 10:00:00', '2026-03-14 10:00:00'),
('베이킹 - 우리 아이 돌케이크 직접 만들기', '주문 대신 셀프', 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-10', 0, 1, NULL, 0, 0, 'cooking,family', NULL, 'N', '2026-07-14 10:00:00', '2026-07-14 10:00:00'),
('베이킹 - 비건 쿠키 상품화 테스트', NULL, 37, 59, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,career', NULL, 'N', '2026-06-21 10:00:00', '2026-06-21 10:00:00'),
('베이킹 - 마들렌 배꼽 만들기', NULL, 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-25 14:00:00', 0, 0, 'cooking', NULL, 'N', '2026-04-18 10:00:00', '2026-04-18 10:00:00'),
('모의고사 - 정처기 실기 1회차 55점', '커트라인까지 5점', 38, 70, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-05 18:00:00', 0, 0, 'certification', NULL, 'N', '2026-07-05 09:00:00', '2026-07-05 09:00:00'),
('모의고사 - 정처기 실기 2회차 61점', '합격권 진입', 38, 70, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-19 18:00:00', 0, 0, 'certification', NULL, 'N', '2026-07-19 09:00:00', '2026-07-19 09:00:00'),
('모의고사 - 정처기 실기 3회차 목표 70점', NULL, 38, 70, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-08-16', 0, 1, NULL, 0, 0, 'certification', NULL, 'N', '2026-08-02 09:00:00', '2026-08-02 09:00:00'),
('모의고사 - 토익 파트5 오답노트 정리', NULL, 38, 71, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'academics', NULL, 'N', '2026-07-28 09:00:00', '2026-07-28 09:00:00'),
('훈련 미션 - 앉아 기다려 마스터', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-28 17:00:00', 0, 0, 'pet', NULL, 'N', '2026-02-10 09:00:00', '2026-02-10 09:00:00'),
('훈련 미션 - 짖음 컨트롤 훈련 4주', NULL, 40, 57, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 4, 4, '2026-04-10 18:00:00', 0, 0, 'pet,study', NULL, 'N', '2026-03-10 09:00:00', '2026-03-10 09:00:00'),
('훈련 미션 - 하네스 산책 적응', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-15 17:30:00', 0, 0, 'pet', NULL, 'N', '2026-05-01 09:00:00', '2026-05-01 09:00:00'),
('훈련 미션 - 분리불안 30분 견디기', '카메라로 확인 중', 40, 57, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet,grinding', NULL, 'N', '2026-06-20 09:00:00', '2026-06-20 09:00:00'),
('훈련 미션 - 노즈워크 장난감 만들기', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet,hobby', NULL, 'N', '2026-07-22 09:00:00', '2026-07-22 09:00:00'),
('스킨케어 - 아침 저녁 루틴 정착 30일', NULL, 41, 58, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 30, 30, '2026-02-28 23:00:00', 0, 0, 'beauty,selfimprovement', NULL, 'N', '2026-01-29 22:00:00', '2026-01-29 22:00:00'),
('스킨케어 - 선크림 매일 바르기 여름 완주', NULL, 41, 58, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-31', 39, 92, NULL, 0, 0, 'beauty', NULL, 'N', '2026-06-01 08:00:00', '2026-06-01 08:00:00'),
('스킨케어 - 피부 상태 사진 기록 8주', NULL, 41, 58, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 5, 8, NULL, 0, 0, 'beauty', NULL, 'N', '2026-07-05 22:00:00', '2026-07-05 22:00:00'),
('스킨케어 - 미용 시술 공부하고 결정하기', '충동 시술 금지', 41, 58, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty,study', NULL, 'N', '2026-07-30 22:00:00', '2026-07-30 22:00:00');

-- =====================================================================
-- 추가 볼륨: 42 알고리즘 / 43 공연 기록 / 44 모임 이벤트 / 45 기부 위시
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('알고리즘 - 백준 골드 승급', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-30 23:00:00', 0, 0, 'study,career', NULL, 'N', '2026-01-15 22:00:00', '2026-01-15 22:00:00'),
('알고리즘 - 하루 1문제 100일', NULL, 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 71, 100, NULL, 0, 0, 'study,grinding', NULL, 'N', '2026-05-01 22:00:00', '2026-05-01 22:00:00'),
('알고리즘 - 프로그래머스 레벨3 정복', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'study', NULL, 'N', '2026-06-15 22:00:00', '2026-06-15 22:00:00'),
('알고리즘 - 코테 스터디 모의면접 5회', NULL, 42, 81, 'CHALLENGE', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 3, 5, NULL, 0, 0, 'study,career', NULL, 'N', '2026-07-01 22:00:00', '2026-07-01 22:00:00'),
('공연 기록 - 재즈 페스티벌 후기', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-25 23:00:00', 0, 0, 'culture', NULL, 'N', '2026-05-25 10:00:00', '2026-05-25 10:00:00'),
('공연 기록 - 국악 크로스오버 공연', '생각보다 힙함', 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-13 22:00:00', 0, 0, 'culture', NULL, 'N', '2026-06-13 10:00:00', '2026-06-13 10:00:00'),
('공연 기록 - 심야 재즈바 라이브', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-11 23:50:00', 0, 0, 'culture,socializing', NULL, 'N', '2026-07-11 10:00:00', '2026-07-11 10:00:00'),
('공연 기록 - 발레 호두까기인형 예매', '연말 마무리 공연', 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-24', 0, 1, NULL, 0, 0, 'culture', NULL, 'N', '2026-08-01 10:00:00', '2026-08-01 10:00:00'),
('모임 - 한강 치맥 번개 성사', NULL, 44, 86, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-19 20:00:00', 0, 0, 'socializing,foodie', NULL, 'N', '2026-06-15 12:00:00', '2026-06-15 12:00:00'),
('모임 - 독서모임 체험 참석', NULL, 44, 86, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-12 16:00:00', 0, 0, 'socializing,reading', NULL, 'N', '2026-04-05 12:00:00', '2026-04-05 12:00:00'),
('모임 - 러닝크루 게스트 참여', '운동 반 사교 반', 44, 86, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-16 08:00:00', 0, 0, 'socializing,workout', NULL, 'N', '2026-05-09 12:00:00', '2026-05-09 12:00:00'),
('모임 - 연말 홈파티 호스트', '올해의 피날레', 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-30', 0, 1, NULL, 0, 0, 'socializing', NULL, 'N', '2026-08-03 12:00:00', '2026-08-03 12:00:00'),
('기부 위시 - 소아병동 장난감 기부', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering', NULL, 'N', '2026-07-02 08:00:00', '2026-07-02 08:00:00'),
('기부 위시 - 머리카락 기부 (25cm)', '기르는 중 18cm', 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering,beauty', NULL, 'N', '2026-01-15 08:00:00', '2026-01-15 08:00:00'),
('기부 위시 - 재난 구호 키트 만들기 참여', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering', NULL, 'N', '2026-07-08 08:00:00', '2026-07-08 08:00:00'),
('기부 위시 - 나눔 냉장고 정기 채우기', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering', NULL, 'N', '2026-07-20 08:00:00', '2026-07-20 08:00:00');

-- =====================================================================
-- 추가 볼륨: 47 대외활동 / 48 전시·공방 / 50 도전 시리즈
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('대외활동 - 서포터즈 1기 활동 수료', NULL, 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-30 18:00:00', 0, 0, 'career,socializing', NULL, 'N', '2026-03-05 09:00:00', '2026-03-05 09:00:00'),
('대외활동 - 해커톤 첫 출전', '입상 못해도 완주', 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-18 21:00:00', 0, 0, 'career,study', NULL, 'N', '2026-05-01 09:00:00', '2026-05-01 09:00:00'),
('대외활동 - 학과 멘토링 멘토 지원', NULL, 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-15', 0, 1, NULL, 0, 0, 'volunteering,academics', NULL, 'N', '2026-08-01 09:00:00', '2026-08-01 09:00:00'),
('대외활동 - 교내 기자단 기사 5건', NULL, 47, 90, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'career,hobby', NULL, 'N', '2026-06-10 09:00:00', '2026-06-10 09:00:00'),
('전시 - 이머시브 미디어아트 관람', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-14 16:00:00', 0, 0, 'culture,hobby', NULL, 'N', '2026-02-07 10:00:00', '2026-02-07 10:00:00'),
('전시 - 공예 비엔날레 다녀오기', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-30', 0, 1, NULL, 0, 0, 'culture,hobby', NULL, 'N', '2026-08-02 10:00:00', '2026-08-02 10:00:00'),
('공방 - 가죽 카드지갑 만들어 선물', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-08 17:00:00', 0, 0, 'hobby,family', NULL, 'N', '2026-05-25 10:00:00', '2026-05-25 10:00:00'),
('공방 - 원데이 목공 도마 만들기', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-20 16:30:00', 0, 0, 'hobby,cooking', NULL, 'N', '2026-07-06 10:00:00', '2026-07-06 10:00:00'),
('도전 - 하루 만보 걷기 60일', NULL, 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 43, 60, NULL, 0, 0, 'workout,grinding', NULL, 'N', '2026-06-20 07:00:00', '2026-06-20 07:00:00'),
('도전 - 아침형 인간 전환 4주', NULL, 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 28, 28, '2026-03-28 07:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2026-03-01 07:00:00', '2026-03-01 07:00:00'),
('도전 - SNS 안 보기 2주', '도파민 디톡스', 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 14, 14, '2026-04-18 21:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2026-04-04 21:00:00', '2026-04-04 21:00:00'),
('도전 - 일기 100일 연속', NULL, 50, 97, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 77, 100, NULL, 0, 0, 'selfimprovement,grinding', NULL, 'N', '2026-05-24 22:00:00', '2026-05-24 22:00:00'),
('도전 - 새로운 언어 인사말 20개국어', NULL, 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 12, 20, NULL, 0, 0, 'academics,hobby', NULL, 'N', '2026-02-14 12:00:00', '2026-02-14 12:00:00'),
('도전 - 냉수마찰 겨울 완주', NULL, 50, 97, 'CHALLENGE', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-28 07:00:00', 0, 0, 'workout,grinding', NULL, 'N', '2025-12-01 07:00:00', '2025-12-01 07:00:00');

-- 마지막: 인기 버킷 좋아요/댓글 보강 (검색·피드·인기 API용 PUBLIC 완료작)
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('퇴사 전 세계일주 자금 3천만원 모으기', '2년 계획. 응원 부탁해요', 36, 53, 'ORIGINAL', 'PUBLIC', 'Y', 'PROGRESS', 'Y', '2028-06-30', 0, 1, NULL, 0, 8, 'finance,travel,grinding', NULL, 'N', '2026-07-28 20:00:00', '2026-07-28 20:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO like_bucket (user_id, bucket_id) VALUES (30,@b),(32,@b),(33,@b),(41,@b),(42,@b),(43,@b),(48,@b),(50,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('목표가 확실하시네요 멋집니다', 33, @b, 'N', 'N'),
('세계일주 루트도 공유해주세요!', 30, @b, 'N', 'N'),
('저축 꿀팁 배우고 갑니다', 42, @b, 'N', 'N'),
('2년 뒤가 기대되는 버킷', 50, @b, 'N', 'N'),
('도배도배도배', 35, @b, 'N', 'Y');

INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at)
VALUES ('유기견 봉사 1년 개근 후기', '매달 셋째 주 토요일, 벌써 12번째', 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'Y', NULL, 12, 12, '2026-07-19 17:00:00', 0, 7, 'volunteering,pet', NULL, 'N', '2026-07-19 19:00:00', '2026-07-19 19:00:00');
SET @b = LAST_INSERT_ID();
INSERT INTO like_bucket (user_id, bucket_id) VALUES (30,@b),(34,@b),(40,@b),(41,@b),(44,@b),(48,@b),(50,@b);
INSERT INTO comment (comment, user_id, bucket_id, is_blocked, is_hide) VALUES
('1년 개근 존경합니다', 40, @b, 'N', 'N'),
('다음 달에 저도 같이 가도 될까요?', 34, @b, 'N', 'N'),
('이런 버킷이 진짜 멋진 듯', 50, @b, 'N', 'N');

-- =====================================================================
-- 마무리 볼륨 A (30/32/33/34/35/36/37/38/40 각 20건 내외)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('당일치기 인천 섬 여행', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-21 18:00:00', 0, 0, 'travel', NULL, 'N', '2025-09-14 08:00:00', '2025-09-14 08:00:00'),
('기차역 스탬프 투어 10곳', NULL, 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 4, 10, NULL, 0, 0, 'travel,hobby', NULL, 'N', '2025-10-05 09:00:00', '2025-10-05 09:00:00'),
('남산타워 자물쇠 걸기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-03 20:00:00', 0, 0, 'travel,dating', NULL, 'N', '2025-09-25 10:00:00', '2025-09-25 10:00:00'),
('한강 야경 자전거 라이딩', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-05 22:00:00', 0, 0, 'workout,travel', NULL, 'N', '2026-05-29 09:00:00', '2026-05-29 09:00:00'),
('시티투어버스 타보기', NULL, 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-06-19 09:00:00', '2026-06-19 09:00:00'),
('DMZ 안보관광 다녀오기', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,culture', NULL, 'N', '2026-07-02 09:00:00', '2026-07-02 09:00:00'),
('별 보러 천문대 투어', NULL, 30, 47, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel,hobby', NULL, 'N', '2026-07-24 09:00:00', '2026-07-24 09:00:00'),
('수상스키 배우기', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-31', 0, 1, NULL, 0, 0, 'workout,hobby', NULL, 'N', '2026-07-05 09:00:00', '2026-07-05 09:00:00'),
('실내 서핑 체험', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-08 17:00:00', 0, 0, 'workout,hobby', NULL, 'N', '2026-02-27 09:00:00', '2026-02-27 09:00:00'),
('스포츠 마사지 정기 관리', NULL, 32, 49, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-05-14 09:00:00', '2026-05-14 09:00:00'),
('운동 전후 사진 비교 콘텐츠 만들기', NULL, 32, 49, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,hobby', NULL, 'N', '2026-06-23 09:00:00', '2026-06-23 09:00:00'),
('보충제 공부해서 직구하기', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-15 21:00:00', 0, 0, 'workout,study', NULL, 'N', '2026-04-01 09:00:00', '2026-04-01 09:00:00'),
('암벽화 장만하고 자연암장 데뷔', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-11', 0, 1, NULL, 0, 0, 'workout,travel', NULL, 'N', '2026-07-27 09:00:00', '2026-07-27 09:00:00'),
('신춘문예 응모해보기', NULL, 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-10', 0, 1, NULL, 0, 0, 'career,reading', NULL, 'N', '2026-07-08 21:00:00', '2026-07-08 21:00:00'),
('낭독회에서 직접 낭독하기', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading,culture', NULL, 'N', '2026-06-24 21:00:00', '2026-06-24 21:00:00'),
('책장 100권 큐레이션 정리', NULL, 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-31 16:00:00', 0, 0, 'reading,hobby', NULL, 'N', '2026-01-24 21:00:00', '2026-01-24 21:00:00'),
('전자책 단말기 적응하기', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-19 22:00:00', 0, 0, 'reading', NULL, 'N', '2026-03-05 21:00:00', '2026-03-05 21:00:00'),
('밀리의서재 완독 배지 10개', NULL, 33, 50, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 6, 10, NULL, 0, 0, 'reading,grinding', NULL, 'N', '2026-04-28 21:00:00', '2026-04-28 21:00:00'),
('시 필사 인스타 계정 운영', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading,hobby', NULL, 'N', '2026-05-30 21:00:00', '2026-05-30 21:00:00'),
('아이 어린이 뮤지컬 첫 관람', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-03', 0, 1, NULL, 0, 0, 'parenting,culture', NULL, 'N', '2026-08-02 10:00:00', '2026-08-02 10:00:00'),
('베이비 마사지 배우기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-12 11:00:00', 0, 0, 'parenting,study', NULL, 'N', '2026-02-01 10:00:00', '2026-02-01 10:00:00'),
('아이 알레르기 검사 받기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-04-08 10:30:00', 0, 0, 'parenting', NULL, 'N', '2026-03-30 10:00:00', '2026-03-30 10:00:00'),
('맘카페 벼룩시장 판매 데뷔', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-14 14:00:00', 0, 0, 'parenting,finance', NULL, 'N', '2026-06-01 10:00:00', '2026-06-01 10:00:00'),
('아이 사진으로 달력 만들기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'parenting,hobby', NULL, 'N', '2026-08-05 10:00:00', '2026-08-05 10:00:00'),
('둘째 고민 정리하기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'family', NULL, 'N', '2026-07-18 10:00:00', '2026-07-18 10:00:00'),
('게임 굿즈 방 꾸미기', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,hobby', NULL, 'N', '2026-03-03 20:00:00', '2026-03-03 20:00:00'),
('게이밍 의자 허리 지키기 스트레칭', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,workout', NULL, 'N', '2026-04-22 20:00:00', '2026-04-22 20:00:00'),
('스피드런 도전 - 마리오 1시간 컷', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,grinding', NULL, 'N', '2026-05-30 20:00:00', '2026-05-30 20:00:00'),
('게임 없는 주말 보내보기', '금단증상 관찰', 35, 52, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-22 21:00:00', 0, 0, 'gaming,selfimprovement', NULL, 'N', '2026-06-15 20:00:00', '2026-06-15 20:00:00'),
('친구에게 보드게임 룰 마스터 되기', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,socializing', NULL, 'N', '2026-07-08 20:00:00', '2026-07-08 20:00:00'),
('레트로 오락실 성지순례', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-17 19:00:00', 0, 0, 'gaming,travel', NULL, 'N', '2026-01-08 20:00:00', '2026-01-08 20:00:00'),
('ISA 계좌 개설하고 공부', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-19 21:00:00', 0, 0, 'finance,study', NULL, 'N', '2026-02-10 08:00:00', '2026-02-10 08:00:00'),
('연금저축 세액공제 한도 채우기', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2026-03-15 08:00:00', '2026-03-15 08:00:00'),
('경제 팟캐스트 50회 청취', NULL, 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 31, 50, NULL, 0, 0, 'finance,study', NULL, 'N', '2026-04-08 08:00:00', '2026-04-08 08:00:00'),
('부수입 파이프라인 1개 만들기', NULL, 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,career', NULL, 'N', '2026-05-25 08:00:00', '2026-05-25 08:00:00'),
('전세 대출 갈아타기 검토', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-27 15:00:00', 0, 0, 'finance', NULL, 'N', '2026-06-10 08:00:00', '2026-06-10 08:00:00'),
('짠테크 유튜브 정주행 후 실천 리스트', NULL, 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,selfimprovement', NULL, 'N', '2026-07-30 08:00:00', '2026-07-30 08:00:00'),
('브런치 가게 창업 시장조사', NULL, 37, 84, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'career,cooking', NULL, 'N', '2026-06-30 10:00:00', '2026-06-30 10:00:00'),
('푸드마켓 셀러 입점 신청', NULL, 37, 84, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-11-01', 0, 1, NULL, 0, 0, 'career,cooking', NULL, 'N', '2026-07-21 10:00:00', '2026-07-21 10:00:00'),
('제철 과일청 4계절 담그기', NULL, 37, 54, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 4, NULL, 0, 0, 'cooking,hobby', NULL, 'N', '2026-03-20 10:00:00', '2026-03-20 10:00:00'),
('요리 유튜버 레시피 검증 시리즈', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,hobby', NULL, 'N', '2026-05-12 10:00:00', '2026-05-12 10:00:00'),
('명절 상차림 혼자 완성하기', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-25', 0, 1, NULL, 0, 0, 'cooking,family', NULL, 'N', '2026-08-04 10:00:00', '2026-08-04 10:00:00'),
('푸드 스타일링 기초 배우기', NULL, 37, 84, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,study', NULL, 'N', '2026-06-05 10:00:00', '2026-06-05 10:00:00'),
('이력서용 증명사진 리뉴얼', NULL, 38, 77, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-26 14:00:00', 0, 0, 'career', NULL, 'N', '2026-07-20 09:00:00', '2026-07-20 09:00:00'),
('포트폴리오 웹사이트 만들기', NULL, 38, 77, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-30', 0, 1, NULL, 0, 0, 'career,study', NULL, 'N', '2026-08-01 09:10:00', '2026-08-01 09:10:00'),
('스터디카페 대신 도서관 이용 한 달', '비용 절감', 38, 74, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 12, 30, NULL, 0, 0, 'study,finance', NULL, 'N', '2026-07-15 09:00:00', '2026-07-15 09:00:00'),
('기사 필기 하루 3시간 루틴 60일', NULL, 38, 70, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 38, 60, NULL, 0, 0, 'certification,grinding', NULL, 'N', '2026-06-20 09:00:00', '2026-06-20 09:00:00'),
('오픽 IH 등급', NULL, 38, 71, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-18', 0, 1, NULL, 0, 0, 'academics,career', NULL, 'N', '2026-07-01 09:00:00', '2026-07-01 09:00:00'),
('강아지 생일파티 열어주기', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-03 18:00:00', 0, 0, 'pet,family', NULL, 'N', '2026-02-20 09:00:00', '2026-02-20 09:00:00'),
('펫 보험 가입 비교 분석', NULL, 40, 57, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-25 20:00:00', 0, 0, 'pet,finance', NULL, 'N', '2026-01-15 09:00:00', '2026-01-15 09:00:00'),
('반려견 동반 카페 창업 아이디어 정리', NULL, 40, 57, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet,career', NULL, 'N', '2026-06-05 09:00:00', '2026-06-05 09:00:00'),
('고양이 둘째 입양 준비 공부', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet,study', NULL, 'N', '2026-07-12 09:00:00', '2026-07-12 09:00:00'),
('반려동물 장례 미리 알아보기', '슬프지만 필요한 일', 40, 57, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet', NULL, 'N', '2026-07-29 09:00:00', '2026-07-29 09:00:00'),
('펫스타그램 팔로워 1000명', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet,hobby', NULL, 'N', '2026-04-18 09:00:00', '2026-04-18 09:00:00');

-- =====================================================================
-- 마무리 볼륨 B (41/42/43/44/45/47/48/49/50)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('옷장 미니멀 - 안 입는 옷 50벌 정리', NULL, 41, 58, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 28, 50, NULL, 0, 0, 'hobby,finance', NULL, 'N', '2026-04-05 12:00:00', '2026-04-05 12:00:00'),
('빈티지샵에서 인생 아이템 찾기', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-09 16:00:00', 0, 0, 'flexing,hobby', NULL, 'N', '2026-04-28 12:00:00', '2026-04-28 12:00:00'),
('컬러 진단 기반 옷장 리셋', NULL, 41, 58, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty,flexing', NULL, 'N', '2026-06-15 12:00:00', '2026-06-15 12:00:00'),
('셀프 왁싱 도전', NULL, 41, 58, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty', NULL, 'N', '2026-07-08 12:00:00', '2026-07-08 12:00:00'),
('발레핏 3개월 등록', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,beauty', NULL, 'N', '2026-07-22 12:00:00', '2026-07-22 12:00:00'),
('생일에 셀프 꽃다발 만들기', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-02', 0, 1, NULL, 0, 0, 'hobby,flexing', NULL, 'N', '2026-08-04 12:00:00', '2026-08-04 12:00:00'),
('오픈소스 기여 첫 PR 머지', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-02-25 23:00:00', 0, 0, 'career,study', NULL, 'N', '2026-02-01 22:00:00', '2026-02-01 22:00:00'),
('개발 서적 스터디 리딩 맡기', NULL, 42, 81, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'career,reading', NULL, 'N', '2026-06-20 22:00:00', '2026-06-20 22:00:00'),
('홈서버 구축해서 서비스 돌리기', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,career', NULL, 'N', '2026-07-11 22:00:00', '2026-07-11 22:00:00'),
('키보드 커스텀 입문', '무한 지출의 세계라던데', 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,flexing', NULL, 'N', '2026-05-17 22:00:00', '2026-05-17 22:00:00'),
('은퇴 전 기술 강의 하나 만들기', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'career,finance', NULL, 'N', '2026-03-25 22:00:00', '2026-03-25 22:00:00'),
('취미로 드로잉 태블릿 입문', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,culture', NULL, 'N', '2026-06-25 10:00:00', '2026-06-25 10:00:00'),
('아트페어에서 그림 한 점 사기', '소액 컬렉팅 시작', 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture,flexing', NULL, 'N', '2026-07-15 10:00:00', '2026-07-15 10:00:00'),
('동네 영화 상영회 열기', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture,socializing', NULL, 'N', '2026-08-03 10:00:00', '2026-08-03 10:00:00'),
('영화 리뷰 블로그 50편', NULL, 43, 82, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 34, 50, NULL, 0, 0, 'culture,hobby', NULL, 'N', '2026-01-10 10:00:00', '2026-01-10 10:00:00'),
('OST 플레이리스트 100곡 큐레이션', NULL, 43, 82, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 100, 100, '2026-04-30 22:00:00', 0, 0, 'culture,hobby', NULL, 'N', '2026-02-14 10:00:00', '2026-02-14 10:00:00'),
('새 친구 10명에게 먼저 연락하기', NULL, 44, 83, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 6, 10, NULL, 0, 0, 'socializing', NULL, 'N', '2026-05-01 12:00:00', '2026-05-01 12:00:00'),
('경조사 캘린더 만들어 챙기기', NULL, 44, 93, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-01-20 21:00:00', 0, 0, 'socializing', NULL, 'N', '2026-01-10 12:00:00', '2026-01-10 12:00:00'),
('부모님 칠순잔치 기획', NULL, 44, 93, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2027-03-20', 0, 1, NULL, 0, 0, 'family,socializing', NULL, 'N', '2026-07-01 12:00:00', '2026-07-01 12:00:00'),
('소개팅 5번 나가보기', NULL, 44, 91, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 3, 5, NULL, 0, 0, 'dating', NULL, 'N', '2026-03-08 12:00:00', '2026-03-08 12:00:00'),
('청첩장 모임 시즌 무사히 넘기기', '축의금 예산 관리', 44, 93, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'socializing,finance', NULL, 'N', '2026-08-05 12:00:00', '2026-08-05 12:00:00'),
('국제 개발협력 NGO 정기 후원', NULL, 45, 85, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-03-01 10:00:00', 0, 0, 'volunteering,finance', NULL, 'N', '2026-02-20 08:00:00', '2026-02-20 08:00:00'),
('시각장애인 도서 녹음 봉사', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering,reading', NULL, 'N', '2026-06-18 08:00:00', '2026-06-18 08:00:00'),
('봉사 동호회 만들기', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering,socializing', NULL, 'N', '2026-07-25 08:00:00', '2026-07-25 08:00:00'),
('자원봉사 100시간 인증 받기', NULL, 45, 85, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 68, 100, NULL, 0, 0, 'volunteering,grinding', NULL, 'N', '2026-01-05 08:00:00', '2026-01-05 08:00:00'),
('중고 노트북 기부 전 포맷 봉사', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-10 16:00:00', 0, 0, 'volunteering,study', NULL, 'N', '2026-05-01 08:00:00', '2026-05-01 08:00:00'),
('과외 알바로 월 50 벌기', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,academics', NULL, 'N', '2026-03-10 09:00:00', '2026-03-10 09:00:00'),
('기숙사 룸메랑 한 학기 잘 지내기', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-21 18:00:00', 0, 0, 'socializing', NULL, 'N', '2026-03-02 09:00:00', '2026-03-02 09:00:00'),
('학교 앞 맛집 지도 만들어 공유', NULL, 47, 89, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-05-30 12:00:00', 0, 0, 'foodie,hobby', NULL, 'N', '2026-05-01 09:00:00', '2026-05-01 09:00:00'),
('전공 스터디그룹 리더 해보기', NULL, 47, 89, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'academics,socializing', NULL, 'N', '2026-08-06 09:00:00', '2026-08-06 09:00:00'),
('버스킹 공연 기타 반주 서기', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,culture', NULL, 'N', '2026-07-28 10:00:00', '2026-07-28 10:00:00'),
('플리마켓 셀러 첫 참가', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-10', 0, 1, NULL, 0, 0, 'hobby,finance', NULL, 'N', '2026-08-01 10:00:00', '2026-08-01 10:00:00'),
('취미 기록 유튜브 쇼츠 30개', NULL, 48, 95, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 12, 30, NULL, 0, 0, 'hobby,career', NULL, 'N', '2026-05-15 10:00:00', '2026-05-15 10:00:00'),
('만년필 입문하고 잉크 5종 모으기', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 5, NULL, 0, 0, 'hobby,flexing', NULL, 'N', '2026-06-28 10:00:00', '2026-06-28 10:00:00'),
('다이어트 자극 짤 모음집 만들기', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-03 21:00:00', 0, 0, 'diet,hobby', NULL, 'N', '2026-06-01 08:00:00', '2026-06-01 08:00:00'),
('바디프로필 스튜디오 알아보기', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-10-31', 0, 1, NULL, 0, 0, 'workout,diet', NULL, 'N', '2026-08-02 08:00:00', '2026-08-02 08:00:00'),
('단백질 레시피 10개 개발', NULL, 49, 96, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 6, 10, NULL, 0, 0, 'diet,cooking', NULL, 'N', '2026-05-20 08:00:00', '2026-05-20 08:00:00'),
('요요 없이 6개월 유지하기', NULL, 49, 96, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2027-02-28', 0, 1, NULL, 0, 0, 'diet,grinding', NULL, 'N', '2026-08-06 08:00:00', '2026-08-06 08:00:00'),
('명상 리트릿 주말 참가', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement,travel', NULL, 'N', '2026-06-30 12:00:00', '2026-06-30 12:00:00'),
('한 달 동안 매일 사진 한 장', NULL, 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 31, 31, '2026-05-31 23:00:00', 0, 0, 'hobby,grinding', NULL, 'N', '2026-05-01 12:00:00', '2026-05-01 12:00:00'),
('안 가본 동네에서 하루 살기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-07-05 20:00:00', 0, 0, 'travel,hobby', NULL, 'N', '2026-06-25 12:00:00', '2026-06-25 12:00:00'),
('버킷리스트 앱으로 친구 5명 초대', NULL, 50, 97, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 3, 5, NULL, 0, 0, 'socializing,hobby', NULL, 'N', '2026-07-20 12:00:00', '2026-07-20 12:00:00'),
('연말 회고 편지 미래의 나에게 쓰기', NULL, 50, 97, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-08-08 12:00:00', '2026-08-08 12:00:00');

-- =====================================================================
-- 2025년 완료 아카이브 A (30/32/33/34/35/36/37/38/40) — 작년에 만들고 끝낸 버킷들
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('2025 첫 해외여행 후쿠오카', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-15 20:00:00', 0, 0, 'travel', NULL, 'N', '2025-09-01 10:00:00', '2025-09-01 10:00:00'),
('2025 가을 내장산 단풍', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-25 16:00:00', 0, 0, 'travel', NULL, 'N', '2025-10-10 10:00:00', '2025-10-10 10:00:00'),
('2025 첫 혼자 여행', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-15 19:00:00', 0, 0, 'travel,selfimprovement', NULL, 'N', '2025-11-01 10:00:00', '2025-11-01 10:00:00'),
('2025 크리스마스 마켓 구경', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-24 19:00:00', 0, 0, 'travel,culture', NULL, 'N', '2025-12-10 10:00:00', '2025-12-10 10:00:00'),
('2025 겨울바다 정동진', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-31 07:30:00', 0, 0, 'travel', NULL, 'N', '2025-12-20 10:00:00', '2025-12-20 10:00:00'),
('2025 헬스 입문 3개월 완주', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2025-11-30 20:00:00', 0, 0, 'workout', NULL, 'N', '2025-09-01 19:00:00', '2025-09-01 19:00:00'),
('2025 첫 5km 완주', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-12 09:00:00', 0, 0, 'workout', NULL, 'N', '2025-09-20 19:00:00', '2025-09-20 19:00:00'),
('2025 운동복 정리하고 새로 장만', NULL, 32, 49, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-28 15:00:00', 0, 0, 'workout,flexing', NULL, 'N', '2025-09-15 19:00:00', '2025-09-15 19:00:00'),
('2025 계단으로 출근 한 달', NULL, 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 20, 20, '2025-11-28 09:00:00', 0, 0, 'workout,grinding', NULL, 'N', '2025-11-01 08:00:00', '2025-11-01 08:00:00'),
('2025 연말 홈트 정착', NULL, 32, 49, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-30 21:00:00', 0, 0, 'workout', NULL, 'N', '2025-12-01 19:00:00', '2025-12-01 19:00:00'),
('2025 독서모임 첫 참석', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-20 19:30:00', 0, 0, 'reading,socializing', NULL, 'N', '2025-09-05 21:00:00', '2025-09-05 21:00:00'),
('2025 서점 굿즈 컬렉션 시작', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-18 15:00:00', 0, 0, 'reading,hobby', NULL, 'N', '2025-10-01 21:00:00', '2025-10-01 21:00:00'),
('2025 올해의 책 10권 결산', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-29 22:00:00', 0, 0, 'reading', NULL, 'N', '2025-12-15 21:00:00', '2025-12-15 21:00:00'),
('2025 헌책방 투어', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-22 16:00:00', 0, 0, 'reading,travel', NULL, 'N', '2025-11-08 21:00:00', '2025-11-08 21:00:00'),
('2025 밤샘 독서 마라톤 참가', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-31 23:50:00', 0, 0, 'reading,culture', NULL, 'N', '2025-10-20 21:00:00', '2025-10-20 21:00:00'),
('2025 산후조리 마무리', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-30 12:00:00', 0, 0, 'parenting', NULL, 'N', '2025-09-01 10:00:00', '2025-09-01 10:00:00'),
('2025 아기 백일상 차리기', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-20 12:00:00', 0, 0, 'parenting,family', NULL, 'N', '2025-10-01 10:00:00', '2025-10-01 10:00:00'),
('2025 유모차 끌고 첫 외출', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-12 14:00:00', 0, 0, 'parenting', NULL, 'N', '2025-09-05 10:00:00', '2025-09-05 10:00:00'),
('2025 아기와 첫 크리스마스 사진', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-25 11:00:00', 0, 0, 'parenting,family', NULL, 'N', '2025-12-15 10:00:00', '2025-12-15 10:00:00'),
('2025 어린이집 대기 신청 완료', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-05 10:00:00', 0, 0, 'parenting', NULL, 'N', '2025-10-28 10:00:00', '2025-10-28 10:00:00'),
('2025 게임 쇼케이스 지스타 참관', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-16 18:00:00', 0, 0, 'gaming,travel', NULL, 'N', '2025-11-01 20:00:00', '2025-11-01 20:00:00'),
('2025 콘솔 셋업 완성', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-05 22:00:00', 0, 0, 'gaming,flexing', NULL, 'N', '2025-09-20 20:00:00', '2025-09-20 20:00:00'),
('2025 할로윈 게임 코스프레', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-31 21:00:00', 0, 0, 'gaming,socializing', NULL, 'N', '2025-10-15 20:00:00', '2025-10-15 20:00:00'),
('2025 겨울 세일 지름 예산 지키기', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-28 23:00:00', 0, 0, 'gaming,finance', NULL, 'N', '2025-12-18 20:00:00', '2025-12-18 20:00:00'),
('2025 GOTY 후보작 다 해보기', NULL, 35, 52, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 6, 6, '2025-12-31 23:00:00', 0, 0, 'gaming', NULL, 'N', '2025-12-01 20:00:00', '2025-12-01 20:00:00'),
('2025 첫 적금 만기 달성', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-15 10:00:00', 0, 0, 'finance', NULL, 'N', '2025-09-01 08:00:00', '2025-09-01 08:00:00'),
('2025 소비 리포트 분석', NULL, 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-31 22:00:00', 0, 0, 'finance', NULL, 'N', '2025-12-20 08:00:00', '2025-12-20 08:00:00'),
('2025 블랙프라이데이 계획 소비', NULL, 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-29 23:00:00', 0, 0, 'finance', NULL, 'N', '2025-11-15 08:00:00', '2025-11-15 08:00:00'),
('2025 카드 혜택 정리표 만들기', NULL, 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-25 21:00:00', 0, 0, 'finance,study', NULL, 'N', '2025-09-10 08:00:00', '2025-09-10 08:00:00'),
('2025 연말 기부로 마무리', NULL, 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-30 18:00:00', 0, 0, 'finance,volunteering', NULL, 'N', '2025-12-26 08:00:00', '2025-12-26 08:00:00'),
('2025 김장 첫 참여', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-23 17:00:00', 0, 0, 'cooking,family', NULL, 'N', '2025-11-10 10:00:00', '2025-11-10 10:00:00'),
('2025 수능 도시락 조카 서포트', NULL, 37, 54, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-13 07:00:00', 0, 0, 'cooking,family', NULL, 'N', '2025-11-05 10:00:00', '2025-11-05 10:00:00'),
('2025 크리스마스 케이크 셀프 제작', NULL, 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-24 20:00:00', 0, 0, 'cooking', NULL, 'N', '2025-12-10 10:00:00', '2025-12-10 10:00:00'),
('2025 동지 팥죽 쑤기', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-22 18:00:00', 0, 0, 'cooking,culture', NULL, 'N', '2025-12-15 10:00:00', '2025-12-15 10:00:00'),
('2025 명란파스타 레시피 정착', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-08 19:00:00', 0, 0, 'cooking', NULL, 'N', '2025-09-28 10:00:00', '2025-09-28 10:00:00'),
('2025 하반기 공채 서류 5곳 통과', NULL, 38, 77, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 5, 5, '2025-10-30 18:00:00', 0, 0, 'career', NULL, 'N', '2025-09-01 09:00:00', '2025-09-01 09:00:00'),
('2025 첫 최종면접 경험', NULL, 38, 77, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-20 16:00:00', 0, 0, 'career', NULL, 'N', '2025-11-01 09:00:00', '2025-11-01 09:00:00'),
('2025 컴활 필기 합격', NULL, 38, 72, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-14 15:00:00', 0, 0, 'certification', NULL, 'N', '2025-11-25 09:00:00', '2025-11-25 09:00:00'),
('2025 스터디플래너 완주', NULL, 38, 75, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-31 23:00:00', 0, 0, 'study,grinding', NULL, 'N', '2025-09-05 09:00:00', '2025-09-05 09:00:00'),
('2025 토익 첫 응시 780', NULL, 38, 71, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-26 12:00:00', 0, 0, 'academics', NULL, 'N', '2025-10-01 09:00:00', '2025-10-01 09:00:00'),
('2025 입양 1주년 기념일 챙기기', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-19 18:00:00', 0, 0, 'pet,family', NULL, 'N', '2025-09-10 09:00:00', '2025-09-10 09:00:00'),
('2025 첫 애견운동회 참가', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-19 15:00:00', 0, 0, 'pet,socializing', NULL, 'N', '2025-10-01 09:00:00', '2025-10-01 09:00:00'),
('2025 겨울옷 뜨개질 (강아지용)', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-07 20:00:00', 0, 0, 'pet,hobby', NULL, 'N', '2025-11-15 09:00:00', '2025-11-15 09:00:00'),
('2025 건강검진 (반려견) 완료', NULL, 40, 57, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-08 11:00:00', 0, 0, 'pet', NULL, 'N', '2025-10-28 09:00:00', '2025-10-28 09:00:00'),
('2025 눈밭 첫 산책 시켜주기', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-21 10:00:00', 0, 0, 'pet', NULL, 'N', '2025-12-10 09:00:00', '2025-12-10 09:00:00');

-- =====================================================================
-- 2025년 완료 아카이브 B (41/42/43/44/45/47/48/49/50)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('2025 F/W 트렌드 분석 노트', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-30 21:00:00', 0, 0, 'beauty,study', NULL, 'N', '2025-09-10 12:00:00', '2025-09-10 12:00:00'),
('2025 첫 명품 향수 구매', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-11 15:00:00', 0, 0, 'flexing,beauty', NULL, 'N', '2025-10-25 12:00:00', '2025-10-25 12:00:00'),
('2025 연말 파티룩 완성', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-30 19:00:00', 0, 0, 'beauty,socializing', NULL, 'N', '2025-12-10 12:00:00', '2025-12-10 12:00:00'),
('2025 피부과 레이저 첫 경험', NULL, 41, 58, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-14 16:00:00', 0, 0, 'beauty', NULL, 'N', '2025-10-01 12:00:00', '2025-10-01 12:00:00'),
('2025 사이드프로젝트 아이디어 30개 발굴', NULL, 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 30, 30, '2025-11-30 22:00:00', 0, 0, 'career,selfimprovement', NULL, 'N', '2025-09-01 22:00:00', '2025-09-01 22:00:00'),
('2025 개발 컨퍼런스 3개 참석', NULL, 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2025-12-06 18:00:00', 0, 0, 'career,study', NULL, 'N', '2025-09-15 22:00:00', '2025-09-15 22:00:00'),
('2025 이직 준비 완료', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-20 23:00:00', 0, 0, 'career', NULL, 'N', '2025-10-01 22:00:00', '2025-10-01 22:00:00'),
('2025 스탠딩 데스크 셋업', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-27 14:00:00', 0, 0, 'career,workout', NULL, 'N', '2025-09-15 22:00:00', '2025-09-15 22:00:00'),
('2025 아트 필름 15편 정주행', NULL, 43, 82, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 15, 15, '2025-12-28 23:00:00', 0, 0, 'culture', NULL, 'N', '2025-09-05 10:00:00', '2025-09-05 10:00:00'),
('2025 서울국제공연예술제 관람', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-12 21:00:00', 0, 0, 'culture', NULL, 'N', '2025-09-25 10:00:00', '2025-09-25 10:00:00'),
('2025 연말 오케스트라 송년음악회', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-30 21:30:00', 0, 0, 'culture', NULL, 'N', '2025-12-01 10:00:00', '2025-12-01 10:00:00'),
('2025 미술 입문서 3권 완독', NULL, 43, 82, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2025-11-25 22:00:00', 0, 0, 'culture,reading', NULL, 'N', '2025-10-05 10:00:00', '2025-10-05 10:00:00'),
('2025 홈파티 시즌 3회 주최', NULL, 44, 87, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 3, 3, '2025-12-27 22:00:00', 0, 0, 'socializing', NULL, 'N', '2025-10-01 12:00:00', '2025-10-01 12:00:00'),
('2025 첫 와인 클래스 수료', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-15 20:00:00', 0, 0, 'socializing,foodie', NULL, 'N', '2025-10-20 12:00:00', '2025-10-20 12:00:00'),
('2025 오래된 친구 재회 프로젝트', NULL, 44, 92, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-14 19:00:00', 0, 0, 'socializing', NULL, 'N', '2025-11-01 12:00:00', '2025-11-01 12:00:00'),
('2025 연하장 손글씨로 20장 보내기', NULL, 44, 83, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 20, 20, '2025-12-29 21:00:00', 0, 0, 'socializing,hobby', NULL, 'N', '2025-12-15 12:00:00', '2025-12-15 12:00:00'),
('2025 김장 봉사 참여', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-22 16:00:00', 0, 0, 'volunteering,cooking', NULL, 'N', '2025-11-10 08:00:00', '2025-11-10 08:00:00'),
('2025 산타 선물 배달 봉사', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-24 20:00:00', 0, 0, 'volunteering', NULL, 'N', '2025-12-10 08:00:00', '2025-12-10 08:00:00'),
('2025 헌혈 연 4회 달성', NULL, 45, 85, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 4, 4, '2025-12-20 14:00:00', 0, 0, 'volunteering', NULL, 'N', '2025-09-01 08:00:00', '2025-09-01 08:00:00'),
('2025 자선 바자회 물품 기증', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-18 13:00:00', 0, 0, 'volunteering', NULL, 'N', '2025-10-05 08:00:00', '2025-10-05 08:00:00'),
('2025 2학기 장학금 사수', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-26 15:00:00', 0, 0, 'academics,finance', NULL, 'N', '2025-09-01 09:00:00', '2025-09-01 09:00:00'),
('2025 축제 부스 운영 성공', NULL, 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-26 20:00:00', 0, 0, 'socializing', NULL, 'N', '2025-09-10 09:00:00', '2025-09-10 09:00:00'),
('2025 기말 올나잇 스터디 생존', NULL, 47, 89, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-19 08:00:00', 0, 0, 'academics,grinding', NULL, 'N', '2025-12-10 09:00:00', '2025-12-10 09:00:00'),
('2025 겨울방학 계획표 완성', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-23 21:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2025-12-20 09:00:00', '2025-12-20 09:00:00'),
('2025 도예 클래스 6주 수료', NULL, 48, 94, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 6, 6, '2025-10-30 18:00:00', 0, 0, 'hobby', NULL, 'N', '2025-09-15 10:00:00', '2025-09-15 10:00:00'),
('2025 핸드메이드 크리스마스 선물 5개', NULL, 48, 94, 'CHALLENGE', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 5, 5, '2025-12-23 22:00:00', 0, 0, 'hobby,family', NULL, 'N', '2025-11-20 10:00:00', '2025-11-20 10:00:00'),
('2025 어반스케치 입문', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-09 16:00:00', 0, 0, 'hobby,culture', NULL, 'N', '2025-10-15 10:00:00', '2025-10-15 10:00:00'),
('2025 연말 취미 결산 포스팅', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-31 21:00:00', 0, 0, 'hobby', NULL, 'N', '2025-12-28 10:00:00', '2025-12-28 10:00:00'),
('2025 다이어트 시즌1 -4kg', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-30 21:00:00', 0, 0, 'diet', NULL, 'N', '2025-09-01 08:00:00', '2025-09-01 08:00:00'),
('2025 홈트 기구 정리하고 재배치', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-12 15:00:00', 0, 0, 'workout,hobby', NULL, 'N', '2025-10-01 08:00:00', '2025-10-01 08:00:00'),
('2025 연말 모임 식단 방어 성공', NULL, 49, 96, 'ORIGINAL', 'FOLLOWER', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-31 23:00:00', 0, 0, 'diet,socializing', NULL, 'N', '2025-12-01 08:00:00', '2025-12-01 08:00:00'),
('2025 기록 앱으로 식단 정착', NULL, 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-30 21:00:00', 0, 0, 'diet,selfimprovement', NULL, 'N', '2025-09-10 08:00:00', '2025-09-10 08:00:00'),
('2025 첫 해돋이 산행', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-09-07 06:30:00', 0, 0, 'travel,workout', NULL, 'N', '2025-09-01 12:00:00', '2025-09-01 12:00:00'),
('2025 버킷리스트 30개 달성 결산', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-31 23:30:00', 0, 0, 'selfimprovement', NULL, 'N', '2025-12-26 12:00:00', '2025-12-26 12:00:00'),
('2025 김장 도우미 알바 체험', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-11-29 17:00:00', 0, 0, 'hobby,finance', NULL, 'N', '2025-11-20 12:00:00', '2025-11-20 12:00:00'),
('2025 우연히 떠난 무박 여행', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-10-04 23:00:00', 0, 0, 'travel', NULL, 'N', '2025-10-03 12:00:00', '2025-10-03 12:00:00'),
('2025 못 이룬 버킷 2026으로 이월 정리', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2025-12-31 22:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2025-12-30 12:00:00', '2025-12-30 12:00:00');

-- =====================================================================
-- 올해 남은 목표 위시 A (30~40)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('가을 억새 명소 3곳 가기', NULL, 30, 47, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-15', 0, 3, NULL, 0, 0, 'travel', NULL, 'N', '2026-08-07 09:00:00', '2026-08-07 09:00:00'),
('첫눈 오는 날 즉흥 여행', NULL, 30, 47, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'travel', NULL, 'N', '2026-08-07 09:05:00', '2026-08-07 09:05:00'),
('연말 오사카 재방문', NULL, 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-28', 0, 1, NULL, 0, 0, 'travel,foodie', NULL, 'N', '2026-08-07 09:10:00', '2026-08-07 09:10:00'),
('여행 경비 통장 따로 만들기', NULL, 30, 47, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,travel', NULL, 'N', '2026-08-07 09:15:00', '2026-08-07 09:15:00'),
('바람막이 새로 장만하고 가을 라이딩', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-31', 0, 1, NULL, 0, 0, 'workout,flexing', NULL, 'N', '2026-08-07 09:20:00', '2026-08-07 09:20:00'),
('가을 마라톤 10km PB 갱신', NULL, 32, 49, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-08', 0, 1, NULL, 0, 0, 'workout', NULL, 'N', '2026-08-07 09:25:00', '2026-08-07 09:25:00'),
('헬스장 친구 3명 만들기', NULL, 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 1, 3, NULL, 0, 0, 'workout,socializing', NULL, 'N', '2026-08-07 09:30:00', '2026-08-07 09:30:00'),
('연말까지 운동일지 완성', NULL, 32, 49, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'workout,selfimprovement', NULL, 'N', '2026-08-07 09:35:00', '2026-08-07 09:35:00'),
('가을 문학기행 (황순원 소나기마을)', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-11', 0, 1, NULL, 0, 0, 'reading,travel', NULL, 'N', '2026-08-07 09:40:00', '2026-08-07 09:40:00'),
('독서의 계절 이벤트 열기 (모임)', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-20', 0, 1, NULL, 0, 0, 'reading,socializing', NULL, 'N', '2026-08-07 09:45:00', '2026-08-07 09:45:00'),
('올해 50권 목표 막판 스퍼트', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'reading,grinding', NULL, 'N', '2026-08-07 09:50:00', '2026-08-07 09:50:00'),
('북클럽 굿즈 제작해보기', NULL, 33, 50, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading,hobby', NULL, 'N', '2026-08-07 09:55:00', '2026-08-07 09:55:00'),
('아이 첫 추석 한복 입히기', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-25', 0, 1, NULL, 0, 0, 'parenting,family', NULL, 'N', '2026-08-07 10:00:00', '2026-08-07 10:00:00'),
('단풍 아래 가족사진 찍기', NULL, 34, 51, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-31', 0, 1, NULL, 0, 0, 'family,hobby', NULL, 'N', '2026-08-07 10:05:00', '2026-08-07 10:05:00'),
('육아템 미니멀 정리 시즌2', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'parenting,finance', NULL, 'N', '2026-08-07 10:10:00', '2026-08-07 10:10:00'),
('키즈 발레 체험 알아보기', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'parenting,culture', NULL, 'N', '2026-08-07 10:15:00', '2026-08-07 10:15:00'),
('가을 게임 신작 3개 발매일 구매', NULL, 35, 52, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-30', 0, 3, NULL, 0, 0, 'gaming,flexing', NULL, 'N', '2026-08-07 10:20:00', '2026-08-07 10:20:00'),
('지스타 2026 예매 성공하기', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-01', 0, 1, NULL, 0, 0, 'gaming', NULL, 'N', '2026-08-07 10:25:00', '2026-08-07 10:25:00'),
('게임 리뷰 블로그 개설', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,hobby', NULL, 'N', '2026-08-07 10:30:00', '2026-08-07 10:30:00'),
('e스포츠 결승전 직관', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-15', 0, 1, NULL, 0, 0, 'gaming,culture', NULL, 'N', '2026-08-07 10:35:00', '2026-08-07 10:35:00'),
('하반기 저축률 60% 도전', NULL, 36, 53, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'finance,grinding', NULL, 'N', '2026-08-07 10:40:00', '2026-08-07 10:40:00'),
('금 소액 투자 공부', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,study', NULL, 'N', '2026-08-07 10:45:00', '2026-08-07 10:45:00'),
('연말정산 미리보기 점검', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-11-30', 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2026-08-07 10:50:00', '2026-08-07 10:50:00'),
('재테크 책 연말까지 3권', NULL, 36, 53, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 3, NULL, 0, 0, 'finance,reading', NULL, 'N', '2026-08-07 10:55:00', '2026-08-07 10:55:00'),
('추석 선물세트 직접 만들기', NULL, 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-20', 0, 1, NULL, 0, 0, 'cooking,family', NULL, 'N', '2026-08-07 11:00:00', '2026-08-07 11:00:00'),
('가을 배추로 겉절이 마스터', NULL, 37, 54, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-10', 0, 1, NULL, 0, 0, 'cooking', NULL, 'N', '2026-08-07 11:05:00', '2026-08-07 11:05:00'),
('수제 그래놀라 정기 제작', NULL, 37, 59, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,diet', NULL, 'N', '2026-08-07 11:10:00', '2026-08-07 11:10:00'),
('원데이 클래스 조수 알바 해보기', NULL, 37, 84, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,career', NULL, 'N', '2026-08-07 11:15:00', '2026-08-07 11:15:00'),
('하반기 정처기 최종 합격', NULL, 38, 70, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-20', 0, 1, NULL, 0, 0, 'certification,career', NULL, 'N', '2026-08-07 11:20:00', '2026-08-07 11:20:00'),
('공채 최종 합격하고 부모님 식사 대접', NULL, 38, 77, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'career,family', NULL, 'N', '2026-08-07 11:25:00', '2026-08-07 11:25:00'),
('스터디원들과 합격 기념 여행 약속', NULL, 38, 74, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'socializing,travel', NULL, 'N', '2026-08-07 11:30:00', '2026-08-07 11:30:00'),
('면접용 정장 맞추기', NULL, 38, 77, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-09-15', 0, 1, NULL, 0, 0, 'career,flexing', NULL, 'N', '2026-08-07 11:35:00', '2026-08-07 11:35:00'),
('가을 낙엽 산책 필수 코스 정리', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-20', 0, 1, NULL, 0, 0, 'pet,travel', NULL, 'N', '2026-08-07 11:40:00', '2026-08-07 11:40:00'),
('반려견 수영 첫 도전', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-08-31', 0, 1, NULL, 0, 0, 'pet,workout', NULL, 'N', '2026-08-07 11:45:00', '2026-08-07 11:45:00'),
('강아지 크리스마스 옷 만들기', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'pet,hobby', NULL, 'N', '2026-08-07 11:50:00', '2026-08-07 11:50:00'),
('보호소 겨울 담요 기부 모으기', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-30', 0, 1, NULL, 0, 0, 'pet,volunteering', NULL, 'N', '2026-08-07 11:55:00', '2026-08-07 11:55:00');

-- =====================================================================
-- 올해 남은 목표 위시 B (41~50)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('가을 웜톤 메이크업 정착', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-01', 0, 1, NULL, 0, 0, 'beauty', NULL, 'N', '2026-08-07 12:00:00', '2026-08-07 12:00:00'),
('연말 뷰티 결산 베스트템 정리', NULL, 41, 58, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-25', 0, 1, NULL, 0, 0, 'beauty,hobby', NULL, 'N', '2026-08-07 12:05:00', '2026-08-07 12:05:00'),
('헤어 트리트먼트 홈케어 8주', NULL, 41, 58, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 8, NULL, 0, 0, 'beauty', NULL, 'N', '2026-08-07 12:10:00', '2026-08-07 12:10:00'),
('블프 위시리스트 미리 정리', NULL, 41, 58, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-11-20', 0, 1, NULL, 0, 0, 'flexing,finance', NULL, 'N', '2026-08-07 12:15:00', '2026-08-07 12:15:00'),
('컨퍼런스 발표 자료 완성', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-15', 0, 1, NULL, 0, 0, 'career', NULL, 'N', '2026-08-07 12:20:00', '2026-08-07 12:20:00'),
('사이드프로젝트 유저 100명', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'career,grinding', NULL, 'N', '2026-08-07 12:25:00', '2026-08-07 12:25:00'),
('개발자 밋업 네트워킹 3회', NULL, 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-15', 0, 3, NULL, 0, 0, 'career,socializing', NULL, 'N', '2026-08-07 12:30:00', '2026-08-07 12:30:00'),
('연말 회고록 블로그 발행', NULL, 42, 81, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'selfimprovement,career', NULL, 'N', '2026-08-07 12:35:00', '2026-08-07 12:35:00'),
('가을 미술관 투어 코스 완성', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-31', 0, 1, NULL, 0, 0, 'culture,travel', NULL, 'N', '2026-08-07 12:40:00', '2026-08-07 12:40:00'),
('올해의 영화 TOP10 결산 포스팅', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-30', 0, 1, NULL, 0, 0, 'culture,hobby', NULL, 'N', '2026-08-07 12:45:00', '2026-08-07 12:45:00'),
('공연 관람 연 30회 채우기', NULL, 43, 82, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-31', 21, 30, NULL, 0, 0, 'culture,grinding', NULL, 'N', '2026-08-07 12:50:00', '2026-08-07 12:50:00'),
('내년 공연 캘린더 미리 짜기', NULL, 43, 82, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'culture', NULL, 'N', '2026-08-07 12:55:00', '2026-08-07 12:55:00'),
('할로윈 파티 기획', NULL, 44, 87, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-31', 0, 1, NULL, 0, 0, 'socializing', NULL, 'N', '2026-08-07 13:00:00', '2026-08-07 13:00:00'),
('연말 시크릿산타 이벤트 진행', NULL, 44, 86, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-24', 0, 1, NULL, 0, 0, 'socializing,family', NULL, 'N', '2026-08-07 13:05:00', '2026-08-07 13:05:00'),
('모임 회비 장부 시스템 만들기', NULL, 44, 86, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'socializing,finance', NULL, 'N', '2026-08-07 13:10:00', '2026-08-07 13:10:00'),
('둘레길 걷기 모임 시즌2 모집', NULL, 44, 86, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-10', 0, 1, NULL, 0, 0, 'socializing,workout', NULL, 'N', '2026-08-07 13:15:00', '2026-08-07 13:15:00'),
('연탄봉사 시즌 개막 준비', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-01', 0, 1, NULL, 0, 0, 'volunteering', NULL, 'N', '2026-08-07 13:20:00', '2026-08-07 13:20:00'),
('김장 나눔 100포기 목표', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-11-25', 0, 1, NULL, 0, 0, 'volunteering,cooking', NULL, 'N', '2026-08-07 13:25:00', '2026-08-07 13:25:00'),
('연말 이웃 선물 박스 만들기', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'volunteering,family', NULL, 'N', '2026-08-07 13:30:00', '2026-08-07 13:30:00'),
('청소년 멘토링 프로그램 지원', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-30', 0, 1, NULL, 0, 0, 'volunteering,study', NULL, 'N', '2026-08-07 13:35:00', '2026-08-07 13:35:00'),
('2학기 발표 공포증 극복', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'academics,selfimprovement', NULL, 'N', '2026-08-07 13:40:00', '2026-08-07 13:40:00'),
('겨울 계절학기로 학점 보충', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2027-01-31', 0, 1, NULL, 0, 0, 'academics', NULL, 'N', '2026-08-07 13:45:00', '2026-08-07 13:45:00'),
('동아리 후배 리크루팅 10명', NULL, 47, 90, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-30', 0, 10, NULL, 0, 0, 'socializing', NULL, 'N', '2026-08-07 13:50:00', '2026-08-07 13:50:00'),
('축제 준비위원 활동 완주', NULL, 47, 90, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-09-25', 0, 1, NULL, 0, 0, 'socializing,career', NULL, 'N', '2026-08-07 13:55:00', '2026-08-07 13:55:00'),
('가을 감성 캘리그라피 엽서 제작', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-15', 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-08-07 14:00:00', '2026-08-07 14:00:00'),
('연말 핸드메이드 마켓 출점', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-13', 0, 1, NULL, 0, 0, 'hobby,finance', NULL, 'N', '2026-08-07 14:05:00', '2026-08-07 14:05:00'),
('공방 정기권 끊고 주 1회 루틴', NULL, 48, 94, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,selfimprovement', NULL, 'N', '2026-08-07 14:10:00', '2026-08-07 14:10:00'),
('취미 부자 연말 전시회 (개인전)', NULL, 48, 95, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-12-28', 0, 1, NULL, 0, 0, 'hobby,culture', NULL, 'N', '2026-08-07 14:15:00', '2026-08-07 14:15:00'),
('추석 연휴 식단 계획 미리 세우기', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-09-24', 0, 1, NULL, 0, 0, 'diet', NULL, 'N', '2026-08-07 14:20:00', '2026-08-07 14:20:00'),
('목표 체중 달성 기념 옷 쇼핑', NULL, 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-01', 0, 1, NULL, 0, 0, 'diet,flexing', NULL, 'N', '2026-08-07 14:25:00', '2026-08-07 14:25:00'),
('바디프로필 D-90 플랜 시작', NULL, 49, 96, 'ORIGINAL', 'FOLLOWER', 'N', 'PROGRESS', 'N', '2026-11-07', 0, 1, NULL, 0, 0, 'workout,diet', NULL, 'N', '2026-08-07 14:30:00', '2026-08-07 14:30:00'),
('다이어트 성공 수기 공모전 응모', NULL, 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-15', 0, 1, NULL, 0, 0, 'diet,career', NULL, 'N', '2026-08-07 14:35:00', '2026-08-07 14:35:00'),
('가을 억새밭 인생샷', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-10-25', 0, 1, NULL, 0, 0, 'travel,hobby', NULL, 'N', '2026-08-07 14:40:00', '2026-08-07 14:40:00'),
('한 해 마무리 버킷 결산 파티', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', '2026-12-30', 0, 1, NULL, 0, 0, 'socializing,selfimprovement', NULL, 'N', '2026-08-07 14:45:00', '2026-08-07 14:45:00'),
('내년 버킷리스트 50개 초안', NULL, 50, 97, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-31', 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-08-07 14:50:00', '2026-08-07 14:50:00'),
('첫 눈 오면 눈사람 만들기', NULL, 50, 97, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,family', NULL, 'N', '2026-08-07 14:55:00', '2026-08-07 14:55:00');

-- =====================================================================
-- 소소한 습관/일상 버킷 (전 유저, 진행중 위주)
-- =====================================================================
INSERT INTO bucket (title, memo, user_id, category_id, type, exposure_status, pin, status, scrap_yn, target_date, user_count, goal_count, completed_date, seq, like_count, keywords, friend_user_ids, deleted, created_at, updated_at) VALUES
('물 하루 2리터 마시기 30일', NULL, 30, 47, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 12, 30, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-07-26 08:00:00', '2026-07-26 08:00:00'),
('아침 이불 정리 습관 21일', NULL, 30, 47, 'CHALLENGE', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 21, 21, '2026-05-21 08:00:00', 0, 0, 'selfimprovement', NULL, 'N', '2026-05-01 08:00:00', '2026-05-01 08:00:00'),
('한 달 야식 3회 이하', NULL, 30, 47, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-08-31', 0, 1, NULL, 0, 0, 'diet', NULL, 'N', '2026-08-01 08:00:00', '2026-08-01 08:00:00'),
('만보 걷기 주 4회', NULL, 32, 49, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 3, 4, NULL, 0, 0, 'workout', NULL, 'N', '2026-08-03 07:00:00', '2026-08-03 07:00:00'),
('취침 전 폰 안 보기 2주', NULL, 32, 49, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 6, 14, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-08-01 22:00:00', '2026-08-01 22:00:00'),
('주 1회 부모님께 전화', NULL, 32, 49, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'family', NULL, 'N', '2026-07-06 20:00:00', '2026-07-06 20:00:00'),
('자기 전 10분 독서', NULL, 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading,selfimprovement', NULL, 'N', '2026-07-15 22:30:00', '2026-07-15 22:30:00'),
('일주일에 서점 한 번 들르기', NULL, 33, 50, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'reading,hobby', NULL, 'N', '2026-06-30 18:00:00', '2026-06-30 18:00:00'),
('메모 습관 만들기', NULL, 33, 50, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-08-02 09:00:00', '2026-08-02 09:00:00'),
('커피 하루 한 잔으로 줄이기', NULL, 34, 51, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 9, 30, NULL, 0, 0, 'diet,selfimprovement', NULL, 'N', '2026-07-31 09:00:00', '2026-07-31 09:00:00'),
('아이 재우고 나만의 시간 30분 확보', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement,parenting', NULL, 'N', '2026-07-10 22:00:00', '2026-07-10 22:00:00'),
('주말 아침 남편과 커피 타임', NULL, 34, 51, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'family,dating', NULL, 'N', '2026-06-27 09:00:00', '2026-06-27 09:00:00'),
('새벽 게임 금지 (1시 취침)', NULL, 35, 52, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 11, 30, NULL, 0, 0, 'selfimprovement,gaming', NULL, 'N', '2026-07-28 01:00:00', '2026-07-28 01:00:00'),
('게임하면서 스쿼트 로딩마다 10개', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,workout', NULL, 'N', '2026-07-20 20:00:00', '2026-07-20 20:00:00'),
('주 1회 보드게임 카페', NULL, 35, 52, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'gaming,socializing', NULL, 'N', '2026-06-18 19:00:00', '2026-06-18 19:00:00'),
('하루 지출 기록 미루지 않기', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,selfimprovement', NULL, 'N', '2026-07-22 22:00:00', '2026-07-22 22:00:00'),
('점심 커피 사내 카페 이용 (반값)', NULL, 36, 53, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance', NULL, 'N', '2026-07-01 12:00:00', '2026-07-01 12:00:00'),
('영수증 리뷰 포인트 모으기', NULL, 36, 53, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'finance,grinding', NULL, 'N', '2026-06-15 12:00:00', '2026-06-15 12:00:00'),
('주 3회 집밥 해먹기', NULL, 37, 54, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 2, 3, NULL, 0, 0, 'cooking,finance', NULL, 'N', '2026-08-04 18:00:00', '2026-08-04 18:00:00'),
('냉장고 재고 정리 주간 루틴', NULL, 37, 54, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'cooking,selfimprovement', NULL, 'N', '2026-07-13 10:00:00', '2026-07-13 10:00:00'),
('설거지 바로바로 하기', NULL, 37, 54, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-06-25 20:00:00', '2026-06-25 20:00:00'),
('아침 영어단어 20개 암기', NULL, 38, 71, 'CHALLENGE', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 26, 60, NULL, 0, 0, 'academics,grinding', NULL, 'N', '2026-07-05 07:00:00', '2026-07-05 07:00:00'),
('공부 시작 전 폰 다른 방에 두기', NULL, 38, 74, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'study,selfimprovement', NULL, 'N', '2026-07-18 09:00:00', '2026-07-18 09:00:00'),
('일요일 저녁 주간 계획 세우기', NULL, 38, 75, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-06-28 20:00:00', '2026-06-28 20:00:00'),
('산책 후 발 닦이기 루틴 정착', NULL, 40, 57, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet', NULL, 'N', '2026-07-08 09:00:00', '2026-07-08 09:00:00'),
('사료 급여량 저울로 재기', NULL, 40, 57, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet,study', NULL, 'N', '2026-07-25 09:00:00', '2026-07-25 09:00:00'),
('주 1회 빗질+치아 관리 데이', NULL, 40, 57, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'pet', NULL, 'N', '2026-06-22 09:00:00', '2026-06-22 09:00:00'),
('자기 전 폼클렌징 거르지 않기', NULL, 41, 58, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty,selfimprovement', NULL, 'N', '2026-07-30 23:00:00', '2026-07-30 23:00:00'),
('하루 한 번 스트레칭', NULL, 41, 58, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,beauty', NULL, 'N', '2026-07-14 22:00:00', '2026-07-14 22:00:00'),
('일주일 옷 미리 코디해두기', NULL, 41, 58, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'beauty,selfimprovement', NULL, 'N', '2026-06-29 21:00:00', '2026-06-29 21:00:00'),
('출근길 팟캐스트로 인풋 채우기', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'study,career', NULL, 'N', '2026-07-21 08:30:00', '2026-07-21 08:30:00'),
('점심시간 산책 15분', NULL, 42, 81, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,selfimprovement', NULL, 'N', '2026-07-02 12:30:00', '2026-07-02 12:30:00'),
('커밋 잔디 안 끊기 100일', NULL, 42, 81, 'CHALLENGE', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 64, 100, NULL, 0, 0, 'career,grinding', NULL, 'N', '2026-06-05 23:00:00', '2026-06-05 23:00:00'),
('주말 아침 예술 다큐 한 편', NULL, 43, 82, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture,selfimprovement', NULL, 'N', '2026-07-11 10:00:00', '2026-07-11 10:00:00'),
('공연 후기 당일에 기록하기', NULL, 43, 82, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture,hobby', NULL, 'N', '2026-06-19 22:00:00', '2026-06-19 22:00:00'),
('한 달에 새 장르 음악 하나 듣기', NULL, 43, 82, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'culture,hobby', NULL, 'N', '2026-05-27 21:00:00', '2026-05-27 21:00:00'),
('약속 10분 전 도착 습관', NULL, 44, 83, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement,socializing', NULL, 'N', '2026-07-24 12:00:00', '2026-07-24 12:00:00'),
('연락처 정리하고 생일 알림 설정', NULL, 44, 83, 'ORIGINAL', 'PRIVATE', 'N', 'COMPLETE', 'N', NULL, 1, 1, '2026-06-30 21:00:00', 0, 0, 'socializing', NULL, 'N', '2026-06-25 12:00:00', '2026-06-25 12:00:00'),
('모임 후 다음날 감사 인사 보내기', NULL, 44, 83, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'socializing,selfimprovement', NULL, 'N', '2026-07-06 12:00:00', '2026-07-06 12:00:00'),
('일주일에 한 번 착한 일 기록', NULL, 45, 85, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering,selfimprovement', NULL, 'N', '2026-07-19 08:00:00', '2026-07-19 08:00:00'),
('분리수거 완벽하게 하기', NULL, 45, 85, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering', NULL, 'N', '2026-06-30 08:00:00', '2026-06-30 08:00:00'),
('장바구니 들고 다니기 습관', NULL, 45, 85, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'volunteering,selfimprovement', NULL, 'N', '2026-06-08 08:00:00', '2026-06-08 08:00:00'),
('강의 끝나고 바로 복습 15분', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'academics,selfimprovement', NULL, 'N', '2026-08-05 09:00:00', '2026-08-05 09:00:00'),
('아침 1교시 지각 제로 학기', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', '2026-12-20', 0, 1, NULL, 0, 0, 'academics,grinding', NULL, 'N', '2026-08-06 09:00:00', '2026-08-06 09:00:00'),
('과제 마감 하루 전 제출 습관', NULL, 47, 89, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'academics,selfimprovement', NULL, 'N', '2026-07-30 09:00:00', '2026-07-30 09:00:00'),
('작업 전 책상 정리 5분', NULL, 48, 94, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,selfimprovement', NULL, 'N', '2026-07-16 10:00:00', '2026-07-16 10:00:00'),
('취미 재료비 월 10만원 상한', NULL, 48, 94, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby,finance', NULL, 'N', '2026-07-03 10:00:00', '2026-07-03 10:00:00'),
('완성작 사진 아카이브 정리', NULL, 48, 95, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'hobby', NULL, 'N', '2026-06-20 10:00:00', '2026-06-20 10:00:00'),
('식사 시간 20분 이상 천천히', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'diet,selfimprovement', NULL, 'N', '2026-07-27 12:00:00', '2026-07-27 12:00:00'),
('엘리베이터 대신 계단', NULL, 49, 96, 'ORIGINAL', 'PUBLIC', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'workout,diet', NULL, 'N', '2026-07-09 08:00:00', '2026-07-09 08:00:00'),
('물 마시기 앱 알림 지키기', NULL, 49, 96, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'diet', NULL, 'N', '2026-06-17 08:00:00', '2026-06-17 08:00:00'),
('하루 한 가지 감사한 일 적기', NULL, 50, 97, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-07-23 22:00:00', '2026-07-23 22:00:00'),
('주 1회 디지털 파일 정리', NULL, 50, 97, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-07-05 21:00:00', '2026-07-05 21:00:00'),
('잠들기 전 내일 할 일 3개 적기', NULL, 50, 97, 'ORIGINAL', 'PRIVATE', 'N', 'PROGRESS', 'N', NULL, 0, 1, NULL, 0, 0, 'selfimprovement', NULL, 'N', '2026-06-11 22:30:00', '2026-06-11 22:30:00');

-- ===== 적재 결과 요약 =====
SELECT type, exposure_status, status, COUNT(*) AS cnt FROM bucket WHERE user_id IN (30,32,33,34,35,36,37,38,40,41,42,43,44,45,47,48,49,50) GROUP BY 1,2,3 ORDER BY 1,2,3;
SELECT 'bucket' AS t, COUNT(*) AS cnt FROM bucket WHERE user_id IN (30,32,33,34,35,36,37,38,40,41,42,43,44,45,47,48,49,50)
UNION ALL SELECT 'bucket_member', COUNT(*) FROM bucket_member WHERE user_id BETWEEN 30 AND 50
UNION ALL SELECT 'comment', COUNT(*) FROM comment WHERE user_id BETWEEN 30 AND 50
UNION ALL SELECT 'like_bucket', COUNT(*) FROM like_bucket WHERE user_id BETWEEN 30 AND 50
UNION ALL SELECT 'follow', COUNT(*) FROM follow WHERE user_id BETWEEN 30 AND 50
UNION ALL SELECT 'alarm', COUNT(*) FROM alarm WHERE user_id BETWEEN 30 AND 50
UNION ALL SELECT 'recent_search', COUNT(*) FROM recent_search WHERE user_id BETWEEN 30 AND 50;
