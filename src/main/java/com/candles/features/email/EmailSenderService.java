package com.candles.features.email;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.order.Order;
import com.candles.features.subscription.EmailSubscriptionRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailSubscriptionRepository emailSubscriptionRepository;
    private final TemplateEngine htmlTemplateEngine;
    @Value("${spring.mail.username}")
    private String senderEmail;
    private static final String EMAIL_TEXT_TEMPLATE_UA = "order-confirmation-ua.html";
    private static final String EMAIL_TEXT_TEMPLATE_EN = "order-confirmation-en.html";

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
    public void sendOrderConfirmationEmail(Order order, Local lang) throws MessagingException {
        String subject = "Order Confirmation";
        String body;
        final Context ctx = new Context();
        ctx.setVariable("order", order);
        if (lang.name().equals("UA")) {
            body = htmlTemplateEngine.process(EMAIL_TEXT_TEMPLATE_UA, ctx);
        } else {
            body = htmlTemplateEngine.process(EMAIL_TEXT_TEMPLATE_EN, ctx);
        }
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom(senderEmail);
        message.setTo(order.getCustomer().getEmail());
        message.setText(body, true /* isHtml */);

        mailSender.send(mimeMessage);

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

}
