package com.candles.features.orderNotification.email;

import com.candles.features.email.EmailSenderService;
import com.candles.features.landTranslateSupport.Local;
import com.candles.features.order.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailNotifierService {
    private final EmailSenderService emailSenderService;
    private final TemplateEngine htmlTemplateEngine;
    private static final String EMAIL_TEXT_TEMPLATE_UA = "order-confirmation-ua.html";
    private static final String EMAIL_TEXT_TEMPLATE_EN = "order-confirmation-en.html";

    public void sendOrderNotification(Order order, Local lang) {
        try {
            //send to admin
            emailSenderService.sendEmail(createOrderConfirmationEmail(order, lang, emailSenderService.getSenderEmail()));
            //send to customer
            emailSenderService.sendEmail(createOrderConfirmationEmail(order, lang, order.getCustomer().getEmail()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public MimeMessage createOrderConfirmationEmail(Order order, Local lang, String email) throws MessagingException {
        String subject = "Order Confirmation";
        String body;
        final Context ctx = new Context();
        ctx.setVariable("order", order);
        if (lang.name().equals("UA")) {
            body = htmlTemplateEngine.process(EMAIL_TEXT_TEMPLATE_UA, ctx);
        } else {
            body = htmlTemplateEngine.process(EMAIL_TEXT_TEMPLATE_EN, ctx);
        }
        final MimeMessage mimeMessage = emailSenderService.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom(emailSenderService.getSenderEmail());
        message.setTo(email);
        message.setText(body, true /* isHtml */);
        return mimeMessage;

    }
}
