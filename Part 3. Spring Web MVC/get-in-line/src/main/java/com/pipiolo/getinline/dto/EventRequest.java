package com.pipiolo.getinline.dto;

import com.pipiolo.getinline.constant.EventStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public record EventRequest(
        @NotNull @Positive Long placeId,
        @NotBlank @Size(min = 2) String eventName,
        @NotNull EventStatus eventStatus,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDateTime,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDateTime,
        @NotNull @PositiveOrZero Integer currentNumberOfPeople,
        @NotNull @Positive Integer capacity,
        String memo
) {
    public static EventRequest of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventRequest(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public EventDTO toDTO() {
        return EventDTO.of(
                this.placeId(),
                this.eventName(),
                this.eventStatus(),
                this.eventStartDateTime(),
                this.eventEndDateTime(),
                this.currentNumberOfPeople(),
                this.capacity(),
                this.memo()
        );
    }
}