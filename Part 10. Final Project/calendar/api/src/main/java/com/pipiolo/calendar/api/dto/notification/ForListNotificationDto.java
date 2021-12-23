package com.pipiolo.calendar.api.dto.notification;

import com.pipiolo.calendar.api.dto.ForListScheduleDto;
import com.pipiolo.calendar.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ForListNotificationDto implements ForListScheduleDto {
    private final Long scheduleId;
    private final Long writerId;
    private final String title;
    private final LocalDateTime notifyAt;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.NOTIFICATION;
    }
}

