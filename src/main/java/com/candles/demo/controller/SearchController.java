package com.candles.demo.controller;

import com.candles.demo.model.SearchResultEntity;
import com.candles.demo.repository.BoxRepository;
import com.candles.demo.repository.CandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController implements RepresentationModelProcessor<RepositoryLinksResource> {
    private final BoxRepository boxRepository;
    private final CandleRepository candleRepository;

    @GetMapping
    public List<SearchResultEntity> search(@RequestParam(name = "q") String pattern) {
        Stream<SearchResultEntity> candles = candleRepository.searchByPattern(pattern).stream()
                .map(SearchResultEntity::new);

        Stream<SearchResultEntity> boxes = boxRepository.searchByPattern(pattern).stream()
                .map(SearchResultEntity::new);
        return Stream.concat(boxes, candles).collect(Collectors.toList());
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(SearchController.class).slash("?q=}").withRel("search by pattern"));
        return model;
    }
}
