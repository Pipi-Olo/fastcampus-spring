package com.pipiolo.calendar.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventCreateRequest {
    private final String title;
    private final String description;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final List<Long> attendeeIds;
}
