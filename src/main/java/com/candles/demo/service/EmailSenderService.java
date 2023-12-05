package com.candles.demo.service;

import com.candles.demo.repository.EmailSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final MailSender mailSender;
    private final EmailSubscriptionRepository emailSubscriptionRepository;

    public void sendSimpleMessage(String subject, String body,
                                  String emailTo) {
        mailSender.send(constructEmail(subject, body, emailTo));
    }

    public void sendSimpleMessageToAll(String subject, String body) {
        emailSubscriptionRepository.findAll()
                .forEach(email -> mailSender.send(constructEmail(subject, body, email.getEmail())));
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             String emailTo) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(emailTo);
        return email;
    }

}
