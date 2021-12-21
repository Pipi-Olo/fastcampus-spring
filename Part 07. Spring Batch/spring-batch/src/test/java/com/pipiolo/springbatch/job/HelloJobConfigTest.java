package com.pipiolo.springbatch.job;

import com.pipiolo.springbatch.BatchTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HelloJobConfig.class, BatchTestConfig.class})
class HelloJobConfigTest {

    /**
     * @ContextConfiguration(classes = {HelloJobConfig.class})
     *  jobLauncherTestUtils.setJob() 에 HelloJobConfig.class 가 자동 등록
     */
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void success() throws Exception {
        // When
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // Then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
    }
}