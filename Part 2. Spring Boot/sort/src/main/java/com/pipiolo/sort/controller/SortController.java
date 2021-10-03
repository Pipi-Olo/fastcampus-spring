package com.pipiolo.sort.controller;

import com.pipiolo.sort.service.SortService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SortController {

    private final SortService sortService;

    @GetMapping("/")
    public String doSort(@RequestParam List<String> list) {
        return sortService.doSort(list).toString();
    }
}
