package com.pipiolo.dmaker.controller;

import com.pipiolo.dmaker.dto.CreateDeveloper;
import com.pipiolo.dmaker.dto.DeveloperDetailDto;
import com.pipiolo.dmaker.dto.DeveloperDto;
import com.pipiolo.dmaker.dto.EditDeveloper;
import com.pipiolo.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Slf4j : log.info()
 * @RestController : save DMakerController class with RestController Bean
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable final String memberId) {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    // @RequestBody : http protocol 에서 requestBody 에 해당하는 데이터를 해당 변수에 넣는다.
    // @Valid : Request class 에서 작성한 data validation 사용
    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody final CreateDeveloper.Request request) {
        log.info("Request : {}", request);

        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(
            @PathVariable final String memberId, @Valid @RequestBody final EditDeveloper.Request request) {

        log.info("GET /developers HTTP/1.1");

        return dMakerService.editDeveloper(memberId, request);
    }

    /**
     * do not delete real data
     * move from Developer Class to RetiredDeveloper class
     * see Transactional atomic feature
     */
    @DeleteMapping("/developer/{memberId}")
    public void deleteDeveloper(@PathVariable final String memberId) {
        dMakerService.deleteDeveloper(memberId);
    }
}
