package com.mybury.waver.repository;

import com.mybury.waver.domain.mybury.MyburyCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyburyCategoryRepository extends JpaRepository<MyburyCategory, String> {

  List<MyburyCategory> findByUserIdOrderByPriorityAsc(String userId);
}
