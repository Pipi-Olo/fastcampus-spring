package com.pipiolo.calendar.api;

import com.pipiolo.calendar.core.SimpleEntity;
import com.pipiolo.calendar.core.SimpleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@SpringBootApplication(scanBasePackages = "com.pipiolo.calendar")
public class ApiApplication {

    private final SimpleEntityRepository repository;

    @GetMapping
    public List<SimpleEntity> findAll() {
        return repository.findAll();
    }

    @PostMapping("/save")
    public SimpleEntity save() {
        SimpleEntity entity = new SimpleEntity();
        entity.setName("Hello World");
        return repository.save(entity);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
