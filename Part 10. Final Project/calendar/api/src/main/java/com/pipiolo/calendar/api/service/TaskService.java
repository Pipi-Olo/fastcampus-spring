package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.dto.user.AuthUser;
import com.pipiolo.calendar.api.dto.task.CreateTaskReq;
import com.pipiolo.calendar.core.domain.entity.Schedule;
import com.pipiolo.calendar.core.domain.repository.ScheduleRepository;
import com.pipiolo.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(CreateTaskReq request, AuthUser authUser) {
        final Schedule taskSchedule = Schedule.task(
                request.getTaskAt(),
                request.getTitle(),
                request.getDescription(),
                userService.findByUserId(authUser.getId())
        );
        scheduleRepository.save(taskSchedule);
    }
}
