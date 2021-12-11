package com.pipiolo.calendar.api.dto;

import com.pipiolo.calendar.core.domain.entity.ScheduleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto implements ScheduleDto {

    private final Long scheduleId;
    private final LocalDateTime notifyAt;
    private final String title;
    private final Long writerId;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.NOTIFICATION;
    }
}
