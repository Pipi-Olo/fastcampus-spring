package com.pipiolo.calendar.core.domain;

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

    private Long id;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private String title;

    private String description;

    private User writer;

    private List<Engagement> engagements;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Event(LocalDateTime startAt, LocalDateTime endAt, String title, String description, User writer, List<Engagement> engagements) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.engagements = engagements;
    }
}
