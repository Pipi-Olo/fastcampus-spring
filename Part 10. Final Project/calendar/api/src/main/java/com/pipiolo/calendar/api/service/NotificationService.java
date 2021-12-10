package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.dto.AuthUser;
import com.pipiolo.calendar.api.dto.NotificationCreateRequest;
import com.pipiolo.calendar.core.domain.entity.Schedule;
import com.pipiolo.calendar.core.domain.entity.User;
import com.pipiolo.calendar.core.domain.repository.ScheduleRepository;
import com.pipiolo.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(NotificationCreateRequest request, AuthUser authUser) {
        final User user = userService.findByUserId(authUser.getId());

        final List<LocalDateTime> notifyAtList = request.getRepeatTimes();
        notifyAtList.forEach(notifyAt -> {
            final Schedule notificationScheduler =
                    Schedule.notification(
                            request.getNotifyAt(),
                            request.getTitle(),
                            user
                    );
            scheduleRepository.save(notificationScheduler);
        });


    }
}
