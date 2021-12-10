package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.core.domain.entity.Engagement;

public interface EmailService {
    void sendEmail(Engagement engagement);
}
