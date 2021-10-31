package com.pipiolo.getinline.repository;

import com.pipiolo.getinline.domain.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DisplayName("DB - Event")
@DataJpaTest
class EventRepositoryTest {

    private final EventRepository sut;

    public EventRepositoryTest(@Autowired EventRepository sut) {
        this.sut = sut;
    }

    @Test
    void test() {
        List<Event> events = sut.findAll();
    }
}