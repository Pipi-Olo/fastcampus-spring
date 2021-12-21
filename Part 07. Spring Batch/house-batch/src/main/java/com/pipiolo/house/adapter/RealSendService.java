package com.pipiolo.house.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RealSendService implements SendService{

    private final JavaMailSender mailSender;

    @Override
    public void send(String email, String message) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(email);
            helper.setSubject("Title");
            helper.setText(message);
        };

        mailSender.send(preparator);
    }
}

