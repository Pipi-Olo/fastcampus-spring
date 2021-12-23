package com.pipiolo.calendar.api.dto;

import com.pipiolo.calendar.api.dto.event.ForListEventDto;
import com.pipiolo.calendar.api.dto.notification.ForListNotificationDto;
import com.pipiolo.calendar.api.dto.task.ForListTaskDto;
import com.pipiolo.calendar.core.domain.entity.Schedule;
import com.pipiolo.calendar.core.excpetion.CalendarException;
import com.pipiolo.calendar.core.excpetion.ErrorCode;

public abstract class DtoConverter {

    public static ForListScheduleDto toForListDto(Schedule schedule) {
        switch (schedule.getScheduleType()) {
            case EVENT:
                return ForListEventDto.builder()
                        .scheduleId(schedule.getId())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case TASK:
                return ForListTaskDto.builder()
                        .scheduleId(schedule.getId())
                        .taskAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case NOTIFICATION:
                return ForListNotificationDto.builder()
                        .scheduleId(schedule.getId())
                        .notifyAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            default:
                throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
    }
}

