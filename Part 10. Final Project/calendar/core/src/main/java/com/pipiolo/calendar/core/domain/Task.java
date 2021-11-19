package com.pipiolo.calendar.core.domain;

import com.pipiolo.calendar.core.domain.entity.Schedule;
import com.pipiolo.calendar.core.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.scheduling.annotation.Schedules;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Task {

    private Schedule schedule;

    public Task(Schedule schedule) {
        this.schedule = schedule;
    }
}
