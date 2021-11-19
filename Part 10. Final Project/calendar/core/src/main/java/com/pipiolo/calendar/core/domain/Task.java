package com.pipiolo.calendar.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Task {

    private Long id;

    private LocalDateTime taskAt;

    private String title;

    private String description;

    private User writer;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Task(LocalDateTime taskAt, String title, String description, User writer) {
        this.taskAt = taskAt;
        this.title = title;
        this.description = description;
        this.writer = writer;
    }
}
