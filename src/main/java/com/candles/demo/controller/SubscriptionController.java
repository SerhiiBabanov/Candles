package com.candles.demo.controller;

import com.candles.demo.model.EmailSubscription;
import com.candles.demo.repository.EmailSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> subscribe(@ModelAttribute EmailSubscription email) {

        try {
            emailSubscriptionRepository.save(email);
        } catch (RuntimeException e) {  // DuplicateKeyException
            return ResponseEntity.badRequest().body("Already subscribed");
        }
        return ResponseEntity.ok("Subscribed");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@ModelAttribute EmailSubscription email) {
        emailSubscriptionRepository.deleteByEmail(email.getEmail());
        return ResponseEntity.ok("Unsubscribed");
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(EmailSenderController.class).slash("/subscribe?email=").withRel("subscribe to email"));
        model.add(linkTo(EmailSenderController.class).slash("/unsubscribe?email=").withRel("unsubscribe from email"));
        return model;
    }
}
