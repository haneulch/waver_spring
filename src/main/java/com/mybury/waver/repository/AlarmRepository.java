package com.mybury.waver.repository;

import com.mybury.waver.domain.Alarm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByUserId(long userId);
}
