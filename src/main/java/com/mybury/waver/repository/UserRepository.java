package com.mybury.waver.repository;

import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.IdProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    IdProjection findIdByEmail(String email);
}
