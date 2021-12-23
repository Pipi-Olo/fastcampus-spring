package com.pipiolo.calendar.api.dto.share;

import com.pipiolo.calendar.core.domain.entity.Share;
import lombok.Data;

@Data
public class CreateShareReq {
    private final Long toUserId;
    private final Share.Direction direction;
}

