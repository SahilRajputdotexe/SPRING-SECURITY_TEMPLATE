package com.project.authtemp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.project.authtemp.payload.response.GeneralMessageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private final JavaMailSender mailSender;

    public GeneralMessageResponse sendEMail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        System.out.println("Mail sent successfully");
        return GeneralMessageResponse.builder().isSuccess(true).message("Mail sent successfully").build();

    }

}
