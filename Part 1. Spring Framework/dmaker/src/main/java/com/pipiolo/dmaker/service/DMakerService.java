package com.pipiolo.dmaker.service;

import com.pipiolo.dmaker.constant.StatusCode;
import com.pipiolo.dmaker.dto.CreateDeveloper;
import com.pipiolo.dmaker.dto.DeveloperDetailDto;
import com.pipiolo.dmaker.dto.DeveloperDto;
import com.pipiolo.dmaker.dto.EditDeveloper;
import com.pipiolo.dmaker.entity.Developer;
import com.pipiolo.dmaker.entity.RetiredDeveloper;
import com.pipiolo.dmaker.exception.DMakerException;
import com.pipiolo.dmaker.repository.DeveloperRepository;
import com.pipiolo.dmaker.repository.RetiredDeveloperRepository;
import com.pipiolo.dmaker.constant.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.pipiolo.dmaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DMakerService {

    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;


    /**
     * @Transactional 4 Features ACID
     *  Atomic
     *  Consistency
     *  Isolation
     *  Durability
     */

    /**
     * Validation
     *  과거에는 try ~ catch 를 통해 예외를 확인, return boolean value
     *
     *  boolean validationResult = validateCreateDeveloperRequest(request);
     *  if (!validationResult) {
     *      ~~~
     *  }
     *
     *  반환한 boolean value 값을 받아주는 변수와 해당 변수를 체크하는 if ~ else 필요
     *
     *  1. 중복 코드, 재활용성 -_-
     *  2. 확장성 저하
     *  -> Controller 에 exceptionHandler 메소드 생성
     */

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);
    }

    @Transactional(readOnly = true)
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }

    /**
     * EMPLOYED -> RETIRED
     * 1. set retired status code in developer
     * 2. save into retiredDeveloper
     *
     * @Transactional : Atomic, 1. 2. 중간에 Error 발생하면, Rollback
     */
    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {

        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);
        developerRepository.save(developer);

        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .age(developer.getAge())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }

    /**
     * Business Validation
     * @NotNull : Null-safety
     */
    private void validateCreateDeveloperRequest(@NotNull CreateDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));
    }

    /**
     * Magic Number : Business Model(Validation) Error 발생하는 특정 고정 값
     *                ex) Senior, 8 years -> Senior 는 10년 이상의 개발자를 의미한다는 Business Validation 으로 코드상 오류는 없지만,
     *                                       에러가 발생한다.
     *                                    -> 10 Years : Magic Number
     */
    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR
                && experienceYears > 3) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }


}
