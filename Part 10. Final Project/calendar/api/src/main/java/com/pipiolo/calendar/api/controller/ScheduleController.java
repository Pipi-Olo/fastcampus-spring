package com.pipiolo.calendar.api.controller;

import com.pipiolo.calendar.api.dto.AuthUser;
import com.pipiolo.calendar.api.dto.EventCreateRequest;
import com.pipiolo.calendar.api.dto.TaskCreateRequest;
import com.pipiolo.calendar.api.service.EventService;
import com.pipiolo.calendar.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.pipiolo.calendar.api.service.LoginService.LOGIN_SESSION_KEY;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final TaskService taskService;
    private final EventService eventService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateRequest request,
            AuthUser authUser
    ) {
        taskService.create(request, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(
            @RequestBody EventCreateRequest request,
            AuthUser authUser
    ) {
        eventService.create(request, authUser);
        return ResponseEntity.ok().build();
    }
}
