package com.pipiolo.dmaker.dto;

import com.pipiolo.dmaker.type.DeveloperLevel;
import com.pipiolo.dmaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EditDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {

        /**
         * @NotNull, @Min, @Max : data validation
         *
         * memberId, name : 수정이 불가능한 property
         */

        @NotNull
        private DeveloperLevel developerLevel;

        @NotNull
        private DeveloperSkillType developerSkillType;

        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;
    }
}
