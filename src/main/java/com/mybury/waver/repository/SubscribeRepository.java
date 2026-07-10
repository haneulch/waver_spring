package com.mybury.waver.repository;

import com.mybury.waver.domain.Subscribe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

  Optional<Subscribe> findTopBySubscribeIdOrderByIdDesc(String subscribeId);

  Optional<Subscribe> findTopByUserIdOrderByStartAtDesc(Long userId);
}
