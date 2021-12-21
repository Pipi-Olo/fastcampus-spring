package com.pipiolo.springbatch.job;

import com.pipiolo.springbatch.BatchTestConfig;
import com.pipiolo.springbatch.core.domain.PlainText;
import com.pipiolo.springbatch.core.repository.PlainTextRepository;
import com.pipiolo.springbatch.core.repository.ResultTextRepository;
import org.junit.jupiter.api.AfterEach;
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

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {PlainTextJobConfig.class, BatchTestConfig.class})
class PlainTextJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlainTextRepository plainTextRepository;

    @Autowired
    private ResultTextRepository resultTextRepository;

    @AfterEach
    void tearDown() {
        plainTextRepository.deleteAll();
        resultTextRepository.deleteAll();
    }

    @Test
    void givenNothing_thenReturnsSuccess() throws Exception {
        // When
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // Then
        assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        assertEquals(resultTextRepository.count(), 0);
    }

    @Test
    void givenPlainTexts_thenReturnsSuccess() throws Exception {
        // Given
        givenPlainTexts(12);

        // When
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // Then
        assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        assertEquals(resultTextRepository.count(), 12);
    }

    private void givenPlainTexts(Integer count) {
        IntStream.range(0, count)
                .forEach(
                        num -> plainTextRepository.save(new PlainText(null, "text" + num))
                );
    }
}