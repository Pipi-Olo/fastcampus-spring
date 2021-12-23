package com.pipiolo.calendar.core.domain.entity;

import com.pipiolo.calendar.core.domain.Event;
import com.pipiolo.calendar.core.domain.Notification;
import com.pipiolo.calendar.core.domain.ScheduleType;
import com.pipiolo.calendar.core.domain.Task;
import com.pipiolo.calendar.core.util.Period;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Entity
public class Schedule extends BaseEntity {

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private String title;

    private String description;

    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    public static Schedule event(LocalDateTime startAt, LocalDateTime endAt, String title, String description, User writer) {
        return Schedule.builder()
                .startAt(startAt)
                .endAt(endAt)
                .title(title)
                .description(description)
                .writer(writer)
                .scheduleType(ScheduleType.EVENT)
                .build();
    }

    public static Schedule task(LocalDateTime taskAt, String title, String description, User writer) {
        return Schedule.builder()
                .startAt(taskAt)
                .title(title)
                .description(description)
                .writer(writer)
                .scheduleType(ScheduleType.TASK)
                .build();
    }

    public static Schedule notification(LocalDateTime notifyAt, String title, User writer) {
        return Schedule.builder()
                .startAt(notifyAt)
                .title(title)
                .writer(writer)
                .scheduleType(ScheduleType.NOTIFICATION)
                .build();
    }

    public Task toTask() {
        return new Task(this);
    }

    public Event toEvent() {
        return new Event(this);
    }

    public Notification toNotification() {
        return new Notification(this);
    }

    public boolean isOverlapped(LocalDate date) {
        return Period.of(this.getStartAt(), this.getEndAt()).isOverlapped(date);
    }

    public boolean isOverlapped(Period period) {
        return Period.of(this.getStartAt(), this.getEndAt()).isOverlapped(period);
    }
}
