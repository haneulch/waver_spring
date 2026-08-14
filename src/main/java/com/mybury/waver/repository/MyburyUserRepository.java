package com.mybury.waver.repository;

import com.mybury.waver.domain.mybury.MyburyUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyburyUserRepository extends JpaRepository<MyburyUser, String> {

  // email은 @ColumnTransformer로 SQL 레벨 복호화 후 비교된다 (인덱스 미사용 — 회원가입 시 1회 조회라 허용)
  boolean existsByEmail(String email);

  Optional<MyburyUser> findByEmail(String email);
}
