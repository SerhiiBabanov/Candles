package com.candles.features.photo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<PhotoEntity, String> { }
