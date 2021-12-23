package com.pipiolo.calendar.api.dto;

import com.pipiolo.calendar.core.domain.type.RequestReplyType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ReplyReq {
    @NotNull
    private RequestReplyType type;
}

