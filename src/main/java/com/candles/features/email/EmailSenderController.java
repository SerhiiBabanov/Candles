package com.candles.features.email;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/email")
public class EmailSenderController{

    private final EmailSenderService emailSenderService;

    @RequestMapping("/send")
    public void sendEmail(@RequestParam(name = "subject") String subject, @RequestParam(name = "body") String body, @RequestParam(name = "emailTo") String emailTo) {
        emailSenderService.sendSimpleMessage(subject, body, emailTo);
    }

    @RequestMapping("/sendToAll")
    public void sendEmailToAll(@RequestParam(name = "subject") String subject, @RequestParam(name = "body") String body) {
        emailSenderService.sendSimpleMessageToAll(subject, body);
    }
}
