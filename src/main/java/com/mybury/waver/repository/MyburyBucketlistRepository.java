package com.mybury.waver.repository;

import com.mybury.waver.domain.mybury.MyburyBucketlist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyburyBucketlistRepository extends JpaRepository<MyburyBucketlist, String> {

  List<MyburyBucketlist> findByUserIdOrderByOrderSeqAsc(String userId);
}
