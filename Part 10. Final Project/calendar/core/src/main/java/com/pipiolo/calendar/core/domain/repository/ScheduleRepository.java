package com.pipiolo.calendar.core.domain.repository;

import com.pipiolo.calendar.core.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
