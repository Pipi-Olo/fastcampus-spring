package com.pipiolo.calendar.core.domain;

import com.pipiolo.calendar.core.domain.entity.Engagement;
import com.pipiolo.calendar.core.domain.entity.Schedule;
import com.pipiolo.calendar.core.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class Event {

    private Schedule schedule;

    public Event(Schedule schedule) {
        this.schedule = schedule;
    }
}
