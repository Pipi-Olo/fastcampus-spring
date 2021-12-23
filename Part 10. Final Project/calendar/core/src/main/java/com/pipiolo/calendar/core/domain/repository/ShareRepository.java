package com.pipiolo.calendar.core.domain.repository;

import com.pipiolo.calendar.core.domain.RequestStatus;
import com.pipiolo.calendar.core.domain.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long> {

    @Query("SELECT s FROM Share s WHERE (s.fromUserId = ?1 or s.toUserId = ?1) and s.requestStatus = ?2 and s.direction = ?3")
    List<Share> findAllByBiDirection(
            Long fromUserId,
            RequestStatus requestStatus,
            Share.Direction direction
    );

    List<Share> findAllByToUserIdAndRequestStatusAndDirection(Long fromUser,
                                                              RequestStatus requestStatus,
                                                              Share.Direction direction
    );
}
