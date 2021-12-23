package com.pipiolo.calendar.api.dto.event;

import com.pipiolo.calendar.api.dto.ForListScheduleDto;
import com.pipiolo.calendar.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ForListEventDto implements ForListScheduleDto {
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
