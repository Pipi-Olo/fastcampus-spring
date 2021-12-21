package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.dto.AuthUser;
import com.pipiolo.calendar.api.dto.EventCreateRequest;
import com.pipiolo.calendar.core.domain.RequestStatus;
import com.pipiolo.calendar.core.domain.entity.Engagement;
import com.pipiolo.calendar.core.domain.entity.Schedule;
import com.pipiolo.calendar.core.domain.entity.User;
import com.pipiolo.calendar.core.domain.repository.EngagementRepository;
import com.pipiolo.calendar.core.domain.repository.ScheduleRepository;
import com.pipiolo.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventService {

    private final UserService userService;
    private final EmailService emailService;

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    @Transactional
    public void create(EventCreateRequest request, AuthUser authUser) {
        // 같은 기간 내에는 다른 약속을 등록할 수 없다.
        final List<Engagement> engagementList = engagementRepository.findAll(); // TODO : findALl 개선
        if (engagementList.stream()
                .anyMatch(e -> request.getAttendeeIds().contains(e.getAttendee().getId()) &&
                        e.getRequestStatus() == RequestStatus.ACCEPTED &&
                        e.getEvent().isOverlapped(request.getStartAt(), request.getEndAt()))
        ) {
            throw new RuntimeException("cannot make engagement. period overlapped!");
        }

        final Schedule eventSchedule = Schedule.event(
                request.getStartAt(),
                request.getEndAt(),
                request.getTitle(),
                request.getDescription(),
                userService.findByUserId(authUser.getId())
        );
        scheduleRepository.save(eventSchedule);
        request.getAttendeeIds()
                .forEach(atId -> {
                    final User attendee = userService.findByUserId(atId);
                    final Engagement engagement = Engagement.builder()
                            .schedule(eventSchedule)
                            .requestStatus(RequestStatus.REQUESTED)
                            .attendee(attendee)
                            .build();
                    engagementRepository.save(engagement);
                    emailService.sendEmail(engagement);
                });
    }
}
