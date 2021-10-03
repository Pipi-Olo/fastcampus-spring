package com.pipiolo.sort.controller;

import com.pipiolo.sort.service.SortService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SortController {

    private final SortService sortService;

    public SortController(SortService sortService) {
        this.sortService = sortService;
    }

    @GetMapping("/")
    public String doSort(@RequestParam List<String> list) {
        return sortService.doSort(list).toString();
    }
}
