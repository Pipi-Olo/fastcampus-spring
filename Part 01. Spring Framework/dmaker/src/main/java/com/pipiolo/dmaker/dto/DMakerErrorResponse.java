package com.pipiolo.dmaker.dto;

import com.pipiolo.dmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMassage;
}
