package com.pipiolo.calendar.api.dto;

import com.pipiolo.calendar.core.domain.entity.ScheduleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto implements ScheduleDto {

    private final Long scheduleId;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final String title;
    private final String description;
    private final Long writerId;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.EVENT;
    }
}
