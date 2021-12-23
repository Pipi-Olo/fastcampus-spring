package com.pipiolo.calendar.api.dto.task;

import com.pipiolo.calendar.api.dto.ScheduleDto;
import com.pipiolo.calendar.core.domain.ScheduleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto implements ScheduleDto {

    private final Long scheduleId;
    private final LocalDateTime taskAt;
    private final String title;
    private final String description;
    private final Long writerId;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.TASK;
    }
}
