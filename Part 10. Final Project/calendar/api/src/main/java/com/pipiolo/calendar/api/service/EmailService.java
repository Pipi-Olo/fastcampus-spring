package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.controller.api.BatchController;
import com.pipiolo.calendar.api.dto.EngagementEmailStuff;
import com.pipiolo.calendar.core.domain.entity.Engagement;
import com.pipiolo.calendar.core.domain.entity.Share;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff stuff);
    void sendAlarmMail(BatchController.SendMailBatchReq sendMailBatchReq);
    void sendShareRequestMail(String email, String email1, Share.Direction direction);
}
