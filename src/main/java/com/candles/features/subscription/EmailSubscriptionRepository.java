package com.candles.features.subscription;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "subscription", path = "subscription")
public interface EmailSubscriptionRepository extends MongoRepository<EmailSubscriptionEntity, String> {
    void deleteByEmail(String email);
}
