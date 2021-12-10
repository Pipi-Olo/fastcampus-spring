package com.pipiolo.calendar.core.domain;

import com.pipiolo.calendar.core.domain.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Event {

    private Schedule schedule;

    public Event(Schedule schedule) {
        this.schedule = schedule;
    }

    public boolean isOverlapped(
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        return schedule.getStartAt().isBefore(endAt) &&
                schedule.getEndAt().isBefore(startAt);
    }
}
