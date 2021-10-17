package com.pipiolo.getinline.dto;

import com.pipiolo.getinline.constant.EventStatus;

import java.time.LocalDateTime;

public record EventResponse(
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDateTime,
        LocalDateTime eventEndDateTime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo
) {
    public static EventResponse of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventResponse(placeId, eventName, eventStatus,
                eventStartDateTime, eventEndDateTime,
                currentNumberOfPeople, capacity, memo);
    }
}