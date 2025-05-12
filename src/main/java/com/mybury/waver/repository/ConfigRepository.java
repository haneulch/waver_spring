package com.mybury.waver.repository;

import com.mybury.waver.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigRepository extends JpaRepository<Config, Integer> {
  List<Config> findByGroupCodeOrderByOrderSeq(String groupCode);
}
