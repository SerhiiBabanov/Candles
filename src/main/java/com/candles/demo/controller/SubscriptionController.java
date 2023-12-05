package com.candles.demo.controller;

import com.candles.demo.model.EmailSubscription;
import com.candles.demo.repository.EmailSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController implements RepresentationModelProcessor<RepositoryLinksResource> {
    private final EmailSubscriptionRepository emailSubscriptionRepository;

    @PostMapping("/subscribe")
    public void subscribe(@ModelAttribute EmailSubscription email) {
        emailSubscriptionRepository.save(email);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@ModelAttribute EmailSubscription email) {
        emailSubscriptionRepository.delete(email);
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(EmailSenderController.class).slash("/subscribe?email=").withRel("subscribe to email"));
        model.add(linkTo(EmailSenderController.class).slash("/unsubscribe?email=").withRel("unsubscribe from email"));
        return model;
    }
}
