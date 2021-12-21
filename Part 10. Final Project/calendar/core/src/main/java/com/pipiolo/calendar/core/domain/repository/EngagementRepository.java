package com.pipiolo.calendar.core.domain.repository;

import com.pipiolo.calendar.core.domain.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
}
