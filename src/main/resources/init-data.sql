-- Default User Set
INSERT IGNORE INTO `user`
(id, account_type, alarm_yn, bio, email, img_url, last_login_at, name, premium_status, status)
VALUES (9999990, 'ANDROID', 'N', NULL, 'mybury999@gmail.com', NULL, NULL, 'mybury999', 'NONE', 'ACTIVE');
INSERT IGNORE INTO `user`
(id, account_type, alarm_yn, bio, email, img_url, last_login_at, name, premium_status, status)
VALUES (9999991, 'ANDROID', 'N', NULL, 'mybury998@gmail.com', NULL, NULL, 'mybury998', 'NONE', 'ACTIVE');
INSERT IGNORE INTO `user`
(id, account_type, alarm_yn, bio, email, img_url, last_login_at, name, premium_status, status)
VALUES (9999992, 'ANDROID', 'N', NULL, 'mybury997@gmail.com', NULL, NULL, 'mybury997', 'NONE', 'ACTIVE');

INSERT INTO badge (achieve_count, achieve_yn, badge_type_id, select_yn, user_id)
SELECT 0, 'Y', 1, 'Y', 9999990
FROM DUAL
WHERE NOT EXISTS (SELECT 1
                  FROM badge
                  WHERE user_id = 9999990);

INSERT INTO badge (achieve_count, achieve_yn, badge_type_id, select_yn, user_id)
SELECT 0, 'Y', 1, 'Y', 9999991
FROM DUAL
WHERE NOT EXISTS (SELECT 1
                  FROM badge
                  WHERE user_id = 9999991);

INSERT INTO badge (achieve_count, achieve_yn, badge_type_id, select_yn, user_id)
SELECT 0, 'Y', 1, 'Y', 9999992
FROM DUAL
WHERE NOT EXISTS (SELECT 1
                  FROM badge
                  WHERE user_id = 9999992);

-- Default Badge Type
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (1, '/images/academics_01.png', '/images/academics_01.png', '/images/academics_01.png',
        '/images/academics_01.png', '기본', '');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (26, '/images/academics_01.png', '/images/academics_02.png', '/images/academics_03.png',
        '/images/academics_04.png', '학업', 'academics');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (27, '/images/beauty_01.png', '/images/beauty_02.png', '/images/beauty_03.png', '/images/beauty_04.png', '뷰티',
        'beauty');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (28, '/images/career_01.png', '/images/career_02.png', '/images/career_03.png', '/images/career_04.png', '커리어',
        'career');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (29, '/images/certification_01.png', '/images/certification_02.png', '/images/certification_03.png',
        '/images/certification_04.png', '자격증', 'certification');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (30, '/images/cooking_01.png', '/images/cooking_02.png', '/images/cooking_03.png', '/images/cooking_04.png',
        '요리', 'cooking');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (31, '/images/culture_01.png', '/images/culture_02.png', '/images/culture_03.png', '/images/culture_04.png',
        '문화', 'culture');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (32, '/images/dating_01.png', '/images/dating_02.png', '/images/dating_03.png', '/images/dating_04.png', '연애',
        'dating');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (33, '/images/diet_01.png', '/images/diet_02.png', '/images/diet_03.png', '/images/diet_04.png', '다이어트', 'diet');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (34, '/images/family_01.png', '/images/family_02.png', '/images/family_03.png', '/images/family_04.png', '가족',
        'family');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (35, '/images/finance_01.png', '/images/finance_02.png', '/images/finance_03.png', '/images/finance_04.png',
        '금융', 'finance');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (36, '/images/flexing_01.png', '/images/flexing_02.png', '/images/flexing_03.png', '/images/flexing_04.png',
        '플렉스', 'flexing');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (37, '/images/foodie_01.png', '/images/foodie_02.png', '/images/foodie_03.png', '/images/foodie_04.png', '맛집',
        'foodie');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (38, '/images/gaming_01.png', '/images/gaming_02.png', '/images/gaming_03.png', '/images/gaming_04.png', '게임',
        'gaming');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (39, '/images/grinding_01.png', '/images/grinding_02.png', '/images/grinding_03.png', '/images/grinding_04.png',
        '존버', 'grinding');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (40, '/images/hobby_01.png', '/images/hobby_02.png', '/images/hobby_03.png', '/images/hobby_04.png', '취미',
        'hobby');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (41, '/images/parenting_01.png', '/images/parenting_02.png', '/images/parenting_03.png',
        '/images/parenting_04.png', '육아', 'parenting');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (42, '/images/pet_01.png', '/images/pet_02.png', '/images/pet_03.png', '/images/pet_04.png', '반려동물', 'pet');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (43, '/images/reading_01.png', '/images/reading_02.png', '/images/reading_03.png', '/images/reading_04.png',
        '독서', 'reading');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (44, '/images/selfimprovement_01.png', '/images/selfimprovement_02.png', '/images/selfimprovement_03.png',
        '/images/selfimprovement_04.png', '자기계발', 'selfimprovement');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (45, '/images/socializing_01.png', '/images/socializing_02.png', '/images/socializing_03.png',
        '/images/socializing_04.png', '사교', 'socializing');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (46, '/images/study_01.png', '/images/study_02.png', '/images/study_03.png', '/images/study_04.png', '학업',
        'study');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (47, '/images/travel_01.png', '/images/travel_02.png', '/images/travel_03.png', '/images/travel_04.png', '여행',
        'travel');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (48, '/images/volunteering_01.png', '/images/volunteering_02.png', '/images/volunteering_03.png',
        '/images/volunteering_04.png', '봉사', 'volunteering');
INSERT IGNORE INTO badge_type
    (id, img_url1, img_url2, img_url3, img_url4, title, code)
VALUES (49, '/images/workout_01.png', '/images/workout_02.png', '/images/workout_03.png', '/images/workout_04.png',
        '운동', 'workout');



