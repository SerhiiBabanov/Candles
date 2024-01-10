package com.candles.features.email;

import com.candles.features.subscription.EmailSubscriptionRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailSubscriptionRepository emailSubscriptionRepository;
    @Value("${spring.mail.username}")
    @Getter
    private String senderEmail;

    public void sendEmail(String subject, String body,
                          String emailTo) {
        mailSender.send(constructEmail(subject, body, emailTo));
    }

    public void sendEmail(String subject, String body,
                          String emailTo, String emailCC) {
        mailSender.send(constructEmail(subject, body, emailTo, emailCC));
    }

    public void sendEmailToAll(String subject, String body) {
        emailSubscriptionRepository.findAll()
                .forEach(email -> mailSender.send(constructEmail(subject, body, email.getEmail())));
    }

    @Async
    public void sendEmail(MimeMessage message) throws MessagingException {
        //send to customer
        mailSender.send(message);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             String emailTo) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(emailTo);
        return email;
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             String emailTo, String emailCC) {
        SimpleMailMessage email = constructEmail(subject, body, emailTo);
        email.setCc(emailCC);
        return email;
    }

    public MimeMessage createMimeMessage() {
        return mailSender.createMimeMessage();
    }
}
