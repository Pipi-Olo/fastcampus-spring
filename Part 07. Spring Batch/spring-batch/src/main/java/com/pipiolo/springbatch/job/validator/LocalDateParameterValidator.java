package com.pipiolo.springbatch.job.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RequiredArgsConstructor
public class LocalDateParameterValidator implements JobParametersValidator {

    private final String parameterName;

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String localDate = parameters.getString(parameterName);

        if (!StringUtils.hasText(localDate)) {
            throw new JobParametersInvalidException(parameterName + "가 빈 문자열이거나 존재하지 않습니다.");
        }

        try {
            LocalDate.parse(localDate);
        } catch (DateTimeParseException e) {
            throw new JobParametersInvalidException(parameterName + "가 날짜 형식의 데이터가 아닙니다.");
        }
    }
}
