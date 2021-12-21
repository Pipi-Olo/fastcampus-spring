package com.pipiolo.springbatch.job.parallel;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@RequiredArgsConstructor
@Configuration
public class PartitionJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final int PARTITION_SIZE = 100;

    @Bean
    public Job partitionJob(Step masterStep) {
        return jobBuilderFactory.get("partitionJob")
                .start(masterStep)
                .build();
    }

    @Bean
    public Step masterStep(
            Partitioner partitioner,
            PartitionHandler partitionHandler
    ) {
        return stepBuilderFactory.get("masterStep")
                .partitioner("anotherStep", partitioner)
                .partitionHandler(partitionHandler)
                .build();
    }

    @StepScope
    @Bean
    public Partitioner partitioner() {
        SimplePartitioner partitioner = new SimplePartitioner();
        partitioner.partition(PARTITION_SIZE);
        return partitioner;
    }

    @StepScope
    @Bean
    public PartitionHandler partitionHandler(
            Step anotherStep,
            TaskExecutor taskExecutor
    ) {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setStep(anotherStep);
        partitionHandler.setGridSize(PARTITION_SIZE);
        partitionHandler.setTaskExecutor(taskExecutor);

        return partitionHandler;
    }
}
