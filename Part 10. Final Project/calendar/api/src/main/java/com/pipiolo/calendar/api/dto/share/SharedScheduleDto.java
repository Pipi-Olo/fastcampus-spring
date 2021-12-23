package com.pipiolo.calendar.api.dto.share;

import com.pipiolo.calendar.api.dto.ForListScheduleDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SharedScheduleDto {
    private final Long userId;
    private final String name;
    private final Boolean me;
    private final List<ForListScheduleDto> schedules;
}
