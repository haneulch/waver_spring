package com.mybury.waver.repository;

import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.IdProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

  IdProjection findIdByEmail(String email);

  List<User> findByNameLike(String name);
}
