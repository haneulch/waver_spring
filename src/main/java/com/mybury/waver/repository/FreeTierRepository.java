package com.mybury.waver.repository;

import com.mybury.waver.domain.FreeTier;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeTierRepository extends JpaRepository<FreeTier, Long> {

  Optional<FreeTier> findByUserId(long userId);
}
