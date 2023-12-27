package com.candles.features.box;


import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "boxes", path = "boxes")
public interface BoxRepository extends MongoRepository<BoxEntity, String>, QuerydslPredicateExecutor<BoxEntity>,
        QuerydslBinderCustomizer<QBoxEntity> {
    @Query("{ '$or': [ " +
            "{ 'name.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'title.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'volume' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'description.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'price' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'kit.aromaToChoose.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'kit.container.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'kit.matchsticks' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'wick' : { '$regex': ?0, '$options': 'i' } } " +
            "] }")
    List<BoxEntity> searchByPattern(String pattern);

    @Override
    default void customize(QuerydslBindings bindings, QBoxEntity root) {
        // Make case-insensitive 'like' filter for all string properties
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

    @RestResource(exported = false)
    List<BoxEntity> findAllByPrice(BigDecimal price);

    @RestResource(exported = false)
    List<BoxEntity> findAllByVolume(String volume);

    @RestResource(exported = false)
    List<BoxEntity> findAllByIdIn(List<String> ids);
}
