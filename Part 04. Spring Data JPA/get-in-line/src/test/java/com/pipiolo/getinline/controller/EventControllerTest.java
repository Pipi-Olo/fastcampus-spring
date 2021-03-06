package com.pipiolo.getinline.controller;

import com.pipiolo.getinline.config.SecurityConfig;
import com.pipiolo.getinline.dto.EventDTO;
import com.pipiolo.getinline.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 이벤트")
@WebMvcTest(
        controllers = EventController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
)
class EventControllerTest {

    private final MockMvc mvc;

    @MockBean private EventService eventService;

    public EventControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 이벤트 리스트 페이지")
    @Test
    void givenNothing_whenRequestingEventsPage_thenReturnsEventsPage() throws Exception {
        // Given
        given(eventService.getEvents(any())).willReturn(List.of());

        // When & Then
        mvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("event/index"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("events"));
        then(eventService).should().getEvents(any());
    }

    @DisplayName("[view][GET] 이벤트 세부 정보 페이지")
    @Test
    void givenEventId_whenRequestingEventDetailPage_thenReturnsEventDetailPage() throws Exception {
        // Given
        long eventId = 1L;
        given(eventService.getEvent(eventId)).willReturn(Optional.of(
                EventDTO.of(eventId, null, null, null, null, null, null, null, null, null, null)
        ));

        // When & Then
        mvc.perform(get("/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("event/detail"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("event"));
        then(eventService).should().getEvent(eventId);
    }

    @DisplayName("[view][GET] 이벤트 세부 정보 페이지 - 데이터 없음")
    @Test
    void givenNonexistentEventId_whenRequestingEventDetailPage_thenReturnsErrorPage() throws Exception {
        // Given
        long eventId = 0L;
        given(eventService.getEvent(eventId)).willReturn(Optional.empty());

        // When & Then
        mvc.perform(get("/events/" + eventId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("error"));
        then(eventService).should().getEvent(eventId);
    }

}
