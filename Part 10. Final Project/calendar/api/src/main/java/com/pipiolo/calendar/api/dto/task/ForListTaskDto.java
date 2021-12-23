package com.pipiolo.calendar.api.dto.task;

import com.pipiolo.calendar.api.dto.ForListScheduleDto;
import com.pipiolo.calendar.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ForListTaskDto implements ForListScheduleDto {
    private final Long scheduleId;
    private final Long writerId;
    private final String title;
    private final String description;
    private final LocalDateTime taskAt;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.TASK;
    }
}

