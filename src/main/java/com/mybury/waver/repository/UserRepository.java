package com.mybury.waver.repository;

import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.LoginProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

  LoginProjection findIdByEmail(String email);

  List<User> findByNameLike(String name);

  boolean existsByEmailOrName(String email, String name);

  boolean existsByName(String name);
}
