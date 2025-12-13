package com.mybury.waver.repository;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.LoginProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  LoginProjection findIdByUid(String uid);

  List<User> findByNameLike(String name);

  List<User> findByNameContaining(String name);

  boolean existsByEmailOrName(String email, String name);

  boolean existsByName(String name);

  boolean existsByIdAndDeleteYn(long userId, YesNo deleteYn);

  @Modifying
  @Query("UPDATE User u SET u.deleteYn = 'Y' WHERE u.id = :userId")
  void withdraw(long userId);
}
