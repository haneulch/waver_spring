package com.mybury.waver.domain.mybury;

/**
 * mybury 레거시 DB 컬럼 복호화 상수.
 * 레거시가 MySQL AES_ENCRYPT + HEX로 저장한 컬럼이 있어 SQL 레벨(@ColumnTransformer read 식)에서 복호화한다.
 * 키는 레거시 서비스와 동일해야 한다 (@ColumnTransformer가 컴파일 상수를 요구해 프로퍼티 주입 불가).
 * <p>
 * 단, 암호화는 테이블·컬럼마다 적용 여부가 다르다(mt_user는 암호문, mt_bucketlist.title은 평문).
 * AES_DECRYPT는 hex가 아니거나 키가 맞지 않으면 NULL을 반환하므로 COALESCE로 원본을 돌려준다.
 * 평문 컬럼에 복호화를 걸어 값이 통째로 NULL이 되는 사고를 막기 위한 것이다.
 */
public final class MyburyEncryption {

  private static final String SECRET_KEY = "VN4A297LLXDHLN7G";
  private static final String DEC_PREFIX = "COALESCE(CAST(AES_DECRYPT(UNHEX(";
  private static final String DEC_INFIX = "), '" + SECRET_KEY + "') AS CHAR(1250)), ";
  private static final String DEC_SUFFIX = ")";

  public static final String DEC_USER_EMAIL = DEC_PREFIX + "email" + DEC_INFIX + "email" + DEC_SUFFIX;
  public static final String DEC_USER_NAME = DEC_PREFIX + "name" + DEC_INFIX + "name" + DEC_SUFFIX;
  public static final String DEC_USER_IMG_URL = DEC_PREFIX + "img_url" + DEC_INFIX + "img_url" + DEC_SUFFIX;

  public static final String DEC_BUCKETLIST_TITLE = DEC_PREFIX + "title" + DEC_INFIX + "title" + DEC_SUFFIX;
  public static final String DEC_BUCKETLIST_MEMO = DEC_PREFIX + "memo" + DEC_INFIX + "memo" + DEC_SUFFIX;
  public static final String DEC_BUCKETLIST_IMG_URL_1 =
      DEC_PREFIX + "img_url_1" + DEC_INFIX + "img_url_1" + DEC_SUFFIX;
  public static final String DEC_BUCKETLIST_IMG_URL_2 =
      DEC_PREFIX + "img_url_2" + DEC_INFIX + "img_url_2" + DEC_SUFFIX;
  public static final String DEC_BUCKETLIST_IMG_URL_3 =
      DEC_PREFIX + "img_url_3" + DEC_INFIX + "img_url_3" + DEC_SUFFIX;

  public static final String DEC_CATEGORY_NAME = DEC_PREFIX + "name" + DEC_INFIX + "name" + DEC_SUFFIX;

  private MyburyEncryption() {
  }
}
