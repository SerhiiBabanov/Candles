package com.candles.features.search;

import com.candles.features.landTranslateSupport.Local;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.search;
import static com.mongodb.client.model.search.SearchOperator.text;
import static com.mongodb.client.model.search.SearchPath.wildcardPath;

@Service
public class SearchService {
    private final MongoCollection<Document> candleEntityMongoCollection;
    private final MongoCollection<Document> boxEntityMongoCollection;

    public SearchService(MongoTemplate mongoTemplate) {
        MongoDatabase database = mongoTemplate.getDb();
        this.candleEntityMongoCollection = database.getCollection("candleEntity");
        this.boxEntityMongoCollection = database.getCollection("boxEntity");
    }

    public List<SearchResult> fullTextSearch(String keywords, Local lang) {
        //only first 5 results
        int limit = 5;
        List<SearchResult> combineResults = new ArrayList<>();
        List<Bson> pipeline = Arrays.asList(
                search(
                        text(
                                wildcardPath("*"), keywords
                        )
                ),
                Aggregates.limit(limit)
        );
        boxEntityMongoCollection.aggregate(pipeline)
                .into(new ArrayList<>()).stream()
                .map(document -> new SearchResult(document, lang))
                .forEach(combineResults::add);
        candleEntityMongoCollection.aggregate(pipeline)
                .into(new ArrayList<>()).stream()
                .map(document -> new SearchResult(document, lang))
                .forEach(combineResults::add);

        return combineResults;
    }
}
