package com.candles.demo.controller;

import com.candles.demo.dto.BoxDto;
import com.candles.demo.dto.CandleDto;
import com.candles.demo.mapper.BoxMapper;
import com.candles.demo.mapper.CandleMapper;
import com.candles.demo.model.Box;
import com.candles.demo.model.Candle;
import com.candles.demo.service.BoxServices;
import com.candles.demo.service.CandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/similar-products")
public class SimilarProductsController implements RepresentationModelProcessor<RepositoryLinksResource> {
    private final CandleService candleService;
    private final BoxServices boxServices;
    private final EntityLinks entityLinks;
    private final BoxMapper boxMapper;
    private final CandleMapper candleMapper;

    @GetMapping("/candles")
    public ResponseEntity<CollectionModel<CandleDto>> getCandles(@RequestParam String id) {
        List<CandleDto> candleDtos = candleService.getSimilarCandles(id).stream()
                .map(candleMapper::toDto)
                .peek(candle -> candle.add(entityLinks.linkToItemResource(Candle.class, candle.getId())))
                .toList();
        return ResponseEntity.ok(CollectionModel.of(candleDtos));
    }

    @GetMapping("/boxes")
    public ResponseEntity<CollectionModel<BoxDto>> getBoxes(@RequestParam String id) {
        List<BoxDto> boxDtos = boxServices.getSimilarBoxes(id).stream()
                .map(boxMapper::toDto)
                .peek(box -> box.add(entityLinks.linkToItemResource(Box.class, box.getId())))
                .toList();
        return ResponseEntity.ok(CollectionModel.of(boxDtos));
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(SimilarProductsController.class).slash("/candles?id=}").withRel("get similar candles to id"));
        model.add(linkTo(SimilarProductsController.class).slash("/boxes?id=}").withRel("get similar boxes to id"));
        return model;
    }
}
