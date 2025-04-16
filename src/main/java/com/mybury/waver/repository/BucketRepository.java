package com.mybury.waver.repository;

import com.mybury.waver.domain.Bucket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {

    List<Bucket> findByUser_Id(Long userId);
}
