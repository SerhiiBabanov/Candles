package com.candles.demo.controller;

import com.candles.demo.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailSenderController implements RepresentationModelProcessor<RepositoryLinksResource> {

    private final EmailSenderService emailSenderService;

    @RequestMapping("/send")
    public void sendEmail(@RequestParam(name = "subject") String subject, @RequestParam(name = "body") String body, @RequestParam(name = "emailTo") String emailTo) {
        emailSenderService.sendSimpleMessage(subject, body, emailTo);
    }

    @RequestMapping("/sendToAll")
    public void sendEmailToAll(@RequestParam(name = "subject") String subject, @RequestParam(name = "body") String body) {
        emailSenderService.sendSimpleMessageToAll(subject, body);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(EmailSenderController.class).slash("/send?subject=&body=&emailTo=").withRel("send email to one person"));
        model.add(linkTo(EmailSenderController.class).slash("/sendToAll?subject=&body=").withRel("send email to all subscribers"));
        return model;
    }
}
