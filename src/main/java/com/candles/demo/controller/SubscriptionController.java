package com.candles.demo.controller;

import com.candles.demo.model.EmailSubscription;
import com.candles.demo.repository.EmailSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {
    private final EmailSubscriptionRepository emailSubscriptionRepository;

    @PostMapping
    public void subscribe(@ModelAttribute EmailSubscription emailSubscription) {
        emailSubscriptionRepository.save(emailSubscription);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@ModelAttribute EmailSubscription emailSubscription) {
        emailSubscriptionRepository.delete(emailSubscription);
    }
}
