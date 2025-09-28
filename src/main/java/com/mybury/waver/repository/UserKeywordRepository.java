package com.mybury.waver.repository;

import com.mybury.waver.domain.UserKeyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {

  List<UserKeyword> findByUserId(long userId);
}
