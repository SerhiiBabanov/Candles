package com.candles.demo.repository;

import com.candles.demo.model.EmailSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "subscription", path = "subscription")
public interface EmailSubscriptionRepository extends MongoRepository<EmailSubscription, String> {
}
