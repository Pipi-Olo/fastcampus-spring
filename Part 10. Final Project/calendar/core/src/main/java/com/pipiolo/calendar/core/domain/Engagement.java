package com.pipiolo.calendar.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Engagement {

    private Long id;

    private Event event;

    private User attendee;

    private RequestStatus requestStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Engagement(Event event, User attendee, RequestStatus requestStatus) {
        this.event = event;
        this.attendee = attendee;
        this.requestStatus = requestStatus;
    }
}
