package com.pipiolo.getinline.controller;

import com.pipiolo.getinline.constant.EventStatus;
import com.pipiolo.getinline.dto.EventResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/events")
@Controller
public class EventController {

    @GetMapping
    public ModelAndView events() {
        Map<String, Object> map = new HashMap<>();

        // TODO : 임시 데이터, 추후 삭제 예정
        map.put("events", List.of(
                EventResponse.of(
                        1L,
                        "eventName",
                        EventStatus.OPENED,
                        LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                        LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                        0,
                        10,
                        "testMemo"),
                EventResponse.of(
                        2L,
                        "eventName2",
                        EventStatus.OPENED,
                        LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                        LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                        0,
                        10,
                        "testMemo2")
        ));

        return new ModelAndView("event/index", map);
    }

    @GetMapping("/{eventId}")
    public ModelAndView eventDetail(@PathVariable Long eventId) {
        Map<String, Object> map = new HashMap<>();
        map.put("events", EventResponse.of(
                        1L,
                        "eventName",
                        EventStatus.OPENED,
                        LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                        LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                        0,
                        10,
                        "testMemo")
        );

        return new ModelAndView("event/detail", map);
    }
}
