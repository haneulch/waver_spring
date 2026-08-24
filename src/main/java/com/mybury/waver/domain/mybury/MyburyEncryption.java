package com.mybury.waver.domain.mybury;

/**
 * mybury 레거시 DB 컬럼 암호화 상수.
 * 레거시가 MySQL AES_ENCRYPT + HEX 함수로 컬럼을 암호화해 저장하므로,
 * 복호화도 SQL 레벨(@ColumnTransformer read 식)에서 수행해야 한다.
 * 키는 레거시 서비스와 동일해야 복호화 가능 (@ColumnTransformer가 컴파일 상수를 요구해 프로퍼티 주입 불가).
 */
public final class MyburyEncryption {

  private static final String SECRET_KEY = "VN4A297LLXDHLN7G";
  private static final String DEC_PREFIX = "CAST(AES_DECRYPT(UNHEX(";
  private static final String DEC_SUFFIX = "), '" + SECRET_KEY + "') AS CHAR(1250))";

  public static final String DEC_USER_EMAIL = DEC_PREFIX + "email" + DEC_SUFFIX;
  public static final String DEC_USER_NAME = DEC_PREFIX + "name" + DEC_SUFFIX;
  public static final String DEC_USER_IMG_URL = DEC_PREFIX + "img_url" + DEC_SUFFIX;

  public static final String DEC_BUCKETLIST_TITLE = DEC_PREFIX + "title" + DEC_SUFFIX;
  public static final String DEC_BUCKETLIST_MEMO = DEC_PREFIX + "memo" + DEC_SUFFIX;
  public static final String DEC_BUCKETLIST_IMG_URL_1 = DEC_PREFIX + "img_url_1" + DEC_SUFFIX;
  public static final String DEC_BUCKETLIST_IMG_URL_2 = DEC_PREFIX + "img_url_2" + DEC_SUFFIX;
  public static final String DEC_BUCKETLIST_IMG_URL_3 = DEC_PREFIX + "img_url_3" + DEC_SUFFIX;

  public static final String DEC_CATEGORY_NAME = DEC_PREFIX + "name" + DEC_SUFFIX;

  private MyburyEncryption() {
  }
}
