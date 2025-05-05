package com.mybury.waver.repository;

import com.mybury.waver.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BucketRepository extends JpaRepository<Bucket, Long>, BucketRepositoryCustom {
  List<Bucket> findByUserId(Long userId);

}
