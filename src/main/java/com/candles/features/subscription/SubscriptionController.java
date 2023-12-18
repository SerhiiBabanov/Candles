package com.candles.features.subscription;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/subscription")
public class SubscriptionController {
    private final EmailSubscriptionRepository emailSubscriptionRepository;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@Valid @ModelAttribute EmailSubscriptionEntity email) {

        try {
            emailSubscriptionRepository.save(email);
        } catch (RuntimeException e) {  // DuplicateKeyException
            return ResponseEntity.badRequest().body("Already subscribed");
        }
        return ResponseEntity.ok("Subscribed");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@ModelAttribute EmailSubscriptionEntity email) {
        emailSubscriptionRepository.deleteByEmail(email.getEmail());
        return ResponseEntity.ok("Unsubscribed");
    }
}
