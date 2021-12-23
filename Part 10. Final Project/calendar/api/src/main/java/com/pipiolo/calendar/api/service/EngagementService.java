package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.dto.user.AuthUser;
import com.pipiolo.calendar.core.domain.RequestStatus;
import com.pipiolo.calendar.core.domain.entity.Engagement;
import com.pipiolo.calendar.core.domain.repository.EngagementRepository;
import com.pipiolo.calendar.core.domain.type.RequestReplyType;
import com.pipiolo.calendar.core.excpetion.CalendarException;
import com.pipiolo.calendar.core.excpetion.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EngagementService {

    private final EngagementRepository engagementRepository;

    @Transactional
    public RequestStatus update(AuthUser authUser, Long engagementId, RequestReplyType type) {
        return engagementRepository.findById(engagementId)
                .filter(Engagement::isRequested)
                .filter(e -> e.getAttendee().getId().equals(authUser.getId()))
                .map(e -> e.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST))
                .getRequestStatus();
    }
}

