package com.candles.features.candle;

import com.candles.features.candle.aroma.Aroma;
import com.candles.features.landTranslateSupport.QPair;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        bindings.bind(String.class).all((StringPath path, Collection<? extends String> value) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            for (String v : value) {
                predicate.or(path.containsIgnoreCase(v));
            }
            return Optional.of(predicate);
        });
        bindings.bind(QPair.pair.value).all((path, value) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            for (String v : value) {
                predicate.or(path.containsIgnoreCase(v));
            }
            return Optional.of(predicate);
        });
    }

    @RestResource(exported = false)
    List<CandleEntity> findAllByAroma(Aroma aroma);

    @RestResource(exported = false)
    List<CandleEntity> findAllByIdIn(List<String> ids);

    @RestResource(exported = false)
    boolean existsById(String id);
}
