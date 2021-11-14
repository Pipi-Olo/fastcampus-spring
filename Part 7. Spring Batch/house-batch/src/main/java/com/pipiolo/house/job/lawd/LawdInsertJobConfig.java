package com.pipiolo.house.job.lawd;

import com.pipiolo.house.core.entity.Lawd;
import com.pipiolo.house.job.validator.FilePathParameterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

import static com.pipiolo.house.job.lawd.LawdFiledSetMapper.*;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class LawdInsertJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job lawdInsertJob(
            Step lawdInsertStep
    ) {
        return jobBuilderFactory.get("lawdInsertJob")
                .incrementer(new RunIdIncrementer())
                .validator(new FilePathParameterValidator())
                .start(lawdInsertStep)
                .build();
    }

    @JobScope
    @Bean
    public Step lawdInsertStep(
            FlatFileItemReader<Lawd> lawdFileItemReader,
            ItemWriter<Lawd> lawdItemWriter
    ) {
        return stepBuilderFactory.get("lawdInsertStep")
                .<Lawd, Lawd>chunk(1000)
                .reader(lawdFileItemReader)
                .writer(lawdItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<Lawd> lawdFileItemReader(@Value("#{jobParameters['filePath']}") String filePath) {
        return new FlatFileItemReaderBuilder<Lawd>()
                .name("lawdFileItemReader")
                .delimited()
                .delimiter("\t")
                .names(LAWD_CD, LAWD_DONG, EXIST)
                .linesToSkip(1)
                .fieldSetMapper(new LawdFiledSetMapper())
                .resource(new ClassPathResource(filePath))
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<Lawd> lawdItemWriter() {
        return new ItemWriter<Lawd>() {
            @Override
            public void write(List<? extends Lawd> items) throws Exception {
                items.forEach(System.out::println);
            }
        };
    }
}
