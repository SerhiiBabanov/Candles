package com.candles.features.candle;

import com.candles.features.box.BoxEntity;
import com.candles.features.candle.aroma.Aroma;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "candles", path = "candles")
public interface CandleRepository extends MongoRepository<CandleEntity, String>, QuerydslPredicateExecutor<CandleEntity>,
        QuerydslBinderCustomizer<QCandleEntity> {
    @Query("{ '$or': [ " +
            "{ 'name.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'title.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'volume' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'description.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'containerColor.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'aroma.name.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'waxColor.value' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'wick' : { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'wax' : { '$regex': ?0, '$options': 'i' } } " +
            "] }")
    List<CandleEntity> searchByPattern(String pattern);

    @Override
    default void customize(QuerydslBindings bindings, QCandleEntity root) {
        // Make case-insensitive 'like' filter for all string properties
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

    @RestResource(exported = false)
    List<CandleEntity> findAllByAroma(Aroma aroma);

    @RestResource(exported = false)
    List<CandleEntity> findAllByVolume(String volume);

    @RestResource(exported = false)
    List<CandleEntity> findAllBySlug(String slug);

    @RestResource(exported = false)
    List<CandleEntity> findAllByIdIn(List<String> ids);
}
