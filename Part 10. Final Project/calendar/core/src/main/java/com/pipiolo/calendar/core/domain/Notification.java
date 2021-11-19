package com.pipiolo.calendar.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@NoArgsConstructor
public class Notification {

    private Long id;

    private LocalDateTime notifyAt;

    private String title;

    private User writer;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Notification(LocalDateTime notifyAt, String title, User writer) {
        this.notifyAt = notifyAt;
        this.title = title;
        this.writer = writer;
    }
}
