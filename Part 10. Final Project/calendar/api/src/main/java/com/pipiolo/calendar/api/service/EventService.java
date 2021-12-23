package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.dto.user.AuthUser;
import com.pipiolo.calendar.api.dto.event.CreateEventReq;
import com.pipiolo.calendar.api.dto.EngagementEmailStuff;
import com.pipiolo.calendar.core.domain.RequestStatus;
import com.pipiolo.calendar.core.domain.entity.Engagement;
import com.pipiolo.calendar.core.domain.entity.Schedule;
import com.pipiolo.calendar.core.domain.entity.User;
import com.pipiolo.calendar.core.domain.repository.EngagementRepository;
import com.pipiolo.calendar.core.domain.repository.ScheduleRepository;
import com.pipiolo.calendar.core.excpetion.CalendarException;
import com.pipiolo.calendar.core.excpetion.ErrorCode;
import com.pipiolo.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class EventService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;
    private final EmailService emailService;

    @Transactional
    public void create(CreateEventReq req, AuthUser authUser) {
        // attendees 의 스케쥴 시간과 겹치지 않는지?
        final List<Engagement> engagementList =
                engagementRepository.findAllByAttendeeIdInAndSchedule_EndAtAfter(req.getAttendeeIds(),
                        req.getStartAt());
        if (engagementList
                .stream()
                .anyMatch(e -> e.getEvent().isOverlapped(req.getStartAt(), req.getEndAt())
                        && e.getRequestStatus() == RequestStatus.ACCEPTED)) {
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }
        final Schedule eventSchedule = Schedule.event(req.getTitle(), req.getDescription(), req.getStartAt(), req.getEndAt(), userService.findByUserId(authUser.getId()));

        scheduleRepository.save(eventSchedule);

        final List<User> attendeeList = req.getAttendeeIds().stream().map(userService::findByUserId).collect(toList());

        attendeeList.forEach(user -> {
            final Engagement e = engagementRepository.save(new Engagement(eventSchedule, user));
            emailService.sendEngagement(
                    new EngagementEmailStuff(
                            e.getId(),
                            e.getAttendee().getEmail(),
                            attendeeList.stream().map(User::getEmail).collect(toList()),
                            e.getEvent().getTitle(),
                            e.getEvent().getPeriod()
                    ));
        });
    }

}
