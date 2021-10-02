package com.pipiolo.dmaker;

import com.pipiolo.dmaker.dto.DeveloperDto;
import com.pipiolo.dmaker.service.DMakerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Real Application 환경을 구성하여 Test 진행
 * Default : ALL Bean 자동 등록
 */

@SpringBootTest
class DmakerApplicationTests {

    @Autowired
    private DMakerService dMakerService;

    @Test
    public void testSomething() {
        List<DeveloperDto> allEmployedDevelopers = dMakerService.getAllEmployedDevelopers();
        System.out.println(allEmployedDevelopers);
    }
}
o