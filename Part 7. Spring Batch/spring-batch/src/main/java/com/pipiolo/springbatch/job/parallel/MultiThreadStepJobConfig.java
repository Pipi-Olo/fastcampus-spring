package com.pipiolo.springbatch.job.parallel;

import com.pipiolo.springbatch.dto.AmountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class MultiThreadStepJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job multiThreadJob(Step multiThreadStep) {
        return  jobBuilderFactory.get("multiThreadJob")
                .incrementer(new RunIdIncrementer())
                .start(multiThreadStep)
                .build();
    }

    @JobScope
    @Bean
    public Step multiThreadStep(
            FlatFileItemReader<AmountDto> amountFileItemReader,
            ItemProcessor<AmountDto, AmountDto> amountItemProcessor,
            FlatFileItemWriter<AmountDto> amountFileItemWriter,
            TaskExecutor taskExecutor
    ) {
        return stepBuilderFactory.get("multiThreadStep")
                .<AmountDto, AmountDto>chunk(10)
                .reader(amountFileItemReader)
                .processor(amountItemProcessor)
                .writer(amountFileItemWriter)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("spring-batch-executor");
        taskExecutor.setConcurrencyLimit(4);
        return taskExecutor;
    }

    @StepScope
    @Bean
    public FlatFileItemReader<AmountDto> amountFileItemReader() {
        return new FlatFileItemReaderBuilder<AmountDto>()
                .name("amountFileItemReader")
                .fieldSetMapper(new AmountFieldSetMapper())
                .resource(new FileSystemResource("data/input.txt"))
                .lineTokenizer(new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<AmountDto, AmountDto> amountItemProcessor() {
        return item -> {
            System.out.println(item + "\tThread = " + Thread.currentThread().getName());
            item.setAmount(item.getAmount() * 100);
            return item;
        };
    }

    @StepScope
    @Bean
    public FlatFileItemWriter<AmountDto> amountFileItemWriter() throws IOException {
        BeanWrapperFieldExtractor<AmountDto> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"index", "name", "amount"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<AmountDto> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setFieldExtractor(fieldExtractor);

        String filePath = "data/output.txt";
        new File(filePath).createNewFile();

        return new FlatFileItemWriterBuilder<AmountDto>()
                .name("amountFileItemWriter")
                .resource(new FileSystemResource(filePath))
                .lineAggregator(lineAggregator)
                .build();
    }
}
