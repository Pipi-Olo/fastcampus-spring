package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.dto.AuthUser;
import com.pipiolo.calendar.api.dto.EventCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class EventService {


    @Transactional
    public void create(EventCreateRequest request, AuthUser authUser) {

    }
}
