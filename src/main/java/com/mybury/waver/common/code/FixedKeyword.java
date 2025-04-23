package com.mybury.waver.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FixedKeyword {
  ACADEMICS("academics", "학업"),
  BEAUTY("beauty", "뷰티"),
  CAREER("career", "커리어"),
  CERTIFICATION("certification", "자격증"),
  COOKING("cooking", "요리"),
  CULTURE("culture", "문화"),
  DATING("dating", "연애"),
  DIET("diet", "다이어트"),
  FAMILY("family", "가족"),
  FINANCE("finance", "금융"),
  FLEXING("flexing", "플렉스"),
  FOODIE("foodie", "맛집"),
  GAMING("gaming", "게임"),
  GRINDING("grinding", "존버"),
  HOBBY("hobby", "취미"),
  PARENTING("parenting", "육아"),
  PET("pet", "반려동물"),
  READING("reading", "독서"),
  SELFIMPROVEMENT("selfimprovement", "자기계발"),
  SOCIALIZING("socializing", "사교"),
  STUDY("study", "스터디"),
  TRAVEL("travel", "여행"),
  VOLUNTEERING("volunteering", "봉사"),
  WORKOUT("workout", "운동"),
  ;

  private final String code;
  private final String name;
}
