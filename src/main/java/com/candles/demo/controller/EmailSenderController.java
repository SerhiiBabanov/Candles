package com.candles.demo.controller;

import com.candles.demo.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    @RequestMapping("/send")
    public void sendEmail(@RequestParam String subject, @RequestParam String body, @RequestParam String emailTo) {
        emailSenderService.sendSimpleMessage(subject, body, emailTo);
    }

    @RequestMapping("/sendToAll")
    public void sendEmailToAll(@RequestParam String subject, @RequestParam String body) {
        emailSenderService.sendSimpleMessageToAll(subject, body);
    }
}
